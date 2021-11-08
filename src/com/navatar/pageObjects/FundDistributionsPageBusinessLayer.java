package com.navatar.pageObjects;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;

import java.text.DecimalFormat;
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
import com.navatar.generic.SmokeCommonVariables;
import com.navatar.generic.EnumConstants.EditViewMode;
import com.navatar.generic.EnumConstants.FundDrawdownPageFieldLabelText;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.TabName;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.relevantcodes.extentreports.LogStatus;
import com.navatar.generic.SoftAssert;
public class FundDistributionsPageBusinessLayer extends FundDistributionsPage {

	public FundDistributionsPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean verifyOneRowCreateDistributionsPage(String a[]) {
		boolean flag = true;
		String lp = a[0],commitmentID = a[1];
		String capitalReturn = a[2];String dividend = a[3];
		String realizedGain = a[4];String otherProceed = a[5];
		String totalDist = a[6];
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		if ((lp!=null)&&(commitmentID!=null)) {
			
		
		String d=fd.getCapitalAmountOrCapitalReturnValueCreateDrawdown(lp, commitmentID);
		d=convertNumberAccordingToFormatWithoutCurrencySymbol(d, "0,000.000");
		if (capitalReturn!=null) {
			capitalReturn=convertNumberAccordingToFormatWithoutCurrencySymbol(capitalReturn, "0,000.000");

			if (d.equalsIgnoreCase(capitalReturn)) {
				log(LogStatus.INFO, "capitalReturnvalue is successfully verified", YesNo.No);
			}
			else {
				log(LogStatus.ERROR, "could not verify capitalReturn value"+d+" and "+capitalReturn, YesNo.Yes);
				flag = false;
			}
		}
		else {
			log(LogStatus.ERROR, "could not verify capitalReturn value is null"+d+" and "+capitalReturn, YesNo.Yes);
			flag = false;
		}
		d=fd.getManagementFeeOrDividendsValueCreateDrawdown(lp, commitmentID);
		d=convertNumberAccordingToFormatWithoutCurrencySymbol(d, "0,000.000");
		if (dividend!=null) {
			dividend=convertNumberAccordingToFormatWithoutCurrencySymbol(dividend, "0,000.000");

			if (d.equalsIgnoreCase(dividend)) {
				log(LogStatus.INFO, "dividend value is successfully verified", YesNo.No);
			}
			else {
				log(LogStatus.ERROR, "could not verify dividend value"+d+" and "+dividend, YesNo.Yes);
				flag = false;
			}
		}
		else {
			log(LogStatus.ERROR, "could not verify dividend value is null", YesNo.Yes);
			flag = false;
		}
		d=fd.getOtherFeeValueOrRealizedGainCreateDrawdown(lp, commitmentID);
		d=convertNumberAccordingToFormatWithoutCurrencySymbol(d, "0,000.000");
		if (realizedGain!=null) {
			realizedGain=convertNumberAccordingToFormatWithoutCurrencySymbol(realizedGain, "0,000.000");

			if (d.equalsIgnoreCase(realizedGain)) {
				log(LogStatus.INFO, "realizedGain value is successfully verified", YesNo.No);
			}
			else {
				log(LogStatus.ERROR, "could not verify realizedGain value"+d+" and "+realizedGain, YesNo.Yes);
				flag = false;
			}
		}
		else {
			log(LogStatus.ERROR, "could not verify realizedGain value is null", YesNo.Yes);
			flag = false;
		}
		d=fd.getOtherProceedsValueCreateD(lp, commitmentID);
		d=convertNumberAccordingToFormatWithoutCurrencySymbol(d, "0,000.000");
		if (otherProceed!=null) {
			otherProceed=convertNumberAccordingToFormatWithoutCurrencySymbol(otherProceed, "0,000.000");

			if (d.equalsIgnoreCase(otherProceed)) {
				log(LogStatus.INFO, "dividend value is successfully verified", YesNo.No);
			}
			else {
				log(LogStatus.ERROR, "could not verify otherProceed value"+d+" and "+otherProceed, YesNo.Yes);
				flag = false;
			}
		}
		else {
			log(LogStatus.ERROR, "could not verify otherProceed value is null", YesNo.Yes);
			flag = false;
		}
		d=fd.getTotalValue(lp, commitmentID);
		d=convertNumberAccordingToFormatWithoutCurrencySymbol(d, "0,000.000");
		
		if (totalDist!=null) {
			totalDist=convertNumberAccordingToFormatWithoutCurrencySymbol(totalDist, "0,000.000");
			
			if (d.equalsIgnoreCase(totalDist)) {
				log(LogStatus.INFO, "total dist value is successfully verified", YesNo.No);
			}
			else {
				log(LogStatus.ERROR, "could not verify total dist value"+d+" and "+totalDist, YesNo.Yes);
				flag = false;
			}
		}
		else {
			log(LogStatus.ERROR, "could not verify total dist value is null", YesNo.Yes);
			flag = false;
		}
		}
		else {
			log(LogStatus.ERROR, "could not find commtment or LP in arguments", YesNo.Yes);
			flag = false;
		}
		return flag;
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
			String accountName, String email, String IDID, String commitmentID, String lp, String partnership, boolean emailSentStatus) {
		int j = 0;
		SoftAssert saa = new SoftAssert();
		String workSpaceXpath = "";
		FundDrawdownsPageBusinessLayer fd = new FundDrawdownsPageBusinessLayer(driver);
		
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
			docContactXpath = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+fd.labelColumnIndex(fd.getEmailingGridColumn("Contact Name",30))+"')]//a";
			docAccountXpath = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+fd.labelColumnIndex(fd.getEmailingGridColumn("Account Name",30))+"')]//a";
			docEmailXpath = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+fd.labelColumnIndex(fd.getEmailingGridColumn("Email",30))+"')]//a";
			docCCID = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+fd.labelColumnIndex(fd.getEmailingGridColumn("Investor Distribution ID",30))+"')]";
			docCommimentID = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+fd.labelColumnIndex(fd.getEmailingGridColumn("Commitment ID",30))+"')]";
			docLP = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+fd.labelColumnIndex(fd.getEmailingGridColumn("Limited Partner",30))+"')]";
			docPartnership = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+fd.labelColumnIndex(fd.getEmailingGridColumn("Partnership",30))+"')]";
			docEmailSentStatus = workSpaceXpath + "//span[contains(@id,'aw') and contains(@id,'-cell-"+fd.labelColumnIndex(fd.getEmailingGridColumn("Email Sent Status",30))+"')]//img";

			
			List<WebElement> contactNames = FindElements(driver, docContactXpath, "Contact Name List");

			if (!contactNames.isEmpty()) {
				
				
				List<String> docEmail = fd.findColumnHorizontalScrollAndReturnElementsString(driver,fd.getScrollBoxStep1FundDrawdownEmailingGrid(30), docEmailXpath,"text");
				List<String> doCCID =fd. findColumnHorizontalScrollAndReturnElementsString(driver, fd.getScrollBoxStep1FundDrawdownEmailingGrid(30),docCCID,"text");
				List<String> doCommimentID = fd.findColumnHorizontalScrollAndReturnElementsString(driver,fd.getScrollBoxStep1FundDrawdownEmailingGrid(30), docCommimentID,"text");
				List<String> doLP = fd.findColumnHorizontalScrollAndReturnElementsString(driver, fd.getScrollBoxStep1FundDrawdownEmailingGrid(30),docLP,"text");
				List<String> doPartnership = fd.findColumnHorizontalScrollAndReturnElementsString(driver, fd.getScrollBoxStep1FundDrawdownEmailingGrid(30),docPartnership ,"text");
				List<String> doEmailSentStatus = fd.findColumnHorizontalScrollAndReturnElementsString(driver,fd.getScrollBoxStep1FundDrawdownEmailingGrid(30),docEmailSentStatus,"title");
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
				System.out.println("<<<<<<<<< pASSING "+contact +" "+accountName+" "+email+" "+IDID+" "+commitmentID+" "+lp+" "+partnership +"email sent status "+emailSentStatus+"  <<<<<<<<<");
				appLog.info(">>>>>>> from WebPage "+dcContact+" "+dcAccountName+" "+dcEmail+" "+dcCCID+" "+dcCommitmentID+" "+dcLP+" "+dcPartnership+" <<<<<<<<");
				appLog.error("<<<<<<<<< pASSING "+contact +" "+accountName+" "+email+" "+IDID+" "+commitmentID+" "+lp+" "+partnership +" <<<<<<<<<");

				if (dcContact.equalsIgnoreCase(contact) &&dcAccountName.equalsIgnoreCase(accountName)
						&&dcEmail.equalsIgnoreCase(email)&& dcCCID.equalsIgnoreCase(IDID) 
						&& dcCommitmentID.equalsIgnoreCase(commitmentID) && dcLP.equalsIgnoreCase(lp)
						&& dcPartnership.contains(partnership)
						&& (dcEmailSentStatus == emailSentStatus)
						) {

					appLog.info(contact+" present having account "+accountName+" email "+email+" investor dist id"+IDID + " " + pName.toString() + " : "
							);
					break;
				}
				else if (j == contactNames.size() - 1)
				{
					appLog.error("could not find "+contact+" from account "+accountName+" in emailing grid");
					saa.assertTrue(false, "could not find "+contact+" from account "+accountName+" in emailing grid");
				}
				}
			}
		
				
		return saa;
	}
	int i = 0;
	public String getCapitalReturn(String commitmentAmount, String totalCommitment, String capitalReturn){
		double cAmount=Double.parseDouble(commitmentAmount);
		double tcAmount=Double.parseDouble(totalCommitment);
		double cReturn=Double.parseDouble(capitalReturn);
		return String.valueOf((cAmount/tcAmount)*cReturn);
	}
	
	public String getDividends(String commitmentAmount, String totalCommitment, String dividend){
		double cAmount=Double.parseDouble(commitmentAmount);
		double tcAmount=Double.parseDouble(totalCommitment);
		double dividends=Double.parseDouble(dividend);
		return String.valueOf((cAmount/tcAmount)*dividends);
	}
	
	public String getRealizedGain(String commitmentAmount, String totalCommitment, String realizedGain){
		double cAmount=Double.parseDouble(commitmentAmount);
		double tcAmount=Double.parseDouble(totalCommitment);
		double realizedGains=Double.parseDouble(realizedGain);
		return String.valueOf((cAmount/tcAmount)*realizedGains);
	}
	
	public String getOtherProceeds(String commitmentAmount, String totalCommitment, String otherProceeds){
		double cAmount=Double.parseDouble(commitmentAmount);
		double tcAmount=Double.parseDouble(totalCommitment);
		double otherProceed=Double.parseDouble(otherProceeds);
		return String.valueOf((cAmount/tcAmount)*otherProceed);
	}
	
	public String getTotalInvestorDistributions(String capitalReturn, String dividend, String realizedGain, String otherProceeds){
		double cReturn = Double.parseDouble(capitalReturn);
		String text = new DecimalFormat("0,000.000").format((cReturn));
		cReturn = Double.parseDouble(text.replace(",", ""));
		double dividends = Double.parseDouble(dividend);
		text = new DecimalFormat("0,000.000").format((dividends));
		dividends = Double.parseDouble(text.replace(",", ""));
		double realizedGains=Double.parseDouble(realizedGain);
		text = new DecimalFormat("0,000.000").format((realizedGains));
		realizedGains = Double.parseDouble(text.replace(",", ""));
		double otherProceed=Double.parseDouble(otherProceeds);
		text = new DecimalFormat("0,000.000").format((otherProceed));
		otherProceed = Double.parseDouble(text.replace(",", ""));
//		System.err.println(new DecimalFormat("0,000.000").format((cReturn+dividends+realizedGains+otherProceed)));
		System.err.println("total: "+ (cReturn+dividends+realizedGains+otherProceed));
		return String.valueOf(cReturn+dividends+realizedGains+otherProceed);
	}
	
	
	public boolean createNewDistribution(String projectName,String mode,String fundName,String capitalReturn,String dividend,String date , int timeOut){
		boolean flag = true;
		WebElement ele;
		FundsPage fund=new FundsPage(driver);
		
		if(click(driver, getNewButton(projectName, timeOut), "fund distribution button"+projectName, action.SCROLLANDBOOLEAN)) {
			appLog.info("clicked on new fund distribution button ");
			
		}else {
			appLog.error("Not able to click on new button on fund distribution button page so cannot fund distribution");
			return false;
		}
		
		switchToFrame(driver, timeOut, getaccessibilityTitleFrame_Lighting(60));
		ThreadSleep(2000);
		if(sendKeys(driver, getSelectFundNameInputBox(timeOut), fundName, "select fund name input box", action.BOOLEAN)){
			
			if(click(driver, getCreateDistributionButton(timeOut), "create distribution button", action.BOOLEAN)){
				ThreadSleep(2000);
				switchToDefaultContent(driver);
				refresh(driver);
				ThreadSleep(5000);
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())){
					switchToFrame(driver, timeOut, getEmailingFrame_Lighting(60));
				}

				sendKeys(driver, getCapitalReturn( 30),capitalReturn, "capitalReturn", action.SCROLLANDBOOLEAN);
				sendKeys(driver,getDividends( 30),dividend, "dividends", action.SCROLLANDBOOLEAN);
				sendKeys(driver, getDistributionDate(30),date , "distribution date", action.BOOLEAN);
				if (click(driver, getSetupInvestorDist(30), "setup investor distribution sbutton", action.SCROLLANDBOOLEAN)) {
					ThreadSleep(2000);


					if(click(driver, getGenerateInvDist(timeOut), "generate investor distribution button", action.SCROLLANDBOOLEAN)){
						
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
								appLog.info("Fund distribution is created and verified.:" + fundName);
								flag=true;
							} else {
								appLog.error("Fund distribution is not created but not verified.:" + fundName);
							}
						} else {
							appLog.error("Not able to find Fund Name in View Mode");
						}
						
					}else{
						appLog.error("Not able to click on generate investors distribution button");
						return false;
						
					}

				}else{
					log(LogStatus.ERROR, "could not click on getSetupInvestorDist", YesNo.Yes);
					sa.assertTrue(false, "could not click on getSetupInvestorDist");
				}
		
				
			}else{
				
				appLog.error("Not able to click on create distribution button");
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
