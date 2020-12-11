/**
 * 
 */
package com.navatar.generic;

import static com.navatar.generic.EnumConstants.*;


/**
 * @author Ankur Rana
 * 	
 */
public class CommonVariables {
//	public static String abc;

	public static String URL;
	public static String browserToLaunch;
	
		
//	/**
//	 * 
//	 */
	public CommonVariables(Object obj) {
		//TODO Auto-generated constructor stub
		AppListeners.appLog.info("Kindly hold on starting variable intialization........");
		long StartTime = System.currentTimeMillis();
		URL = ExcelUtils.readDataFromPropertyFile("URL");
		 browserToLaunch = ExcelUtils.readDataFromPropertyFile("Browser");
		
		//****************Module 1***************************//
		/*if(obj instanceof Module1DummyClass){
			
		}*/
		 System.err.println("");
		AppListeners.appLog.info("Done with intialization. Enjoy the show.\nTotal Time Taken: "+((System.currentTimeMillis()-StartTime)/1000)+" seconds.");
		
	}
	
}
