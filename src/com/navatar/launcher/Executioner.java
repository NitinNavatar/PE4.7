package com.navatar.launcher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import com.navatar.generic.CommonLib;
import static com.navatar.generic.CommonLib.*;
import com.navatar.generic.ExcelUtils;

public class Executioner {

	public Executioner() {
		// TODO Auto-generated constructor stub
		main(null);
		
	}
	public static void testNgXmlSuite(List<String> excludedMethods, String browser, String platform, String mode,String projectName) {
		
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		List<XmlClass> classes = new ArrayList<XmlClass>();
		List<String> listenerClasses = new ArrayList<String>();
		Map<String, String> parameters = new LinkedHashMap<String, String>();
		parameters.put("browser", browser);
		parameters.put("environment", platform);
		parameters.put("mode", mode);
		parameters.put("projectName", projectName);
		listenerClasses.add("com.navatar.generic.AppListeners");
		XmlSuite suite = new XmlSuite();
		suite.setName("NavatarSuite");
		suite.setListeners(listenerClasses);
		XmlTest test = new XmlTest(suite);
		test.setName("Test");
		test.setParameters(parameters);
		List<XmlClass> xmlClass = new ArrayList<XmlClass>();
		@SuppressWarnings("rawtypes")
		Class[] classesInScriptPackage = CommonLib.getClasses("com.navatar.scripts");
		for(int i = 0; i < classesInScriptPackage.length; i++){
//			System.err.println(classesInScriptPackage[i].toString());/*.split("class com.")[1]).split("scripts.")[1]*/
			if(!ExcelUtils.readData("Modules", excelLabel.Module_Name, (classesInScriptPackage[i].toString().split("class com.")[1]).split("scripts.")[1], excelLabel.Execute).equalsIgnoreCase("No")){
//				xmlClass.add(new XmlClass("com."+classesInScriptPackage[i].toString().split("class com.")[1]));
				XmlClass cl = new XmlClass("com."+classesInScriptPackage[i].toString().split("class com.")[1]);
//				System.err.println(((classesInScriptPackage[i].toString().split("class com.")[1]).split("scripts.")[1]));
				try{
					String priorityNumber = ((classesInScriptPackage[i].toString().split("class com.")[1]).split("scripts.")[1]).split("Module")[1];
					cl.setIndex(Integer.parseInt(priorityNumber));
				} catch (Exception e){
					cl.setIndex(0);
				}
				xmlClass.add(cl);
				
//				Method[] allMethods = classesInScriptPackage[i].getDeclaredMethods();
//				for(Method m : allMethods){
//					System.out.println(m.toGenericString().split(classesInScriptPackage[i].toString().split("class com.")[1])[1].substring(1).substring(0, m.toGenericString().split(classesInScriptPackage[i].toString().split("class com.")[1])[1].substring(1).length()-2));
//					excludedMethods.add(m.toGenericString().split(classesInScriptPackage[i].toString().split("class com.")[1])[1].substring(1).substring(0, m.toGenericString().split(classesInScriptPackage[i].toString().split("class com.")[1])[1].substring(1).length()-2));
//				}
			} else {
				Method[] allMethods = classesInScriptPackage[i].getDeclaredMethods();
				for(Method m : allMethods){
//					System.out.println(m.toGenericString().split(classesInScriptPackage[i].toString().split("class com.")[1])[1].substring(1).substring(0, m.toGenericString().split(classesInScriptPackage[i].toString().split("class com.")[1])[1].substring(1).length()-2));
					excludedMethods.add(m.toGenericString().split(classesInScriptPackage[i].toString().split("class com.")[1])[1].substring(1).substring(0, m.toGenericString().split(classesInScriptPackage[i].toString().split("class com.")[1])[1].substring(1).length()-2));
				}
				
				System.err.println(("Module: "+(classesInScriptPackage[i].toString().split("class com.")[1]).split("scripts.")[1]+" is switched Off"));
				//AppListeners.appLog.info("Module: "+(classesInScriptPackage[i].toString().split("class com.")[1]).split("scripts.")[1]+" is switched Off");
			}
		}
		for(int i = 0; i < xmlClass.size(); i++){
			classes.add(xmlClass.get(i));
			xmlClass.get(i).setExcludedMethods(excludedMethods);
		}
		test.setXmlClasses(classes);
		suites.add(suite);
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		System.err.println(CommonLib.getSystemDate("hh:mm:ss"));
		tng.run();

	}

	public static void main(String[] args) {
		CommonLib.execution();
		String browser = ExcelUtils.readDataFromPropertyFile("Browser");
		String platform = ExcelUtils.readDataFromPropertyFile("Environment");
		String mode = ExcelUtils.readDataFromPropertyFile("Mode");
		String projectName = ExcelUtils.readDataFromPropertyFile("ProjectName");
		testNgXmlSuite(CommonLib.excludedMethods, browser, platform, mode,projectName);

	}

}
