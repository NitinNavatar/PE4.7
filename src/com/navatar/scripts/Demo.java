package com.navatar.scripts;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.CommonLib.getDateAccToTimeZone;
import static com.navatar.generic.CommonLib.previousOrForwardDate;
import static com.navatar.generic.SmokeCommonVariables.todaysDate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.navatar.generic.ExcelUtils;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//ExcelUtils.writedDataFromPropertyFile("BoxUserNmae", "azhaattjvvvvvvvvvvvvvvvvvjjjjjj");
		
		
//		try {
//			FileInputStream in = new FileInputStream(System.getProperty("user.dir") + "//credentials.properties");
//			Properties props = new Properties();
//			props.load(in);
//			in.close();
//
//			FileOutputStream out = new FileOutputStream(System.getProperty("user.dir") + "//credentials.properties");
//			props.setProperty("EmailIdForStatusMail", "azhar");
//			props.store(out, null);
//			out.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// MM/dd/YYYY
//		try {
//			String myDate=("09/08/2020").replace("/", "-");
//			Date date2=new SimpleDateFormat("MM-dd-yyyy").parse(myDate);
//			System.err.println("Date : "+date2);
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		//isPastDate("7/8/2020", "America/Los_Angeles");
		System.err.println(isPastDate("10/09/2020", "America/Los_Angeles"));
		 SimpleDateFormat sdformat = new SimpleDateFormat("MM-dd-yyyy");
	   
		try {
			Date d1 = sdformat.parse(("7/8/2020").replace("/", "-"));
			 Date d2 = sdformat.parse((previousOrForwardDateAccordingToTimeZone(0,"MM/dd/YYYY","America/Los_Angeles")).replace("/", "-"));
		      System.out.println("The date 1 is: " + sdformat.format(d1));
		      System.out.println("The date 2 is: " + sdformat.format(d2));
		      if(d1.compareTo(d2) > 0) {
		         System.out.println("Date 1 occurs after Date 2");
		      } else if(d1.compareTo(d2) < 0) {
		         System.out.println("Date 1 occurs before Date 2");
		      } else if(d1.compareTo(d2) == 0) {
		         System.out.println("Both dates are equal");
		      }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
		
		String date1 = "11/11/2020";
	  
	     
	      
	
		convertStringIntoDate(date1, "MM/dd/YYYY");
		System.err.println("<><><><  : "+convertStringIntoDate(date1, "MM/dd/YYYY"));
		System.err.println("<><><><  : "+convertStringIntoDate(getDateAccToTimeZone("America/Los_Angeles", "MM/dd/YYYY"), "MM/dd/YYYY"));
		System.err.println("Toady : "+getDateAccToTimeZone("America/Los_Angeles", "MM/dd/YYYY"));
	//	String date1 = getDateAccToTimeZone("America/New_York", "MM/dd/YYYY");
		String value = "";
		System.err.println("vvvvvvvv : "+previousOrForwardDateAccordingToTimeZone(0,"MM/dd/YYYY","America/Los_Angeles"));
		System.err.println("vvvvvvvv : "+previousOrForwardDateAccordingToTimeZone(0,"M/d/YYYY","America/Los_Angeles"));
		if (date1.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(0,"MM/dd/YYYY","America/Los_Angeles")) || date1.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(0,"M/d/YYYY","America/Los_Angeles"))) {
			value="Today";
		}else if(date1.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(-1,"MM/dd/YYYY","America/Los_Angeles")) || date1.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(-1,"M/d/YYYY","America/Los_Angeles"))) {
			
			value="Yesterday";
			
		}else if(date1.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(1,"MM/dd/YYYY","America/Los_Angeles")) || date1.equalsIgnoreCase(previousOrForwardDateAccordingToTimeZone(1,"M/d/YYYY","America/Los_Angeles"))){
			
			value="Tomorrow";
			
		} else {
			
			value=findThreeLetterMonthName(date1);
			
		}
		
		System.err.println("><<><<<<  "+date1);
		System.err.println(value);
		System.err.println(date1);
	}
	
	void DateConerter() {
	
		;
	}

}
