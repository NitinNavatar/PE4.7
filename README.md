**Summary**

Deal room integrated with our M&A software, is the only secure virtual deal room today that automates business development, deal origination and deal execution.

*This repository contains Deal Room 1.3.5 automation test scripts which is covered major parts of CRM, External Admin & Target side end to end scenarios.
*Version DR 1.3.5



**How do i get steup?**

* Install git bash in your local system and login big bucket credentials on big bucket portal.
* go to DR Automation module3 project.
* copy the project Https URL/SSH key and get remotely access project in your local system.  
* import that project in eclipse as Git project to maintain collaboration in between remote repository and your local copy of remote repository.
* Java 1.8 or above & Environment variable should be set for JDK.
* Eclipse mars or latest should be available in machine.
* 2MnA orgs are required with deal room 1.3.5 or above version for running phase 1 script.


**Run Test Suite**


* To execute the test case, run the “Executioner.java” file which is available in “com.navatar.launcher” package.
* Before executing the test cases, select the execution status (Yes/No) from Testcase.xlsx sheet. Test case are always executing on basis of execution status set in excel sheet. If you select execution status as “Yes” in excel sheet, then only those test case will execute.
* Complete Phase 1 can be run together.
* Module 1 test script can be run separately but for filter logics scenarios you need to create custom fields on Account & Target page, Use data loader for uploading required data.
* Module 2&3 test script can be run separately but required below setup:-
     1. Super Admin User should be registered for deal room.
     2. Admin User, Manage Approvals & Watermarking settings should be Global and inactive.                            
     3. Same folder template should be created as given in FolderTemp Sheet of TestCase.xlsx file.   
	 4. Provide CRM User1 & CRM User2 details in User sheet of TestCase.xlsx file. 
     5. CRM User1 should be registered with Admin permission of Deal room.
     6. CRM User2 should be registered with deal room access.
	 7. Duplicate template name should not be present.
* Manual setup for running module 2 independently can be skip though running below scenarios of module 1 :-tc003, tc006, tc008, tc009, tc0010, tc0011, tc0012 & tc031.



**Configuration Steup**


* Change "SuperAdminUsername" and "password" according to your Org credentials in credentials.properties file.
* Change “SuperAdminRegistered” No if Super Admin is not registered in credentials.properties file. If you are using old org then “SuperAdminRegistered” should be “yes” in credentials.properties file.
* if you are running test suite/case first time or with new data, so you have to set the "Not Registered" value in Contact test data sheet under Registered column.
* Change the browser value (chrome/Firefox/IE Edge) according to your requirement in credentials.properties file.
* Change TargetUrl & TargetRegistrationUrl as per as connected hub in credentials.properties file.
* Change Gmail Username & Gmail Password As per as requirement. 
* Please don’t make changes on Box Username & Password in credentials.properties file.
* Set outlook as a default app for email in Script running machine.




**Points to be remember before running script**

* Add all Navatar IP in network access so that verification process can be skip at the time of Login.
* At least 22 high volume customer portal license should be available in Hub.
* At least 3 Salesforce licenses should be available in Org.
* At least 3 user space should be available in install package.
* Duplicate data should not be exist in system.
* If you update Account, Deal, Folder Name, File Name then you need to update in file path also.
* Use provided Gmail id or disable security of Gmail account set less secure apps access your account.
* Don’t Add & Delete any row in TestCases.xslx file.
* while uploading new contacts in CSV file, please remember that the contacts id should be same as given in credential.properties file.For e.g.-Suppose if in credentials.Properties Gmail Username is –navataripautomation@gmail.com then contact should be like navataripautomation+randomnumber@gmail.com
* Date format in filter logic test cases should be same as written in filter logic test data excel sheet.
* At the time of uploading contacts.csv, you need to change only email id in contact data excel sheet. Please don’t change the sequence of contacts in contact test data sheet.
*Create custom fields on Account & Target. Upload filter logic data through data loader before tc033. 




**Check Result & output of Script**

* Check test case result from TestCases.xlsx file.
* Check execution report “emailable-report.html” under ‘test-output’ folder.
* Check execution Logs under Reports->Logs folder.
* Check screenshot of failed test cases under “screenshot” folder.  





