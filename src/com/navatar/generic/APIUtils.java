package com.navatar.generic;

import static com.navatar.generic.CommonLib.log;
import static com.navatar.generic.CommonVariables.*;
import static io.restassured.RestAssured.given;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.navatar.generic.EnumConstants.*;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.http.ContentType;

public class APIUtils {

	
	public  String instanceUrl ="https://"+ExcelUtils.readDataFromPropertyFile("URL");;
	public  String accessToken="";
	public APIUtils(String connectedUser) {
		accessToken = connection(connectedUser);
	}
	public APIUtils() {
		accessToken = connection(ExcelUtils.readDataFromPropertyFile("SuperAdminUsername"));
	}
    public  String connection(String connectedUser)
    {
    	String postUrl ="https://test.salesforce.com/services/oauth2/token";
        if(!ExcelUtils.readDataFromPropertyFile("Environment").equalsIgnoreCase(Environment.Sandbox.toString())) {
        	 postUrl ="https://login.salesforce.com/services/oauth2/token";

        }
    	return given().urlEncodingEnabled(true)
        .param("username", connectedUser)
        .param("password", ExcelUtils.readDataFromPropertyFile("password"))
        .param("client_id", ExcelUtils.readDataFromPropertyFile("ConsumerKey").trim())
        .param("client_secret", ExcelUtils.readDataFromPropertyFile("ConsumerSecretKey").trim())
        .param("grant_type", "password")
        .header("Accept", "application/json")
        .when().
        post(postUrl).
        then().
        assertThat().statusCode(200).log().body().extract().path("access_token");
        
    }
    
    public  String getQueryResult(String soql, String fieldName)
    {
        System.out.println("SOQL to execute is: "+soql);
        List<Map<String, Object>> recordsArray = 
            given().
            contentType(ContentType.JSON).
            header("Authorization","Bearer "+ accessToken).
            get(instanceUrl+"/services/data/v58.0/query?q="+soql+"").
            then().assertThat().statusCode(200).extract().path("records");
           
        String fieldValue = (String) recordsArray.get(0).get(fieldName);
        return fieldValue;   
    }    
    
    public  String getObjectRecordId(String obejctApiName,String recordName)
    {
    	String soql ="SELECT Id FROM "+obejctApiName+" WHERE Name = '"+recordName+"'";
        System.out.println("SOQL to execute is: "+soql);
        List<Map<String, Object>> recordsArray = 
            given().
            contentType(ContentType.JSON).
            header("Authorization","Bearer "+ accessToken).
            get(instanceUrl+"/services/data/v58.0/query?q="+soql+"").
            then().assertThat().statusCode(200).extract().path("records");
           
        String fieldValue = (String) recordsArray.get(0).get("Id");
        return fieldValue;   
    }    
    
    public  String getTaskObjectRecordId(String recordName)
    {
    	String soql ="SELECT Id FROM Task WHERE Subject = '"+recordName+"'";
        System.out.println("SOQL to execute is: "+soql);
        List<Map<String, Object>> recordsArray = 
            given().
            contentType(ContentType.JSON).
            header("Authorization","Bearer "+ accessToken).
            get(instanceUrl+"/services/data/v58.0/query?q="+soql+"").
            then().assertThat().statusCode(200).extract().path("records");
           
        String fieldValue = (String) recordsArray.get(0).get("Id");
        return fieldValue;   
    }    
    
    public  String getEventObjectRecordId(String recordName)
    {
    	String soql ="SELECT Id FROM Event WHERE Subject = '"+recordName+"'";
        System.out.println("SOQL to execute is: "+soql);
        List<Map<String, Object>> recordsArray = 
            given().
            contentType(ContentType.JSON).
            header("Authorization","Bearer "+ accessToken).
            get(instanceUrl+"/services/data/v58.0/query?q="+soql+"").
            then().assertThat().statusCode(200).extract().path("records");
           
        String fieldValue = (String) recordsArray.get(0).get("Id");
        return fieldValue;   
    }    
  
    public  String getObjectActiveRecordTypeId(String objectName,String recordTypeName) {
    
    	String soql ="SELECT Id FROM RecordType WHERE IsActive = true AND SobjectType = '"+objectName+"' AND Name = '"+recordTypeName+"'";
        System.out.println("SOQL to execute is: "+soql);
        
        List<Map<String, Object>> recordsArray = 
            given().
            contentType(ContentType.JSON).
            header("Authorization","Bearer "+ accessToken).
            get(instanceUrl+"/services/data/v58.0/query?q="+soql+"").
            then().assertThat().statusCode(200).extract().path("records");
           
        String fieldValue = (String) recordsArray.get(0).get("Id");
        return fieldValue;   
    }  

    public String createObejectRecordByAPI(String objectAPIName,Map<String,Object> mapper) {
    


    // Map<String,Object> mapper = new HashMap<String,Object>();

//     for(String[] labelAndValue:data) {
//     	mapper.put(labelAndValue[0], labelAndValue[1]);
//     	System.out.println("Added Account Data label'"+labelAndValue[0]+"' with value '"+labelAndValue[1]+"'");
//     
//     }
     JSONObject requester = new JSONObject(mapper); 

     System.out.println("Data JSON file is: "+requester.toString());
	log(LogStatus.INFO, "Data JSON file is: "+requester.toString(), YesNo.No);

     return
 		 given().
 		 contentType(ContentType.JSON).
 		 accept(ContentType.JSON).
 		 header("Authorization", "Bearer " +accessToken).
 		 header("Content-Type","application/json").
 		 body(requester.toString()).
 		 when().post(instanceUrl+"/services/data/v58.0/sobjects/"+objectAPIName+"").
 		 then().statusCode(201).log().body().extract().path("id");
    } 
   
    public  String convertDate(String strDate) {
	
	    //for strdate = 2017 July 25

	    DateTimeFormatter f = new DateTimeFormatterBuilder().appendPattern("MM/dd/yyyy")
	                                        .toFormatter();

	    LocalDate parsedDate = LocalDate.parse(strDate, f);
	    DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    String newDate = parsedDate.format(f2);

	    return newDate;
	}

//Data of file
    public void AccountObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);

		for (int i = 1; i < row+1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j);
					String value = ExcelUtils.readData(filePath, sheetName, i , j);
					if (label.equals("RecordTypeId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
						String RT = getObjectActiveRecordTypeId("Account", value);
						data.put(label, RT);
						System.out.println("Added Data for account :" + i + " Label :" + label + " with value :" + value +"Replacing :"+RT);
						log(LogStatus.INFO, "Added Data for account :" + i + " Label :" + label + " with value :" + value +"Replacing :"+RT, YesNo.No);
						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for account :" + i + " Label :" + label + " with value :" + value +"Replacing :"+date);
							log(LogStatus.INFO, "Added Data for account :" + i + " Label :" + label + " with value :" + value +"Replacing :"+date, YesNo.No);

						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
						data.put(label, value);
						System.out.println("Added Data for account :" + i + " Label :" + label + " with value :" + value);
						log(LogStatus.INFO, "Added Data for account :" + i + " Label :" + label + " with value :" + value, YesNo.No);

						}
					}

				}

				createObejectRecordByAPI("Account", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for account :" + i );
				log(LogStatus.INFO, "Data already created for account :" + i , YesNo.No);

			}
		}
		
		
	}
	
	public void ContactObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath,sheetName , i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

						String RT = getObjectActiveRecordTypeId("Contact", value);
						data.put(label, RT);
						System.out.println("Added Data for Contact :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
						log(LogStatus.INFO, "Added Data for Contact :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);
						}
					} else if (label.equals("AccountId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
						String account = getObjectRecordId("Account", value);
						data.put(label, account);
						System.out.println("Added Data for Contact :" + i + " Label :" + label + " with value :" + value+" Replacing :"+account);
						log(LogStatus.INFO, "Added Data for Contact :" + i + " Label :" + label + " with value :" + value+" Replacing :"+account, YesNo.No);
						
						}
					}else if (label.contains("Date") || label.contains("date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for Contact :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date);
							log(LogStatus.INFO, "Added Data for Contact :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date, YesNo.No);
							
						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
						data.put(label, value);
						System.out.println("Added Data for Contact :" + i + " Label :" + label + " with value :" + value);
						log(LogStatus.INFO, "Added Data for Contact :" + i + " Label :" + label + " with value :" + value, YesNo.No);
						
						}
					}

				}

				createObejectRecordByAPI("Contact", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Contact :" + i);
				log(LogStatus.INFO,"Data already created for Contact :" + i, YesNo.No);

			}
		}

	}
	
	public void DealObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {

						if (value != null && !value.equalsIgnoreCase("")  && !value.isEmpty()) {
							String RT = getObjectActiveRecordTypeId("navpeII__Pipeline__c", value);
							data.put(label, RT);
							System.out.println("Added Data for Deal :" + i + " Label :" + label + " with value :" + value +"Replacing:"+RT);
							 log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value:"+value, YesNo.No);

						}
					} else if (label.equals("AccountId") || label.equals("navpeII__Company__c") || label.equals("navpeII__Institution__c")) {
						if (value != null && !value.equalsIgnoreCase("")  && !value.isEmpty()) {
							String accoundId = getObjectRecordId("Account", value);
							data.put(label, accoundId);
							System.out.println("Added Data for Deal :" + i + " Label :" + label + " with value :" + value +"Replacing:"+accoundId);
							 log(LogStatus.INFO, "Added Data for Deal :" + i + " Label :" + label + " with value :" + value +"Replacing:"+accoundId, YesNo.No);

						}
					}else if (label.equals("navpeII__Source_Company__c")) {
						if (value != null && !value.equalsIgnoreCase("")  && !value.isEmpty()) {
						String account = getObjectRecordId("Account", value);
						data.put(label, account);
						System.out.println("Added Data for Deal :" + i + " Label :" + label + " with value :" + value +"Replacing:"+account);
						 log(LogStatus.INFO, "Added Data for Deal :" + i + " Label :" + label + " with value :" + value +"Replacing:"+account, YesNo.No);

						}
					}
					else if (label.equals("navpeII__Source_Contact__c")) {
						if (value != null && !value.equalsIgnoreCase("")  && !value.isEmpty()) {
						String account = getObjectRecordId("Contact", value);
						data.put(label, account);
						System.out.println("Added Data for Deal :" + i + " Label :" + label + " with value :" + value +"Replacing:"+account);
						 log(LogStatus.INFO, "Added Data for Deal :" + i + " Label :" + label + " with value :" + value +"Replacing:"+account, YesNo.No);

						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("")  && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for Deal :" + i + " Label :" + label + " with value :" + value +"Replacing:"+date);
							 log(LogStatus.INFO, "Added Data for Deal :" + i + " Label :" + label + " with value :" + value +"Replacing:"+date, YesNo.No);

						}
					} else {
						if (value != null && !value.equalsIgnoreCase("")  && !value.isEmpty()) {
							data.put(label, value);
							System.out.println(
									"Added Data for Deal :" + i + " Label :" + label + " with value :" + value);
							 log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value:"+value, YesNo.No);

						}
					}

				}

				createObejectRecordByAPI("navpeII__Pipeline__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Deal :" + i);
				log(LogStatus.INFO, "Data already created for Deal :" + i, YesNo.No);
			}
		}

	}

	public void FundObjectDataUpload(String filePath,String sheetName) {

		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);
		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {
					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {
							if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
								String RT = getObjectActiveRecordTypeId("navpeII__Fund__c", value);
								data.put(label, RT);
								System.out.println("Added Data for Fund :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
								 log(LogStatus.INFO,"Added Data for Fund :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);
							}
						}else if (label.contains("Date")) {
							if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

								String date = convertDate(value);
								data.put(label, date);
								System.out.println("Added Data for Fund :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date);
								 log(LogStatus.INFO,"Added Data for Fund :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date, YesNo.No);
							
							}
						} else {
							if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
								data.put(label, value);
								System.out.println(
										"Added Data for Fund :" + i + " Label :" + label + " with value :" + value);
								 log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value:"+value, YesNo.No);
							}
						}
					}
					createObejectRecordByAPI("navpeII__Fund__c", data);
					ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
					data.clear();
				} else {
					System.out.println("Data already created for Fund :" + i);
					log(LogStatus.INFO, "Data already created for Fund :" + i, YesNo.No);
				}
			}
		}
	
	public void DealTeamObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();

		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {

						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String RT = getObjectActiveRecordTypeId("navpeII__Deal_Team__c", value);
							data.put(label, RT);
							System.out.println(
									"Added Data for Deal Team:" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
							 log(LogStatus.INFO, 
										"Added Data for Deal Team:" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);

						}
					} else if (label.contains("Contact")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String accoundId = getObjectRecordId("Contact", value);
							data.put(label, accoundId);
							System.out.println(
									"Added Data for Deal Team:" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId);
							 log(LogStatus.INFO, 
										"Added Data for Deal Team:" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId, YesNo.No);


						}
					}else if (label.contains("Deal")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String accoundId = getObjectRecordId("navpeII__Pipeline__c", value);
							data.put(label, accoundId);
							System.out.println(
									"Added Data for Deal Team:" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId);
							 log(LogStatus.INFO, 
										"Added Data for Deal Team:" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId, YesNo.No);


						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Deal Team:" + i + " Label :" + label + " with value :" + value+" Replacing :"+date);
							 log(LogStatus.INFO, 
										"Added Data for Deal Team:" + i + " Label :" + label + " with value :" + value+" Replacing :"+date, YesNo.No);

						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println(
									"Added Data for Deal Team :" + i + " Label :" + label + " with value :" + value);
							 log(LogStatus.INFO, "Added Data for Deal Team :"+i +" Label :"+label +" with value:"+value, YesNo.No);

						}
					}

				}

				createObejectRecordByAPI("navpeII__Deal_Team__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Deal Team :" + i);
				log(LogStatus.INFO, "Data already created for Deal Team :" + i, YesNo.No);
			}
		}

	}
	
	public void FundTeamObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String RT = getObjectActiveRecordTypeId("Fund_Team__c", value);
							data.put(label, RT);
							System.out.println("Added Data for Fund Team:" + i + " Label :" + label + " with value :" + value +" Replacing :"+RT);
							 log(LogStatus.INFO, "Added Data for Fund Team:" + i + " Label :" + label + " with value :" + value +" Replacing :"+RT, YesNo.No);
							}
						} else if (label.equals("Fund__c")) {
								if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
									String RT = getObjectRecordId("navpeII__Fund__c", value);
									data.put(label, RT);
									System.out.println("Added Data for Fund Team:" + i + " Label :" + label + " with value :" + value +" Replacing :"+RT);
									 log(LogStatus.INFO, "Added Data for Fund Team:" + i + " Label :" + label + " with value :" + value +" Replacing :"+RT, YesNo.No);
									

								}
					}else if (label.equals("Fund_Team_Member__c")) {
								if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

									if(value.equalsIgnoreCase("User1")) {

										String query="SELECT Id FROM User WHERE IsActive = true  AND FirstName = 'CRM1'";

										String user1=getQueryResult(query, "Id");

										data.put(label, user1);

										System.out.println("Added Data for Fund Team:" + i + " Label :" + label + " with value :" + value +" Replacing :"+user1);
										 log(LogStatus.INFO, "Added Data for Fund Team:" + i + " Label :" + label + " with value :" + value +" Replacing :"+user1, YesNo.No);
										

									}else if(value.equalsIgnoreCase("User2")) {

										String query="SELECT Id FROM User WHERE IsActive = true  AND FirstName = 'CRM2'";

										String user2=getQueryResult(query, "Id");

										data.put(label, user2);

										System.out.println("Added Data for Fund Team:" + i + " Label :" + label + " with value :" + value +" Replacing :"+user2);
										 log(LogStatus.INFO, "Added Data for Fund Team:" + i + " Label :" + label + " with value :" + value +" Replacing :"+user2, YesNo.No);
										

									}else {

										String query="SELECT Id FROM User WHERE IsActive = true AND Username = '"+ExcelUtils.readDataFromPropertyFile("SuperAdminUsername").trim()+"'";
										String admin=getQueryResult(query, "Id");
										System.out.println("Added Data for Fund Team:" + i + " Label :" + label + " with value :" + value +" Replacing :"+admin);
										 log(LogStatus.INFO, "Added Data for Fund Team:" + i + " Label :" + label + " with value :" + value +" Replacing :"+admin, YesNo.No);
										
									}
								}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println("Added Data for Fund Team:" + i + " Label :" + label + " with value :" + value);
							 log(LogStatus.INFO, "Added Data for Fund Team:" + i + " Label :" + label + " with value :" + value, YesNo.No);
							

						}
					}
				}
				createObejectRecordByAPI("Fund_Team__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Fund Team :" + i);
				log(LogStatus.INFO, "Data already created for Fund Team :" + i, YesNo.No);
			}
		}

	}
	
	public void ClipObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {

						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String RT = getObjectActiveRecordTypeId("navpeII__Clip__c", value);
							data.put(label, RT);
							System.out.println("Added Data for clip :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
							 log(LogStatus.INFO,"Added Data for clip :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);

						}
					} else if (label.equals("AccountId") || label.equals("navmnaI__Account__c")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String accoundId = getObjectRecordId("Account", value);
							data.put(label, accoundId);
							System.out.println("Added Data for clip :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId);
							 log(LogStatus.INFO,"Added Data for clip :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId, YesNo.No);


						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for clip :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date);
							 log(LogStatus.INFO,"Added Data for clip :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date, YesNo.No);

						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println(
									"Added Data for clip :" + i + " Label :" + label + " with value :" + value);
							 log(LogStatus.INFO, "Added Data for Clip :"+i +" Label :"+label +" with value:"+value, YesNo.No);

						}
					}

				}

				createObejectRecordByAPI("navpeII__Clip__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Clip :" + i);
				log(LogStatus.INFO, "Data already created for Clip :" + i, YesNo.No);
			
			}
		}

	}
	
	public void ActionObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {

						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String RT = getObjectActiveRecordTypeId("navpeII__SDG_Action__c", value);
							data.put(label, RT);
							System.out.println("Added Data for Action :" + i + " Label :" + label + " with value :" + value+"Replacing :"+RT);
							 log(LogStatus.INFO, "Added Data for Action :" + i + " Label :" + label + " with value :" + value+"Replacing :"+RT, YesNo.No);

						}
					} else if (label.equals("sdg__c") || label.equals("navpeII__sdg__c")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String accoundId = getObjectRecordId("navpeII__SDG__c", value);
							data.put(label, accoundId);
							System.out.println("Added Data for Action :" + i + " Label :" + label + " with value :" + value+"Replacing :"+accoundId);
							 log(LogStatus.INFO, "Added Data for Action :" + i + " Label :" + label + " with value :" + value+"Replacing :"+accoundId, YesNo.No);


						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for Action :" + i + " Label :" + label + " with value :" + value+"Replacing :"+date);
							 log(LogStatus.INFO, "Added Data for Action :" + i + " Label :" + label + " with value :" + value+"Replacing :"+date, YesNo.No);

						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println(
									"Added Data for Action :" + i + " Label :" + label + " with value :" + value);
							 log(LogStatus.INFO, "Added Data for Action :"+i +" Label :"+label +" with value:"+value, YesNo.No);

						}
					}

				}

				createObejectRecordByAPI("navpeII__SDG_Action__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Action :" + i);
				log(LogStatus.INFO, "Data already created for Action :" + i, YesNo.No);
			}
		}

	}
	
	public void FieldObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {

						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String RT = getObjectActiveRecordTypeId("navpeII__SDG_Field__c", value);
							data.put(label, RT);
							System.out.println("Added Data for Field :" + i + " Label :" + label + " with value :" + value +" Replacing :"+RT);
							 log(LogStatus.INFO, "Added Data for Field :" + i + " Label :" + label + " with value :" + value +" Replacing :"+RT, YesNo.No);

						}
					} else if (label.equals("sdg__c") || label.equals("navpeII__sdg__c")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String accoundId = getObjectRecordId("navpeII__SDG__c", value);
							data.put(label, accoundId);
							System.out.println("Added Data for Field :" + i + " Label :" + label + " with value :" + value +" Replacing :"+accoundId);
							 log(LogStatus.INFO, "Added Data for Field :" + i + " Label :" + label + " with value :" + value +" Replacing :"+accoundId, YesNo.No);


						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for Field :" + i + " Label :" + label + " with value :" + value +" Replacing :"+date);
							 log(LogStatus.INFO, "Added Data for Field :" + i + " Label :" + label + " with value :" + value +" Replacing :"+date, YesNo.No);

						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println(
									"Added Data for Field :" + i + " Label :" + label + " with value :" + value);
							 log(LogStatus.INFO, "Added Data for field :"+i +" Label :"+label +" with value:"+value, YesNo.No);

						}
					}

				}

				createObejectRecordByAPI("navpeII__SDG_Field__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Action :" + i);
				log(LogStatus.INFO, "Data already created for Action :" + i, YesNo.No);
			}
		}

	}
	
	public void SDGObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {

						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String RT = getObjectActiveRecordTypeId("navpeII__SDG__c", value);
							data.put(label, RT);
							System.out.println("Added Data for SDG :" + i + " Label :" + label + " with value :" + value +" Replacing :"+RT);
							 log(LogStatus.INFO, "Added Data for SDG :" + i + " Label :" + label + " with value :" + value +" Replacing :"+RT, YesNo.No);

						}
					} else if (label.equals("AccountId") || label.equals("navmnaI__Account__c")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String accoundId = getObjectRecordId("Account", value);
							data.put(label, accoundId);
							System.out.println("Added Data for SDG :" + i + " Label :" + label + " with value :" + value +" Replacing :"+accoundId);
							 log(LogStatus.INFO, "Added Data for SDG :" + i + " Label :" + label + " with value :" + value +" Replacing :"+accoundId, YesNo.No);


						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for SDG :" + i + " Label :" + label + " with value :" + value +" Replacing :"+date);
							 log(LogStatus.INFO, "Added Data for SDG :" + i + " Label :" + label + " with value :" + value +" Replacing :"+date, YesNo.No);

						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println(
									"Added Data for SDG :" + i + " Label :" + label + " with value :" + value);
							 log(LogStatus.INFO, "Added Data for SDG :"+i +" Label :"+label +" with value:"+value, YesNo.No);

						}
					}

				}

				createObejectRecordByAPI("navpeII__SDG__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for SDG :" + i);
				log(LogStatus.INFO, "Data already created for SDG :", YesNo.No);
			}
		}

	}
	
	public void ClipRelationObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i =1; i < row + 1; i++) {
			String id = "";
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {

						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String RT = getObjectActiveRecordTypeId("navpeII__Clip__c", value);
							data.put(label, RT);
							System.out.println("Added Data for clip relation :" + i + " Label :" + label+ " with value :" + value+" Replacing :"+RT);
							 log(LogStatus.INFO, "Added Data for clip relation :" + i + " Label :" + label+ " with value :" + value+" Replacing :"+RT, YesNo.No);

						}
					} else if (label.contains("Clip")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String RT = getObjectRecordId("navpeII__Clip__c", value);
							data.put(label, RT);
							System.out.println("Added Data for clip relation :" + i + " Label :" + label+ " with value :" + value+" Replacing :"+RT);
							 log(LogStatus.INFO, "Added Data for clip relation :" + i + " Label :" + label+ " with value :" + value+" Replacing :"+RT, YesNo.No);


						}
					} else if (label.contains("<break>")) {
						String relatedAss = label.split("<break>")[0];
						String type = label.split("<break>")[1];

						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String newId = "";
							if (id != null && !id.equals("")) {
								id = id + ",";
							}
							switch (type) {
							case "Account":
								newId = getObjectRecordId("Account", value);

								break;
							case "Contact":
								newId = getObjectRecordId("Contact", value);

								break;
							case "Deal":
								newId = getObjectRecordId("navpeII__Pipeline__c", value);

								break;
							case "Target":
								newId = getObjectRecordId("navpeII__Fundraising__c", value);

								break;
							case "Theme":
								newId = getObjectRecordId("navpeII__Theme__c", value);

								break;
							default:
								break;
							}
							id = id + newId;
							data.put(relatedAss, id);
							System.out.println("Added Data for clip relation :" + i + " Label :" + label+ " with value :" + value+" Replacing :"+id);
							 log(LogStatus.INFO, "Added Data for clip relation :" + i + " Label :" + label+ " with value :" + value+" Replacing :"+id, YesNo.No);


						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for clip relation :" + i + " Label :" + label+ " with value :" + value+" Replacing :"+date);
							 log(LogStatus.INFO, "Added Data for clip relation :" + i + " Label :" + label+ " with value :" + value+" Replacing :"+date, YesNo.No);

						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println("Added Data for clip relation :" + i + " Label :" + label+ " with value :" + value);
							 log(LogStatus.INFO, "Added Data for clip relation :" + i + " Label :" + label+ " with value :" + value, YesNo.No);


						}
					}

				}

				createObejectRecordByAPI("navpeII__Clip_Relation__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Clip Relation :" + i);
				log(LogStatus.INFO, "Data already created for Clip Relation :" + i, YesNo.No);
			}
		}

	}
	
	@Test
	public void ThemeObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {

						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String RT = getObjectActiveRecordTypeId("navpeII__Theme__c", value);
							data.put(label, RT);
							System.out.println("Added Data for Theme :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
							 log(LogStatus.INFO, "Added Data for Theme :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);

						}
					} else if (label.equals("AccountId") || label.equals("navmnaI__Account__c")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String accoundId = getObjectRecordId("Account", value);
							data.put(label, accoundId);
							System.out.println("Added Data for Theme :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId);
							 log(LogStatus.INFO, "Added Data for Theme :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId, YesNo.No);


						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for Theme :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date);
							 log(LogStatus.INFO, "Added Data for Theme :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date, YesNo.No);

						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println("Added Data for Theme :" + i + " Label :" + label + " with value :" + value);
							 log(LogStatus.INFO, "Added Data for Theme :" + i + " Label :" + label + " with value :" + value, YesNo.No);


						}
					}

				}

				createObejectRecordByAPI("navpeII__Theme__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Theme :" + i);
				log(LogStatus.INFO, "Data already created for Theme :" + i, YesNo.No);
			}
		}

	}
	
	@Test
	public void ThemeRelationObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String id = "";
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {

						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String RT = getObjectActiveRecordTypeId("navpeII__Theme_Relation__c", value);
							data.put(label, RT);
							System.out.println("Added Data for Theme Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
							 log(LogStatus.INFO, "Added Data for Theme Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);


						}
					} else if (label.equals("navpeII__Theme__c")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String RT = getObjectRecordId("navpeII__Theme__c", value);
							data.put(label, RT);
							System.out.println("Added Data for Theme Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
							 log(LogStatus.INFO, "Added Data for Theme Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);


						}
					} else if (label.contains("<break>")) {
						String relatedAss = label.split("<break>")[0];
						String type = label.split("<break>")[1];

						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String newId = "";
							if (id != null && !id.equals("")) {
								id = id + ",";
							}
							switch (type) {
							case "Account":
								newId = getObjectRecordId("Account", value);

								break;
							case "Contact":
								newId = getObjectRecordId("Contact", value);

								break;
							case "Deal":
								newId = getObjectRecordId("navpeII__Pipeline__c", value);

								break;
							case "Fundraising":
								newId = getObjectRecordId("navpeII__Fundraising__c", value);

								break;
							case "Theme":
								newId = getObjectRecordId("navpeII__Theme__c", value);

								break;
							case "Clip":
								newId = getObjectRecordId("navpeII__Clip__c", value);

								break;
							default:
								break;
							}
							id = id + newId;
							data.put(relatedAss, id);
							System.out.println("Added Data for Theme Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+id);
							 log(LogStatus.INFO, "Added Data for Theme Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+id, YesNo.No);

						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for Theme Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date);
							 log(LogStatus.INFO, "Added Data for Theme Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date, YesNo.No);

						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println("Added Data for Theme Relation :" + i + " Label :" + label + " with value :" + value);
							 log(LogStatus.INFO, "Added Data for Theme Relation :" + i + " Label :" + label + " with value :" + value, YesNo.No);


						}
					}

				}

				createObejectRecordByAPI("navpeII__Theme_Relation__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Theme Relation :" + i);
				log(LogStatus.INFO, "Data already created for Theme Relation :" + i, YesNo.No);
			}
		}

	}
	
	public void TargetObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

						String RT = getObjectActiveRecordTypeId("navpeII__Fundraising__c", value);
						data.put(label, RT);
						System.out.println("Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
						 log(LogStatus.INFO, "Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);

						}
					} else if (label.equals("AccountId") || label.equals("navpeII__Legal_Name__c")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

						String accoundId = getObjectRecordId("Account", value);
						data.put(label, accoundId);
						System.out.println("Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId);
						 log(LogStatus.INFO, "Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId, YesNo.No);
						}
					} else if (label.equals("navpeII__Fund_Name__c")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

						String dealId = getObjectRecordId("navpeII__Fund__c", value);
						data.put(label, dealId);
						System.out.println("Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value+" Replacing :"+dealId);
						 log(LogStatus.INFO, "Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value+" Replacing :"+dealId, YesNo.No);
						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date);
							 log(LogStatus.INFO, "Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date, YesNo.No);

						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

						data.put(label, value);
						System.out.println("Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value);
						 log(LogStatus.INFO, "Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value, YesNo.No);
						}
					}
				}

				createObejectRecordByAPI("navpeII__Fundraising__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Fundraising :" + i);
				log(LogStatus.INFO, "Data already created for Fundraising :" + i, YesNo.No);
			}
		}

	}
	
	public void TargetContactRoleObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
						String RT = getObjectActiveRecordTypeId("navpeII__Fundraising__c", value);
						data.put(label, RT);
						System.out.println("Added Data for Target Role :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
						 log(LogStatus.INFO, "Added Data for Target Role :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);
						}
					} else if (label.contains("Fundraising")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
						String accoundId = getObjectRecordId("navpeII__Fundraising__c", value);
						data.put(label, accoundId);
						System.out.println("Added Data for Target Role :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId);
						 log(LogStatus.INFO, "Added Data for Target Role :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId, YesNo.No);
						}
					} else if (label.contains("Contact")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
						String dealId = getObjectRecordId("Contact", value);
						data.put(label, dealId);
						System.out.println("Added Data for Target Role :" + i + " Label :" + label + " with value :" + value+" Replacing :"+dealId);
						 log(LogStatus.INFO, "Added Data for Target Role :" + i + " Label :" + label + " with value :" + value+" Replacing :"+dealId, YesNo.No);
						
						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for Target Role :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date);
							 log(LogStatus.INFO, "Added Data for Target Role :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date, YesNo.No);
							
						}
					}  else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
						data.put(label, value);
						System.out.println("Added Data for Target Role :" + i + " Label :" + label + " with value :" + value);
						 log(LogStatus.INFO, "Added Data for Target Role :" + i + " Label :" + label + " with value :" + value, YesNo.No);
						
						}
					}


				}

				createObejectRecordByAPI("navpeII__Fundraising_Contact__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Target Role :" + i);
				log(LogStatus.INFO, "Data already created for Target Role :" + i, YesNo.No);
			}
		}

	}
	

	public void EventWorkAroundObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
						String RT = getObjectActiveRecordTypeId("Event_WorkAround__c", value);
						data.put(label, RT);
						System.out.println("Added Data for Event  workaround :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
						 log(LogStatus.INFO, "Added Data for Event  workaround :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);
						}
					} else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for Event  workaround :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date);
							 log(LogStatus.INFO, "Added Data for Event  workaround :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date, YesNo.No);
							
						}
					}  else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
						data.put(label, value);
						System.out.println("Added Data for Event  workaround :" + i + " Label :" + label + " with value :" + value);
						 log(LogStatus.INFO, "Added Data for Event  workaround :" + i + " Label :" + label + " with value :" + value, YesNo.No);
						}
					}


				}

				createObejectRecordByAPI("Event_WorkAround__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Event WorkAround :" + i);
				log(LogStatus.INFO, "Data already created for Event WorkAround :" + i, YesNo.No);
			}
		}

	}
	
	
	public void TaskObjectDataUpload(String filePath,String sheetName) {
	
		Map<String, Object> data = new HashMap<String, Object>();
		

		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String id = "";
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String RT = getObjectActiveRecordTypeId("Task", value);
							data.put(label, RT);
							System.out.println("Added Data for Task :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
							 log(LogStatus.INFO, "Added Data for Task :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);
							

						}
					} else if (label.contains("Related")) {
						String relatedAss = label.split("<>")[0];
						String type = label.split("<>")[1];

						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String newId = "";
							if (id != null && !id.equals("")) {
								id = id + ",";
							}
							switch (type) {
							case "Account":
								newId = getObjectRecordId("Account", value);

								break;

							case "Deal":
								newId = getObjectRecordId("navpeII__Pipeline__c", value);

								break;
							case "Fundraising":
								newId = getObjectRecordId("navpeII__Fundraising__c", value);
								break;
							case "Fund":
								newId = getObjectRecordId("navpeII__Fund__c", value);
								break;
							case "Theme":
								newId = getObjectRecordId("navpeII__Theme__c", value);

								break;
							default:
								break;
							}
							id = id + newId;
							data.put(relatedAss, id);
							System.out.println("Added Data for Task :" + i + " Label :" + label + " with value :" + value+" Replacing :"+id);
							 log(LogStatus.INFO, "Added Data for Task :" + i + " Label :" + label + " with value :" + value+" Replacing :"+id, YesNo.No);
							


						}
					} else if (label.equals("OwnerId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							if(value.equalsIgnoreCase("CRM1")) {
								String query="SELECT Id FROM User WHERE IsActive = true  AND FirstName = 'CRM1'";
								String user1=getQueryResult(query, "Id");
								data.put(label, user1);
								System.out.println("Added Data for Task :" + i + " Label :" + label + " with value :" + value+" Replacing :"+user1);
								 log(LogStatus.INFO, "Added Data for Task :" + i + " Label :" + label + " with value :" + value+" Replacing :"+user1, YesNo.No);
								
							}else if(value.equalsIgnoreCase("CRM2")) {
								String query="SELECT Id FROM User WHERE IsActive = true  AND FirstName = 'CRM2'";
								String user2=getQueryResult(query, "Id");
								data.put(label, user2);
								System.out.println("Added Data for Task :" + i + " Label :" + label + " with value :" + value+" Replacing :"+user2);
								 log(LogStatus.INFO, "Added Data for Task :" + i + " Label :" + label + " with value :" + value+" Replacing :"+user2, YesNo.No);
								
							}else {
								String query="SELECT Id FROM User WHERE IsActive = true AND Username = '"+ExcelUtils.readDataFromPropertyFile("SuperAdminUsername").trim()+"'";
								String admin=getQueryResult(query, "Id");
								data.put(label, admin);
								System.out.println("Added Data for Task :" + i + " Label :" + label + " with value :" + value+" Replacing :"+admin);
								 log(LogStatus.INFO, "Added Data for Task :" + i + " Label :" + label + " with value :" + value+" Replacing :"+admin, YesNo.No);
								
							}
							
						}
					} else if (label.equals("WhoId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String accoundId = getObjectRecordId("Contact", value);
							data.put(label, accoundId);
							System.out.println("Added Data for Task :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId);
							 log(LogStatus.INFO, "Added Data for Task :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId, YesNo.No);
							
						}
					} else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for Task :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date);
							 log(LogStatus.INFO, "Added Data for Task :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date, YesNo.No);
							

						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							data.put(label, value);
							System.out.println("Added Data for Task :" + i + " Label :" + label + " with value :" + value);
							 log(LogStatus.INFO, "Added Data for Task :" + i + " Label :" + label + " with value :" + value, YesNo.No);
							


						}
					}
				}

				createObejectRecordByAPI("Task", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Task :" + i);
				log(LogStatus.INFO, "Data already created for Task :" + i, YesNo.No);
			}
		}
		
	}
	
	public void TaskRelationshipObjectDataUpload(String filePath,String sheetName) {
	
		Map<String, Object> data = new HashMap<String, Object>();
		

		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("TaskId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String RT = getTaskObjectRecordId(value);
							data.put(label, RT);
							System.out.println("Added Data for Task Relation:" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
							 log(LogStatus.INFO, "Added Data for Task Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);
							

						}
					} else if (label.equals("RelationId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String accoundId = getObjectRecordId("Contact", value);
							data.put(label, accoundId);
							System.out.println("Added Data for Task Relation:" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId);
							 log(LogStatus.INFO, "Added Data for Task Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId, YesNo.No);
							

						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for Task Relation:" + i + " Label :" + label + " with value :" + value+" Replacing :"+date);
							 log(LogStatus.INFO, "Added Data for Task Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date, YesNo.No);
							

						}
					}  else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							data.put(label, value);
							System.out.println("Added Data for Task Relation:" + i + " Label :" + label + " with value :" + value);
							 log(LogStatus.INFO, "Added Data for Task Relation :" + i + " Label :" + label + " with value :" + value, YesNo.No);
							


						}
					}
				}

				createObejectRecordByAPI("TaskRelation", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Task Relation :" + i);
				log(LogStatus.INFO, "Data already created for Task Relation :" + i, YesNo.No);
			}
		}

	}
	
	
	public void EventObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		

		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String id = "";
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String RT = getObjectActiveRecordTypeId("Event", value);
							data.put(label, RT);
							System.out.println("Added Data for Event:" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
							 log(LogStatus.INFO, "Added Data for Event :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);
							
						}
					} else if (label.contains("Related")) {
						String relatedAss = label.split("<>")[0];
						String type = label.split("<>")[1];

						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String newId = "";
							if (id != null && !id.equals("")) {
								id = id + ",";
							}
							switch (type) {
							case "Account":
								newId = getObjectRecordId("Account", value);

								break;

							case "Deal":
								newId = getObjectRecordId("navpeII__Pipeline__c", value);

								break;
							case "Fund":
								newId = getObjectRecordId("navpeII__Fund__c", value);

								break;
							case "Fundraising":
								newId = getObjectRecordId("navpeII__Fundraising__c", value);

								break;
							case "Theme":
								newId = getObjectRecordId("navpeII__Theme__c", value);

								break;
							default:
								break;
							}
							id = id + newId;
							data.put(relatedAss, id);
							System.out.println("Added Data for Event:" + i + " Label :" + label + " with value :" + value+" Replacing :"+id);
							 log(LogStatus.INFO, "Added Data for Event :" + i + " Label :" + label + " with value :" + value+" Replacing :"+id, YesNo.No);
							
						}
					} else if (label.equals("OwnerId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							if(value.equalsIgnoreCase("CRM1")) {
								String query="SELECT Id FROM User WHERE IsActive = true  AND FirstName = 'CRM1'";
								String user1=getQueryResult(query, "Id");
								data.put(label, user1);
								System.out.println("Added Data for Event:" + i + " Label :" + label + " with value :" + value+" Replacing :"+user1);
								 log(LogStatus.INFO, "Added Data for Event :" + i + " Label :" + label + " with value :" + value+" Replacing :"+user1, YesNo.No);
								
							}else if(value.equalsIgnoreCase("CRM2")) {
								String query="SELECT Id FROM User WHERE IsActive = true  AND FirstName = 'CRM2'";
								String user2=getQueryResult(query, "Id");
								data.put(label, user2);
								System.out.println("Added Data for Event:" + i + " Label :" + label + " with value :" + value+" Replacing :"+user2);
								 log(LogStatus.INFO, "Added Data for Event :" + i + " Label :" + label + " with value :" + value+" Replacing :"+user2, YesNo.No);
								
							}else {
								String query="SELECT Id FROM User WHERE IsActive = true AND Username = '"+ExcelUtils.readDataFromPropertyFile("SuperAdminUsername").trim()+"'";
								String admin=getQueryResult(query, "Id");
								data.put(label, admin);
								System.out.println("Added Data for Event:" + i + " Label :" + label + " with value :" + value+" Replacing :"+admin);
								 log(LogStatus.INFO, "Added Data for Event :" + i + " Label :" + label + " with value :" + value+" Replacing :"+admin, YesNo.No);
								
							}
							
						}
					} else if (label.equals("WhoId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String accoundId = getObjectRecordId("Contact", value);
							data.put(label, accoundId);
							System.out.println("Added Data for Event:" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId);
							 log(LogStatus.INFO, "Added Data for Event :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId, YesNo.No);
							
						}
					} else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for Event:" + i + " Label :" + label + " with value :" + value+" Replacing :"+date);
							 log(LogStatus.INFO, "Added Data for Event :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date, YesNo.No);
							
						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							data.put(label, value);
							System.out.println("Added Data for Event:" + i + " Label :" + label + " with value :" + value);
							 log(LogStatus.INFO, "Added Data for Event :" + i + " Label :" + label + " with value :" + value, YesNo.No);
							

						}
					}
				}

				createObejectRecordByAPI("Event", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Event :" + i);
				log(LogStatus.INFO, "Data already created for Event :" + i, YesNo.No);
			}
		}

	}
	
	
	public void EventRelationshipObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		

		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("EventId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String RT = getEventObjectRecordId(value);
							data.put(label, RT);
							System.out.println("Added Data for Event Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
							 log(LogStatus.INFO, "Added Data for Event Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);
							

						}
					} else if (label.contains("RelationId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String accoundId = "";
							String label1 = label.split("<break>")[0];
							String obj = label.split("<break>")[1];
							if (obj.equalsIgnoreCase("user")) {
								accoundId = getObjectRecordId("User", value);
							} else {
								accoundId = getObjectRecordId("Contact", value);

							}
							data.put(label1, accoundId);
							System.out.println("Added Data for Event Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId);
							 log(LogStatus.INFO, "Added Data for Event Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId, YesNo.No);
							
						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for Event Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date);
							 log(LogStatus.INFO, "Added Data for Event Relation :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date, YesNo.No);
							
						}
					}  else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							data.put(label, value);
							System.out.println("Added Data for Event Relation :" + i + " Label :" + label + " with value :" + value );
							 log(LogStatus.INFO, "Added Data for Event Relation :" + i + " Label :" + label + " with value :" + value , YesNo.No);
							

						}
					}
				}

				createObejectRecordByAPI("EventRelation", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Event Relation :" + i);
				log(LogStatus.INFO, "Data already created for Event Relation :" + i, YesNo.No);
			}
		}

	}
	
    public void ThemeTeamsObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

						String RT = getObjectActiveRecordTypeId("navmnaI__Theme__c", value);
						data.put(label, RT);
						System.out.println("Added Data for Theme team :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT);
						 log(LogStatus.INFO, "Added Data for theme team :" + i + " Label :" + label + " with value :" + value+" Replacing :"+RT, YesNo.No);
					
						}
					} else if (label.contains("Theme")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

						String accoundId =getObjectRecordId("navmnaI__Theme__c", value);
						data.put(label, accoundId);
						System.out.println("Added Data for Theme team :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId);
						 log(LogStatus.INFO, "Added Data for theme team :" + i + " Label :" + label + " with value :" + value+" Replacing :"+accoundId, YesNo.No);
					
						}
					} else if (label.contains("Member")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

						String user="";
						String query="";
						if (value.equalsIgnoreCase("user1")) {
							query ="SELECT Id FROM User WHERE IsActive = true AND FirstName = 'CRM1'"; 									
							user=getQueryResult(query, "Id");


						} else if (value.equalsIgnoreCase("user2")) {

							query ="SELECT Id FROM User WHERE IsActive = true AND FirstName = 'CRM2'"; 									
							user=getQueryResult(query, "Id");
						}else if (value.equalsIgnoreCase("user3")) {

							query ="SELECT Id FROM User WHERE IsActive = true AND FirstName = 'CRM3'"; 									
							user=getQueryResult(query, "Id");
						} else if (value.equalsIgnoreCase("user4")) {

							query ="SELECT Id FROM User WHERE IsActive = true AND FirstName = 'CRM4'"; 									
							user=getQueryResult(query, "Id");
						}   else  {

							query ="SELECT Id FROM User WHERE IsActive = true AND Username = '"+ExcelUtils.readDataFromPropertyFile("SuperAdminUsername")+"'"; 									
							user=getQueryResult(query, "Id");


						}
						
						data.put(label, user);
						System.out.println("Added Data for Theme team :" + i + " Label :" + label + " with value :" + value+" Replacing :"+user);
						 log(LogStatus.INFO, "Added Data for theme team :" + i + " Label :" + label + " with value :" + value+" Replacing :"+user, YesNo.No);
					
						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = convertDate(value);
							data.put(label, date);
							System.out.println("Added Data for Theme team :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date);
							 log(LogStatus.INFO, "Added Data for theme team :" + i + " Label :" + label + " with value :" + value+" Replacing :"+date, YesNo.No);
						
						}
					}  else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

						data.put(label, value);
						System.out.println("Added Data for Theme team :" + i + " Label :" + label + " with value :" + value);
						 log(LogStatus.INFO, "Added Data for theme team :" + i + " Label :" + label + " with value :" + value, YesNo.No);
					
						}
					}


				}

				createObejectRecordByAPI("navmnaI__Theme_Team__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
				data.clear();
			} else {
				System.out.println("Data already created for Target Role :" + i);
				log(LogStatus.INFO, "Data already created for Target Role :" + i, YesNo.No);
			}
		}

	}
	
    
}
