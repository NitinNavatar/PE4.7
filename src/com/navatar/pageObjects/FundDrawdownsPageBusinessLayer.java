package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.FundDrawdownPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;
import com.navatar.generic.SoftAssert;

/**
 * @author Akul Bhutani
 *
 */
public class FundDrawdownsPageBusinessLayer extends FundDrawdownsPage {

	public FundDrawdownsPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**@author Akul Bhutani
	 * @param environment
	 * @param mode
	 * @param DDID
	 * @return true/false
	 */
	public boolean clickOnCreatedDrawdown(String environment, String mode,String DDID) {
		
		if(mode.equalsIgnoreCase(Mode.Classic.toString())){
		int i=1;
		if (getSelectedOptionOfDropDown(driver, getViewDropdown(60), "View dropdown", "text").equalsIgnoreCase("All")) {
			if (click(driver, getGoButton(60), "Go button", action.BOOLEAN)) {

			}
			else {
				appLog.error("Go button not found");
				return false;
			}
		}
		else{
			if (selectVisibleTextFromDropDown(driver, getViewDropdown(60),"View dropdown","All") ){
			}
			else {
				appLog.error("All Funds not found in dropdown");
				return false;
			}

		}
	
			WebElement DD = getDrawdownIDAtFDPage(DDID, 20);
			if (DD != null) {
				if (click(driver, DD, "", action.SCROLLANDBOOLEAN)) {
					appLog.info("Clicked on DD : " + DDID);
					return true;
					} 
			} else {
		
				//
				
				while (true) {
					appLog.error("Fund Name is not Displaying on "+i+ " Page:" + DDID);
					if (click(driver, getNextImageonPage(10), "Fund Page Next Button",
							action.SCROLLANDBOOLEAN)) {

						appLog.info("Clicked on Next Button");
						DD = getDrawdownIDAtFDPage(DDID, 20);
						if (DD != null) {
							if (click(driver, DD, "Fund Name", action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on fund name : " + DDID);
								return true;
								
							}
						}

						

					} else {
						appLog.error("Fund Not Available : " + DDID);
						return false;
					}
					i++;
				}
				
				//
				
			}
	}else{
		if(clickOnAlreadyCreated_Lighting(environment, mode, TabName.FundDrawdowns, DDID, 30)){
			appLog.info("Clicked on fund name : " + DDID);
			return true;
		}else{
			appLog.error("Fund Not Available : " + DDID);	
		}
	}
			return false;
		
		
	}

	/**@author Akul Bhutani
	 * @param environment
	 * @param mode
	 * @param tabName
	 * @param labelName
	 * @param labelValue
	 * @return true/false
	 */
	public boolean fieldValueVerificationOnFundDrawdown(String environment, String mode, TabName tabName,
			String labelName,String labelValue) {
		String finalLabelName;
		if (labelName.equalsIgnoreCase(FundDrawdownPageFieldLabelText.Call_Amount.toString()) || 
				labelName.equalsIgnoreCase(FundDrawdownPageFieldLabelText.Amount_Due.toString())||
				labelName.equalsIgnoreCase(FundDrawdownPageFieldLabelText.Total_Call_Amount_Received.toString()) ||
				labelName.equalsIgnoreCase(FundDrawdownPageFieldLabelText.Capital_Amount.toString())||
				labelName.equalsIgnoreCase(FundDrawdownPageFieldLabelText.Management_Fee.toString()) ||
				labelName.equalsIgnoreCase(FundDrawdownPageFieldLabelText.Other_Fee.toString())) {
			labelValue=convertNumberAccordingToFormatWithCurrencySymbol(labelValue, "0,000.00");
			
		}
		if (labelName.contains("_")) {
			finalLabelName = labelName.replace("_", " ");
		} else {
			finalLabelName = labelName;
		}
		String xpath = "";
		WebElement ele = null;
		if (mode.equalsIgnoreCase(Mode.Classic.toString())) {
				if (finalLabelName.contains("Fund Name"))
					xpath = "//td[text()='"+finalLabelName+"']/following-sibling::td/div/a";
				else if(finalLabelName.contains("Call Date")||finalLabelName.contains("Due Date"))
					xpath = "//span[text()='" + finalLabelName + "']/../following-sibling::div";
				else
					xpath = "//span[text()='" + finalLabelName + "']/../following-sibling::div";

			

		} else {//lightning
			
			
			////////////////////////////////////////////////////////////////////////
			
	xpath = "//span[text()='"+finalLabelName+"']/../following-sibling::div//*[text()='"+labelValue+"']";
		
	ele = 	FindElement(driver, xpath, finalLabelName + " label text with  " + labelValue, action.SCROLLANDBOOLEAN, 10);
	scrollDownThroughWebelement(driver, ele, finalLabelName + " label text with  " + labelValue);
	ele = 	isDisplayed(driver,ele,"Visibility", 5, finalLabelName + " label text with  " + labelValue);
	if (ele != null) {
		String aa = ele.getText().trim();
		appLog.info(finalLabelName + " label text with  " + labelValue+" verified");
		return true;

	} else {
		appLog.error("<<<<<<   "+finalLabelName + " label text with  " + labelValue+" not verified "+"   >>>>>>");
	}
	return false;
	
			//////////////////////////////////////////////////////////////////

		}
		ele = isDisplayed(driver,
				FindElement(driver, xpath, finalLabelName + " label text in " + mode, action.SCROLLANDBOOLEAN, 10),
				"Visibility", 5, finalLabelName + " label text in " + mode);
		if (ele != null) {
			String aa = ele.getText().trim();
			if (aa.equals("")) {
				aa = CommonLib.getValueFromElementUsingJavaScript(driver,ele, finalLabelName);
			}
			appLog.info("Lable Value is: "+aa);
			if(aa.contains(labelValue)) {
				appLog.info(labelValue + " Value is matched successfully.");
				return true;
				
			}else {
				appLog.info(labelValue + " Value is not matched. Expected: "+labelValue+" /t Actual : "+aa);
			}
		} else {
			appLog.error(finalLabelName + " Value is not visible so cannot matched  label Value "+labelValue);
		}
		return false;

	}
	
	/**@author Akul Bhutani
	 * @param driver
	 * @param pName
	 * @param contact
	 * @param accountName
	 * @param email
	 * @param CCID
	 * @param commitmentID
	 * @param lp
	 * @param partnership
	 * @param emailSentStatus
	 * @return
	 */
	public SoftAssert verifyEmailingContentGrid(WebDriver driver, PageName pName, String contact,
			String accountName, String email, String CCID, String commitmentID, String lp, String partnership, boolean emailSentStatus) {
		int j = 0;
		SoftAssert saa = new SoftAssert();
		String workSpaceXpath = "";
		boolean flag = false;
		String dcContact=null;
		String dcAccountName = null;
		String dcEmail = null;
		String dcCCID = null;
		String dcCommitmentID = null;
		String dcLP = null;
		String dcPartnership = null;
		boolean dcEmailSentStatus=false;


			String docContactXpath="";
			String docAccountXpath="";
			String docEmailXpath="";
			String docCCID="";
			String docCommimentID="";
			String docLP="";
			String docPartnership="";
			String docEmailSentStatus="";
			docContactXpath = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+labelColumnIndex(getEmailingGridColumn("Contact Name",30))+"')]//a";
			docAccountXpath = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+labelColumnIndex(getEmailingGridColumn("Account Name",30))+"')]//a";
			docEmailXpath = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+labelColumnIndex(getEmailingGridColumn("Email",30))+"')]//a";
			docCCID = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+labelColumnIndex(getEmailingGridColumn("Capital Call ID",30))+"')]";
			docCommimentID = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+labelColumnIndex(getEmailingGridColumn("Commitment ID",30))+"')]";
			docLP = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+labelColumnIndex(getEmailingGridColumn("Limited Partner",30))+"')]";
			docPartnership = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+labelColumnIndex(getEmailingGridColumn("Partnership",30))+"')]";
			docEmailSentStatus = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+labelColumnIndex(getEmailingGridColumn("Email Sent Status",30))+"')]//img";

			
			List<WebElement> contactNames = FindElements(driver, docContactXpath, "Contact Name List");

			if (!contactNames.isEmpty()) {
				
				
				List<String> docEmail = findColumnHorizontalScrollAndReturnElementsString(driver,getScrollBoxStep1FundDrawdownEmailingGrid(30), docEmailXpath,"text");
				List<String> doCCID = findColumnHorizontalScrollAndReturnElementsString(driver, getScrollBoxStep1FundDrawdownEmailingGrid(30),docCCID,"text");
				List<String> doCommimentID = findColumnHorizontalScrollAndReturnElementsString(driver,getScrollBoxStep1FundDrawdownEmailingGrid(30), docCommimentID,"text");
				List<String> doLP = findColumnHorizontalScrollAndReturnElementsString(driver, getScrollBoxStep1FundDrawdownEmailingGrid(30),docLP,"text");
				List<String> doPartnership = findColumnHorizontalScrollAndReturnElementsString(driver, getScrollBoxStep1FundDrawdownEmailingGrid(30),docPartnership ,"text");
				List<String> doEmailSentStatus = findColumnHorizontalScrollAndReturnElementsString(driver,getScrollBoxStep1FundDrawdownEmailingGrid(30),docEmailSentStatus,"title");
				contactNames = FindElements(driver, docContactXpath, "Contact Name List");
				List<WebElement> docAccount = FindElements(driver, docAccountXpath, "Account Name List");
				for (j = 0;j<contactNames.size();j++) {

					dcContact=contactNames.get(j).getText().trim();
					dcAccountName = docAccount.get(j).getText().trim();
					dcEmail = docEmail.get(j);
					dcCCID = doCCID.get(j);
					dcCommitmentID = doCommimentID.get(j);
					dcPartnership = doPartnership.get(j);
					dcLP = doLP.get(j);
					System.err.println("value in email status"+ doEmailSentStatus.get(j));
					if (doEmailSentStatus.get(j).equalsIgnoreCase("false"))
						dcEmailSentStatus = false;
					else if(doEmailSentStatus.get(j).equalsIgnoreCase("true"))
						dcEmailSentStatus = true;





					System.err.println(">>>>>>> from WebPage "+dcContact+" "+dcAccountName+" "+dcEmail+" "+dcCCID+" "+dcCommitmentID+" "+dcLP+" "+dcPartnership+" "+"email sent status "+dcEmailSentStatus+" <<<<<<<<");
					System.out.println("<<<<<<<<< pASSING "+contact +" "+accountName+" "+email+" "+CCID+" "+commitmentID+" "+lp+" "+partnership +" email sent status "+emailSentStatus+"  <<<<<<<<<");
					appLog.info(">>>>>>> from WebPage "+dcContact+" "+dcAccountName+" "+dcEmail+" "+dcCCID+" "+dcCommitmentID+" "+dcLP+" "+dcPartnership+" <<<<<<<<");
					appLog.error("<<<<<<<<< pASSING "+contact +" "+accountName+" "+email+" "+CCID+" "+commitmentID+" "+lp+" "+partnership +" <<<<<<<<<");

					if (dcContact.equalsIgnoreCase(contact) &&dcAccountName.equalsIgnoreCase(accountName)
							&&dcEmail.equalsIgnoreCase(email)&& dcCCID.equalsIgnoreCase(CCID) 
							&& dcCommitmentID.equalsIgnoreCase(commitmentID) && dcLP.equalsIgnoreCase(lp)
							&& dcPartnership.contains(partnership)
							&& (dcEmailSentStatus == emailSentStatus)
							) {

						appLog.info(contact+" present having account "+accountName+" email "+email+" capital call id"+CCID + " " + pName.toString() + " : "
								);
						flag = true;
						break;
					}
				}
			}
		
		
		if (!flag) {
			appLog.error("could not find "+contact+" from account "+accountName+" in emailing grid");
			saa.assertTrue(false, "could not find "+contact+" from account "+accountName+" in emailing grid");
		}
				
		return saa;
	}
	
	/**@author Akul Bhutani
	 * @param driver
	 * @param widgetScrollingElement
	 * @param columnXpath
	 * @param getTitleOrText
	 * @return
	 */
	public List<String> findColumnHorizontalScrollAndReturnElementsString(WebDriver driver, WebElement widgetScrollingElement, String columnXpath, String getTitleOrText) {
		int j =50;
		int i =0;
		WebElement el=null;
		List<WebElement> li = new ArrayList<WebElement>();
		List<String> lis = new ArrayList<String>();
		
		int widgetTotalScrollingWidth = Integer.parseInt(String.valueOf(((JavascriptExecutor) driver)
				.executeScript("return arguments[0].scrollWidth", widgetScrollingElement)));
		while (el==null) {
			el=isDisplayed(driver,FindElement(driver, columnXpath, "column header", action.BOOLEAN, 2) , "visibility", 2, "column header");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy( "+j+" ,0)",
					widgetScrollingElement);
			i+=j;
			if (i == widgetTotalScrollingWidth)
				return null;
		}
		li = FindElements(driver, columnXpath, "column elements");
		if (getTitleOrText.equalsIgnoreCase("text")) {
			for (WebElement e : li) {
				lis.add(e.getText().trim());
			}
		}
		else if(getTitleOrText.equalsIgnoreCase("title")) {
			for (WebElement e:li ) {
				lis.add(e.getAttribute("title"));
			}
		}
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo( 0 ,0)",
				widgetScrollingElement);
		return lis;
	}

	/**@author Akul Bhutani
	 * @param pageName TODO
	 * @param column
	 * @return
	 */
	public boolean moveFromAvailableToSelectedColumnsToDisplay(PageName pageName, String column) {
		String  page ;
		if (PageName.FundDrawdown.toString().equalsIgnoreCase(pageName.toString())) {
			page="DD";
		} else {
			page="FD";
		}
		WebElement from = null;
		from = isDisplayed(driver, FindElement(driver, "//span[@id='avllistbulkEmail"+page+"-view']//span[text()='"+column+"']", "available column", action.SCROLLANDBOOLEAN, 10), "visibility", 30, "available column");
		if (from!=null) {
			if (click(driver, from, "from column", action.SCROLLANDBOOLEAN) ) {
				if (click(driver, getMoveToSelected(pageName,30), "move to selected button", action.BOOLEAN)) {
					if (isDisplayed(driver, FindElement(driver, "//span[@id='sellistbulkEmail"+page+"-view']//span[text()='"+column+"']", "column name in selected grid", action.SCROLLANDBOOLEAN, 30), "visibility", 30, "selected column")!=null) {
						log(LogStatus.INFO, "successfully found column name in selected grid", YesNo.No);
						return true;
					}
					else {
						log(LogStatus.ERROR, "could not find element in selected grid", YesNo.Yes);
						BaseLib.sa.assertTrue(false, "could not find element in selected grid");
					}
				}
				else {
					log(LogStatus.ERROR, "could not click on move to selected icon", YesNo.Yes);
					BaseLib.sa.assertTrue(false, "could not click on move to selected icon");
				}
			}
			else {
				log(LogStatus.ERROR, "could not click on column in available grid", YesNo.Yes);
				BaseLib.sa.assertTrue(false, "could not click on column in available grid");
			}
		}
		else {
			log(LogStatus.ERROR, "could not find column in content grid",YesNo.Yes);
			BaseLib.sa.assertTrue(false,  "could not find column in content grid");
		}
		return false;
	}
	
	

	/**@author Akul Bhutani
	 * @param pageName TODO
	 * @param column
	 * @return
	 */
	public boolean verifyColumnInColumnsToDisplayGrid(PageName pageName, String column) {
		String  page ;
		if (PageName.FundDrawdown.toString().equalsIgnoreCase(pageName.toString())) {
			page="DD";
		} else {
			page="FD";
		}
		
		WebElement ele = isDisplayed(driver,FindElement(driver, "//span[@id='sellistbulkEmail"+page+"-view']//span[text()='"+column+"']", column+" in available grid", action.SCROLLANDBOOLEAN, 20) , "visibility", 20, "column in available grid");
		if (ele!=null) {
			if (click(driver, ele, column + " in available grid", action.SCROLLANDBOOLEAN)) {
				return true;
			}
		}
		return false;
	}
	
	/**@author Akul Bhutani
	 * @param headerName
	 * @param timeOut
	 * @return
	 */
	public WebElement getEmailingGridColumn(String headerName,int timeOut) {
		String xpath="//span[text()='"+headerName+"'][contains(@id,'aw')]";
		return FindElement(driver,xpath, "", action.BOOLEAN,20);
	}
	
	/**@author Akul Bhutani
	 * @param headerName
	 * @param timeout
	 * @return
	 */
	public List<WebElement> getEmailingGridContentGrid(String headerName,int timeout) {
		List<WebElement> eleList = new ArrayList<WebElement>();
		String xpath="";
		int i =labelColumnIndex(getEmailingGridColumn(headerName,timeout));
		appLog.info("Column Label Index : "+i);
		if(headerName.equalsIgnoreCase("Contact Name") || headerName.equalsIgnoreCase("Email")) {
			xpath="//span[contains(@id,'aw')][contains(@id,'cell-"+i+"')]/a";
		}else {
			xpath="//span[contains(@id,'aw')][contains(@id,'cell-"+i+"')]";
		}
		eleList=FindElements(driver,xpath, headerName+" List");
		return eleList;

	}
	
	/**@author Akul Bhutani
	 * @param ele
	 * @return
	 */
	public int labelColumnIndex(WebElement ele){
		if(ele!=null){
			String attributeValue = ele.getAttribute("id");
			appLog.info("getIntegerFromString(attributeValue).get(0) : "+getIntegerFromString(attributeValue).get(0));
			appLog.info("getIntegerFromString(attributeValue).get(1) : "+getIntegerFromString(attributeValue).get(1));
			return getIntegerFromString(attributeValue).get(1);
		}else{
			return 1;	
		}		
	}

	/**@author Akul Bhutani
	 * @param emailTemplateName
	 * @return
	 */
	public boolean clickOnEmailTemplatePreviewLink(String emailTemplateName) {
		boolean flag = false;
		WebElement ele=null;
		String parentWindow=null;
		ele=emailingPreviewTemplateLink(emailTemplateName, 10);
		if(ele!=null) {
			if(click(driver, ele, emailTemplateName+" preview link", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "clicked on "+emailTemplateName+" email template link",YesNo.No);
				parentWindow=switchOnWindow(driver);
				if(parentWindow!=null) {
					String xpath="//td[text()='"+emailTemplateName+"']";
					ele = isDisplayed(driver, FindElement(driver,xpath, "", action.BOOLEAN,20), "visibility",20,emailTemplateName+" template name");
					if(ele!=null) {
						log(LogStatus.INFO, emailTemplateName+" template is open in preview mode", YesNo.No);
						flag=true;
					}else {
						log(LogStatus.ERROR, emailTemplateName+" template is open in preview mode", YesNo.Yes);
					}
					driver.close();
					driver.switchTo().window(parentWindow);
					
				}else {
					log(LogStatus.ERROR, "No new window is open after click on Capital Call Notice template preview link", YesNo.Yes);
				}
			}else {
				log(LogStatus.ERROR, "Not able to click on "+emailTemplateName+" email template link so cannot check preview email template", YesNo.Yes);
			}
		}else {
			log(LogStatus.ERROR, emailTemplateName+" email template preview link is not visible so cannot click on it", YesNo.Yes);
		}
		
		return flag;
		
	}

	/**@author Akul Bhutani
	 * @param folderName
	 * @param emailTemplateName
	 * @return
	 */
	public boolean selectEmailTemplateFromFundDrawdown(String folderName, String emailTemplateName) {
		int j = 0;
		WebElement ele=null;
		String XpathelementTOSearch="//span[text()='"+emailTemplateName+"']/preceding-sibling::span/input";
		if(folderName!=null) {
			if(selectVisibleTextFromDropDown(driver, getFundDrawdownFolderDropDownList(20), "folder drop downlist", folderName)) {
				appLog.info("Folder Name is selected from folder drop down list : "+folderName);
			}else {
				appLog.error("Not able to select email prospects email template folder: "+folderName);
				return false;
			}
		}
		By byelementToSearch = By.xpath(XpathelementTOSearch);
		int widgetTotalScrollingHeight = Integer.parseInt(String.valueOf(((JavascriptExecutor) driver)
				.executeScript("return arguments[0].scrollHeight", getScrollBoxStep2FundDrawdown(10))));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0,0)", getScrollBoxStep2FundDrawdown(10));
		for (int i = 0; i <= widgetTotalScrollingHeight / 25; i++) {
			if (!driver.findElements(byelementToSearch).isEmpty() && driver.findElement(byelementToSearch).isDisplayed()) {
				appLog.info("Element Successfully Found and displayed");
				ThreadSleep(500);
				ele = FindElement(driver, XpathelementTOSearch, "", action.BOOLEAN, 10);
				if (ele != null) {
					if (click(driver, ele, "", action.BOOLEAN)) {
						appLog.info("clicked on Custom email template radio button : "+emailTemplateName);
						
					} else {
						appLog.error("Not able to clicked on email template radio button: "+emailTemplateName);
						return false;
					}
				}
				break;
			} else {
				System.out.println("Not FOund: " + byelementToSearch.toString());
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(" + j + "," + (j = j + 45) + ")",
						getScrollBoxStep2FundDrawdown(10));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (i == widgetTotalScrollingHeight / 50) {
					return false;
				}
			}
		}
		return true;
		
	}

	public boolean ScrollAndClickOnContactNameCheckBoxInFundDrawdown(PageName pageName,String contactName, String accountName,int timeout) {
		int j = 0;
		WebElement ele = null;
		String XpathelementTOSearch="";
			XpathelementTOSearch ="//span[contains(@class,'aw-grid-row')]//a[text()='"+contactName+"']/../following-sibling::span/a[text()='"+accountName+"']/../../span[2]/span/span[1]";
		By byelementToSearch = By.xpath(XpathelementTOSearch);
		int widgetTotalScrollingHeight = Integer.parseInt(String.valueOf(((JavascriptExecutor) driver)
				.executeScript("return arguments[0].scrollHeight", getScrollBoxStep1FundDrawdownEmailingGrid(10))));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0,0)", getScrollBoxStep1FundDrawdownEmailingGrid(10));
		for (int i = 0; i <= widgetTotalScrollingHeight / 25; i++) {
			if (!driver.findElements(byelementToSearch).isEmpty()
					&& driver.findElement(byelementToSearch).isDisplayed()) {
				appLog.info("Element Successfully Found and displayed");
				ThreadSleep(500);
				ele = FindElement(driver, XpathelementTOSearch, "", action.BOOLEAN, timeout);
				if (ele != null) {
					if (click(driver, ele, "", action.BOOLEAN)) {
						appLog.info("clicked on Contact Name : "+contactName);
					} else {
						appLog.error("Not able to clicke on Contact Name: "+contactName);
						return false;
					}
				}
				break;
			} else {
				System.out.println("Not FOund: " + byelementToSearch.toString());
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(" + j + "," + (j = j + 45) + ")",
						getScrollBoxStep1FundDrawdownEmailingGrid(10));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (i == widgetTotalScrollingHeight / 50) {
					return false;
				}
			}
		}
		return true;
	}

	public String CapitalAmountAndManagementFeeAndOtherFee(String commitmentID,String commitmentAmount,String totalCommitmentAmountForDrawDown,String fee){
		// commitmentAmount*fee/totalCommitmentAmountForDrawDown
		double dcommitmentAmount = Double.parseDouble(commitmentAmount);
		double dtotalCommitmentAmountForDrawDown = Double.parseDouble(totalCommitmentAmountForDrawDown);
		double dfee = Double.parseDouble(fee);
		double dd= (dcommitmentAmount * dfee) / dtotalCommitmentAmountForDrawDown;
		return Double.toString(dd);
		
		
	}
	
	public String getCapitalAmountOrCapitalReturnValueCreateDrawdown(String lp, String comm) {
		WebElement ele = FindElement(driver, "(//a[text()='"+lp+"']/../following-sibling::td/a[text()='"+comm+"']/../following-sibling::td/input)[1]", "capital amount value", action.SCROLLANDBOOLEAN, 30);
		if (ele!=null)
			return ele.getAttribute("value").trim();
		else
			return null;
	}
	
	public String getManagementFeeOrDividendsValueCreateDrawdown(String lp, String comm) {
		WebElement ele = FindElement(driver, "(//a[text()='"+lp+"']/../following-sibling::td/a[text()='"+comm+"']/../following-sibling::td/input)[2]", "management fee value", action.SCROLLANDBOOLEAN, 30);
		if (ele!=null)
			return ele.getAttribute("value").trim();
		else
			return null;
	}
	
	public String getTotalValue(String lp, String comm) {
		WebElement ele = FindElement(driver, "//a[text()='"+lp+"']/../following-sibling::td/a[text()='"+comm+"']/../following-sibling::td/span", "total value", action.SCROLLANDBOOLEAN, 30);
		if (ele!=null)
			return ele.getText().trim();
		else
			return null;
	}
	public String getOtherFeeValueOrRealizedGainCreateDrawdown(String lp, String comm) {
		WebElement ele = FindElement(driver, "(//a[text()='"+lp+"']/../following-sibling::td/a[text()='"+comm+"']/../following-sibling::td/input)[3]", "other fee value", action.SCROLLANDBOOLEAN, 30);
		if (ele!=null)
			return ele.getAttribute("value").trim();
		else
			return null;
	}
	public String getOtherProceedsValueCreateD(String lp, String comm) {
		WebElement ele = FindElement(driver, "(//a[text()='"+lp+"']/../following-sibling::td/a[text()='"+comm+"']/../following-sibling::td/input)[4]", "other fee value", action.SCROLLANDBOOLEAN, 30);
		if (ele!=null)
			return ele.getAttribute("value").trim();
		else
			return null;
	}
	public boolean verifyOneRowCreateDrawdownPage(String a[]) {
		boolean flag = true;
		String lp=a[0];
		String commitmentID=a[1];
		String capitalAmount=a[3];
		String managementFee=a[4];
		String OF=a[5];
		String d=getCapitalAmountOrCapitalReturnValueCreateDrawdown(lp, commitmentID);
		d=convertNumberAccordingToFormatWithoutCurrencySymbol(d, "0,000.000");
		capitalAmount=convertNumberAccordingToFormatWithoutCurrencySymbol(capitalAmount, "0,000.000");
		
		if (d.equalsIgnoreCase(capitalAmount)) {
			log(LogStatus.INFO, "capital amount value is successfully verified", YesNo.No);
		}
		else {
			log(LogStatus.ERROR, "could not verify capital amount value"+d+" and "+capitalAmount, YesNo.Yes);
			flag = false;
		}
		d=getManagementFeeOrDividendsValueCreateDrawdown(lp, commitmentID);
		d=convertNumberAccordingToFormatWithoutCurrencySymbol(d, "0,000.000");
		managementFee=convertNumberAccordingToFormatWithoutCurrencySymbol(managementFee, "0,000.000");
		
		if (d.equalsIgnoreCase(managementFee)) {
			log(LogStatus.INFO, "capital amount value is successfully verified", YesNo.No);
		}
		else {
			log(LogStatus.ERROR, "could not verify capital amount value"+d+" and "+managementFee, YesNo.Yes);
			flag = false;
		}
		d=getOtherFeeValueOrRealizedGainCreateDrawdown(lp, commitmentID);
		d=convertNumberAccordingToFormatWithoutCurrencySymbol(d, "0,000.000");
		OF=convertNumberAccordingToFormatWithoutCurrencySymbol(OF, "0,000.000");
		
		if (d.equalsIgnoreCase(OF)) {
			log(LogStatus.INFO, "capital amount value is successfully verified", YesNo.No);
		}
		else {
			log(LogStatus.ERROR, "could not verify capital amount value"+d+" and "+OF, YesNo.Yes);
			flag = false;
		}
		return flag;
	}
	
	public List<WebElement> getEmailProcessingOptionsLableTextList(){
		return FindElements(driver, "(//h3[text()='PROCESSING OPTIONS']/following-sibling::table)[1]//td[@class='td1']", "email prospect step 3 processing options lable list");
	}
	
	public List<WebElement> getEmailProcessingOptionsCheckBoxList(){
		return FindElements(driver, "(//h3[text()='PROCESSING OPTIONS']/following-sibling::table)[1]//td[@class='td2']//input", "email prospect step 3 processing options check box list");
	}
	
	public List<WebElement> getEmailProcessingOptionsCheckBoxSpanList(){
		return FindElements(driver, "(//h3[text()='PROCESSING OPTIONS']/following-sibling::table)[1]//td[@class='td2']//input/../span", "email prospect step 3 processing options check box list");
	}
	
	public boolean createNewDrawdown(String projectName,String mode,String fundName,String capitalAmount,String callDate,String dueDate , int timeOut){
		boolean flag = true;
		WebElement ele;
		FundsPage fund=new FundsPage(driver);
		
		if(click(driver, getNewButton(projectName, timeOut), "fund drawdown button"+projectName, action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on new fund drawdown button ");
			
		}else {
			appLog.error("Not able to click on new button on fund drawdown button page so cannot fund drawdown");
			return false;
		}
		
		switchToFrame(driver, timeOut, getaccessibilityTitleFrame_Lighting(60));
		ThreadSleep(2000);
		if(sendKeys(driver, getSelectFundNameInputBox(timeOut), fundName, "select fund name input box", action.BOOLEAN)){
			
			if(click(driver, getCreatedrawdownButton(timeOut), "create drawdown button", action.BOOLEAN)){
				ThreadSleep(2000);
				switchToDefaultContent(driver);
				refresh(driver);
				ThreadSleep(5000);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())){
					switchToFrame(driver, timeOut, getEmailingFrame_Lighting(60));
				}

				sendKeys(driver, getCapitalAmount( timeOut),capitalAmount, "capital amount", action.SCROLLANDBOOLEAN);
				sendKeys(driver,getCallDateTextbox( timeOut),callDate, "call date", action.SCROLLANDBOOLEAN);
				sendKeys(driver, getDueDate( timeOut),dueDate , "due date", action.BOOLEAN);
				if (click(driver, getSetupCapitalCallsButton(timeOut), "setup capital calls button", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);


					if(click(driver, getGenerateCapitalCallsButton(timeOut), "generate capital call button", action.SCROLLANDBOOLEAN)){
						
						ThreadSleep(3000);
						String xpath;
						
						if (fund.getFundNameInViewMode(projectName, 60) != null) {
							ThreadSleep(2000);
							String fundNameViewMode = getText(driver, fund.getFundNameInViewMode(projectName, 10),
									"Fund Name", action.BOOLEAN);
							appLog.info("fundNameViewMode : "+fundNameViewMode);
							xpath="//div//h1/div/..//*[text()='"+fundName+"']";
							ele = FindElement(driver, xpath, "Heeder : "+fundName, action.BOOLEAN, 30);
							if (ele!=null) {
								appLog.info("Fund drawdown is created and verified.:" + fundName);
								flag=true;
							} else {
								appLog.error("Fund drawdown is not created but not verified.:" + fundName);
							}
						} else {
							appLog.error("Not able to find Fund Name in View Mode");
						}
						
					}else{
						appLog.error("Not able to click on generate capital call button");
						return false;
						
					}

				}else{
					log(LogStatus.ERROR, "could not click on setup capital call", YesNo.Yes);
					sa.assertTrue(false, "could not click on setup capital call");
				}
		
				
			}else{
				
				appLog.error("Not able to click on create drawdown button");
				return false;
			}
			
			
		}else{
			
			appLog.error("Not able to select fund :"+fundName);
			return false;
		}
		
		switchToDefaultContent(driver);
		return flag;
		
	}
}
