package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.FindElement;
import static com.navatar.generic.CommonLib.FindElements;
import static com.navatar.generic.CommonLib.click;
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
import com.navatar.generic.EnumConstants.SortOrder;
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


	public boolean VerifyDropdownCountandAscendingOrder(ArrayList<String> fieldLabel,ArrayList<Integer> size)
	{
		boolean flag=false;
		String xPath="";
		WebElement ele=null;

		for(int i=0;i<fieldLabel.size();i++)
		{

			try
			{
				ele = new WebDriverWait(driver, 25).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[text()='"+fieldLabel.get(i)+"']/parent::lightning-combobox//button/span[text()='All']")));
				log(LogStatus.INFO, "Element has been found for the "+fieldLabel.get(i), YesNo.No);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				log(LogStatus.ERROR,"Could not get the element for "+fieldLabel.get(i),YesNo.No);
				return false;
			}

			if(CommonLib.click(driver, ele,fieldLabel.get(i) , action.SCROLLANDBOOLEAN))
			{

				log(LogStatus.INFO, "Clicked on the "+fieldLabel.get(i), YesNo.No);
				xPath="//label[text()='"+fieldLabel.get(i)+"']/parent::lightning-combobox//span[not(text()='All')]/parent::span/parent::lightning-base-combobox-item";
				List<WebElement> elements=CommonLib.FindElements(driver, xPath, fieldLabel.get(i));

				if(elements.size()==size.get(i))
				{
					log(LogStatus.INFO, "Dropdown count has been matched of "+fieldLabel.get(i), YesNo.No);
					sa.assertTrue(true, "Dropdown count has been matched of "+fieldLabel.get(i));
					if(CommonLib.checkSorting(driver, SortOrder.Assecending, elements))
					{
						log(LogStatus.PASS, "Dropdown lists are in the ascending order "+fieldLabel.get(i), YesNo.No);
						sa.assertTrue(true, "Dropdown lists are in the ascending order "+fieldLabel.get(i));
						flag=true;
					}
					else
					{
						log(LogStatus.ERROR, "Dropdown lists are not in the ascending order "+fieldLabel.get(i), YesNo.No);
						sa.assertTrue(false, "Dropdown lists are not in the ascending order "+fieldLabel.get(i));
						flag=true;

					}

					flag= true;
				}
				else
				{
					log(LogStatus.ERROR,"Dropdown count is not matched of "+fieldLabel.get(i),YesNo.No);
					sa.assertTrue(false, "Dropdown count is not matched of "+fieldLabel.get(i));
					flag= false;
				}
			}
			else
			{
				log(LogStatus.ERROR,"Could not click on the "+fieldLabel.get(i),YesNo.No);
				flag=false;
			}	

		}

		return flag;
	}




	public boolean verifySDGExpandByDefault(String Title) {
		boolean flag = false;
		WebElement expandElement = FindElement(driver,
				"//a[text()='" + Title + "']/ancestor::article//div[@class='slds-hide']/following-sibling::div",
				"Expand Element of SDG: " + Title, action.SCROLLANDBOOLEAN, 10);
		if (expandElement != null) {

			log(LogStatus.INFO, "Expand Element Found of SDG: " + Title, YesNo.Yes);

			String display = CommonLib.getAttribute(driver, expandElement, "Expand Element of SDG: " + Title, "style");

			if (display.contains("block")) {
				log(LogStatus.INFO, "-------------SDG of Title:  " + Title + " is Expanded------------", YesNo.No);
				flag = true;

			} else {
				log(LogStatus.ERROR, "-------------SDG of Title:  " + Title + " is not Expanded------------", YesNo.No);

			}

		} else {
			log(LogStatus.ERROR, "Expand Element Not Found of SDG:  " + Title, YesNo.No);

		}
		return flag;

	}



	public boolean sdgGridExpandedByDefaultIfNotThenExpand(String Title) 
	{
		boolean flag = false;
		WebElement alreadyAddedComponentToHomePage = FindElement(driver, "//a[text()='" + Title + "']",
				"Component Title ", action.SCROLLANDBOOLEAN, 10);
		if (alreadyAddedComponentToHomePage != null) {

			log(LogStatus.INFO, "Component Title Matched to Home Page " + Title, YesNo.Yes);

			if(!verifySDGExpandByDefault(Title)) {
				log(LogStatus.INFO, "Not Expanded By Default SDG: " + Title, YesNo.No);
				log(LogStatus.INFO, "Now Expanding  SDG: " + Title, YesNo.No);

				WebElement TooltipElement = FindElement(driver,
						"//a[text()='" + Title + "']/ancestor::article/preceding-sibling::lightning-icon", "Tooltip",
						action.SCROLLANDBOOLEAN, 20);
				if (click(driver, TooltipElement, "Collapse/Expand Element", action.SCROLLANDBOOLEAN)) {
					appLog.info("clicked on Collapse/Expand");
					flag=true;
				}
				else {
					log(LogStatus.ERROR, "Not Able to click on Expand Button of SDG :" + Title, YesNo.No);

				}			
			}	

			else {
				log(LogStatus.INFO, "Expanded By Default SDG :" + Title, YesNo.No);
				flag=true;

			}
		}
		else {
			log(LogStatus.ERROR, "Component Title Not Matched to Home Page :" + Title, YesNo.No);

		}

		return flag;
	}



	public boolean pageSizeSelect(String Title, String pageSize)
	{

		boolean flag = false;
		WebElement pageSizeSelect = FindElement(driver,
				"//a[text()='" + Title
				+ "']/ancestor::article//span[text()='Page Size']/../parent::div//select",
				"Page Size Select ", action.SCROLLANDBOOLEAN, 10);
		if (CommonLib.selectVisibleTextFromDropDown(driver, pageSizeSelect, "Page Size Select", pageSize)) {
			log(LogStatus.INFO, "Selected the Page Size", YesNo.No);
			CommonLib.ThreadSleep(25000);
			flag=true;
		}
		else {
			log(LogStatus.ERROR, "Not Able To Select Page Size ", YesNo.No);
			return flag;

		}

		return flag;
	}


	public int numberOfRecords(String Title,String pageSize) 
	{
		boolean flag=false;
		int size=0;
		if(sdgGridExpandedByDefaultIfNotThenExpand(Title))
		{
			log(LogStatus.INFO, "SDG data has been expended", YesNo.No);

			if(pageSizeSelect(Title, pageSize))
			{
				log(LogStatus.INFO, "Page size "+pageSize+" has been selected", YesNo.No);	   
				CommonLib.ThreadSleep(5000);
				List<WebElement> records = FindElements(driver,
						"//a[text()='" + Title + "']/ancestor::article//tbody/tr", "Records");
				System.out.println("No. of Records Present: " + records.size());
				size= records.size();
				flag=true;
			}
			else
			{
				log(LogStatus.ERROR,"Could not select the Pagesize",YesNo.No);
				flag= false;
			}
		}
		else
		{
			log(LogStatus.ERROR,"Could not expend the SDG",YesNo.No);
			flag= false;
		}
		return size;

	}

	public boolean selectFilter(String fieldLabel,String filterName)
	{
		WebElement ele;
		String xPath;

		CommonLib.refresh(driver);

		try
		{

			//	ele = new WebDriverWait(driver, 25).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[text()='"+fieldLabel+"']/parent::lightning-combobox//button/span[text()='All']")));
			ele = new WebDriverWait(driver, 25).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[text()='"+fieldLabel+"']/parent::lightning-combobox//button")));		

			log(LogStatus.INFO, "Element has been found for the "+fieldLabel, YesNo.No);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			log(LogStatus.ERROR,"Could not get the element for "+fieldLabel,YesNo.No);
			return false;
		}
		CommonLib.ThreadSleep(3000);
		try
		{
			Actions act=new Actions(driver);
			act.moveToElement(ele).perform();
			log(LogStatus.INFO, "Element has been moved to "+fieldLabel, YesNo.No);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			log(LogStatus.ERROR, "not able to move to Element : "+fieldLabel, YesNo.No);
		}
		CommonLib.ThreadSleep(2000);
		if(CommonLib.click(driver, ele,fieldLabel , action.BOOLEAN))
		{
			CommonLib.ThreadSleep(4000);
			xPath="//label[text()='"+fieldLabel+"']/parent::lightning-combobox//lightning-base-combobox-item//span[@class='slds-truncate']";
			List<WebElement> elements=CommonLib.FindElements(driver, xPath, fieldLabel);
			if(CommonLib.getSelectedOptionOfDropDown(driver,elements,fieldLabel+ "Dropdown list" , filterName))
			{
				log(LogStatus.INFO, "Drop down has been selected from "+fieldLabel, YesNo.No);
				return true;
			}
			else
			{
				log(LogStatus.ERROR,"Dropdown value is not selected from "+fieldLabel,YesNo.No);
				return false;
			}
		}
		else
		{
			log(LogStatus.ERROR,"Could not click on "+fieldLabel,YesNo.No);
			return false;
		}


	}

	public boolean verifyRecordfilterfieldvisibility()
	{
		
		CommonLib.refresh(driver);
		
		if(CommonLib.checkElementVisibility(driver, getrecordFilter(50), "Record Filter", 50))
		{
			log(LogStatus.INFO,"Record Filter fild is visible",YesNo.No);

			return true;
		}
		else
		{
			log(LogStatus.INFO,"Record Filter fild is not visible",YesNo.No);

			return false;
		}

	}
	
	public ArrayList<String> verifyDropDownOptionValue(String fieldLabel, ArrayList<String> optionvalue)
	{
		WebElement ele=null;
		boolean flag= false;
		String xPath;
		ArrayList<String> result=new ArrayList<String>();
		try
		{
			ele = new WebDriverWait(driver, 25).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[text()='"+fieldLabel+"']/parent::lightning-combobox//button")));
			log(LogStatus.INFO, "Element has been found for the "+fieldLabel, YesNo.No);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			log(LogStatus.ERROR,"Could not get the element for "+fieldLabel,YesNo.No);
			result.add("Could not get the element for "+fieldLabel);
		}
		if(CommonLib.click(driver, ele,fieldLabel , action.SCROLLANDBOOLEAN))
		{
			log(LogStatus.INFO, "Clicked on the "+fieldLabel, YesNo.No);
			xPath="//label[text()='"+fieldLabel+"']/parent::lightning-combobox//span[not(text()='All')]/parent::span/parent::lightning-base-combobox-item";
			List<WebElement> elements=CommonLib.FindElements(driver, xPath, fieldLabel);
			
			ArrayList<String> dropDownOptionvalue = new ArrayList<String>();
			
			for(WebElement eles:elements)
			{
				String val=CommonLib.getText(driver, eles, fieldLabel, action.SCROLLANDBOOLEAN);
				dropDownOptionvalue.add(val);
			}
			
			for(int i=0;i<optionvalue.size();i++)
			{
				if(optionvalue.get(i).equals(dropDownOptionvalue.get(i)))
				{
					log(LogStatus.INFO, optionvalue.get(i)+" value has been matched with the "+dropDownOptionvalue.get(i), YesNo.No);
				}
				else
				{
					log(LogStatus.ERROR, optionvalue.get(i)+" value is not matched with the "+dropDownOptionvalue.get(i), YesNo.No);
					result.add(optionvalue.get(i)+" value is not matched with the "+dropDownOptionvalue.get(i));					
				}
			}

			
		}
		else
		{
			log(LogStatus.ERROR, "Could not click on the Record filter field", YesNo.No);
			result.add("Could not click on the Record filter field");					
	
		}
		
		
		return result;

		
	}



}








