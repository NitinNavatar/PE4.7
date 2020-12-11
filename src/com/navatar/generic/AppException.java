package com.navatar.generic;

import static org.testng.Assert.fail;

import org.testng.SkipException;

/**
 * 
 * @author Ankur Rana
 * @description Customized error, if used can only be catched by throwable class
 */

@SuppressWarnings("serial")
public class AppException extends AssertionError{

	
	public AppException(String message){
		super(message);
	}
	
	public AppException(String message,Exception e){
		super(message);
		CommonLib.failedMethod(e);
	}
//	public AppException(String message, Exception e){
//		super(message, e);
//		IPListeners.ipLog.info("\nReason: "+e.getMessage());
//	}
	
}
