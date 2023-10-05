package com.navatar.generic;

import static io.restassured.RestAssured.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.json.JSONObject;
import org.testng.annotations.Test;

import com.navatar.generic.EnumConstants.Environment;

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
		//log(LogStatus.INFO, "Data JSON file is: "+requester.toString(), YesNo.No);

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
		APIUtils api = new APIUtils();
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);

		for (int i = 1; i < row+1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j);
					String value = ExcelUtils.readData(filePath, sheetName, i , j);
					if (label.equals("RecordTypeId")) {
						String RT = api.getObjectActiveRecordTypeId("Account", value);
						data.put(label, RT);
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Account :" + i + " Label :" + label + " with value :" + value);
						}
					} else {
						data.put(label, value);
					}
					System.out.println("Added Data for account :" + i + " Label :" + label + " with value :" + value);

				}

				api.createObejectRecordByAPI("Account", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for account :" + i );
			}
		}
		
		
	}
	
	
	public void ContactObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath,sheetName , i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {
						String RT = api.getObjectActiveRecordTypeId("Contact", value);
						data.put(label, RT);
					} else if (label.equals("AccountId")) {

						String account = api.getObjectRecordId("Account", value);
						data.put(label, account);
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Contact :" + i + " Label :" + label + " with value :" + value);
						}
					} else {
						data.put(label, value);
					}

					System.out.println("Added Data for contact :" + i + " Label :" + label + " with value :" + value);

				}

				api.createObejectRecordByAPI("Contact", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Contact :" + i);
			}
		}

	}
	
	
	public void DealObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();
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
							String RT = api.getObjectActiveRecordTypeId("navpeII__Pipeline__c", value);
							data.put(label, RT);
							System.out.println(
									"Added Data for Deal :" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					} else if (label.equals("AccountId") || label.equals("navpeII__Company_Name__c")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String accoundId = api.getObjectRecordId("Account", value);
							data.put(label, accoundId);
							System.out.println(
									"Added Data for Deal :" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Deal :" + i + " Label :" + label + " with value :" + value);
						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println(
									"Added Data for Deal :" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					}

				}

				api.createObejectRecordByAPI("navpeII__Pipeline__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Deal :" + i);
			}
		}

	}
	
	public void FundObjectDataUpload(String filePath,String sheetName) {

		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();
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
							String RT = api.getObjectActiveRecordTypeId("navpeII__Fund__c", value);
							data.put(label, RT);
							System.out.println(
									"Added Data for Fund :" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

 

						}
					}else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println(
									"Added Data for Fund :" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

 

						}
					}

 

				}

 

				api.createObejectRecordByAPI("navpeII__Fund__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Fund :" + i);
			}
		}

 

	}
	
	public void DealTeamObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();
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
							String RT = api.getObjectActiveRecordTypeId("navmnaI__Deal_Contacts__c", value);
							data.put(label, RT);
							System.out.println(
									"Added Data for Deal Team:" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					} else if (label.contains("Contact")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String accoundId = api.getObjectRecordId("Contact", value);
							data.put(label, accoundId);
							System.out.println(
									"Added Data for Deal team :" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					}else if (label.contains("Deal")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String accoundId = api.getObjectRecordId("navmnaI__Deal__c", value);
							data.put(label, accoundId);
							System.out.println(
									"Added Data for Deal team :" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Deal Team :" + i + " Label :" + label + " with value :" + value);
						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println(
									"Added Data for Deal Team :" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					}

				}

				api.createObejectRecordByAPI("navmnaI__Deal_Contacts__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Deal Team :" + i);
			}
		}

	}
	
	
	public void ClipObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();
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
							String RT = api.getObjectActiveRecordTypeId("navmnaI__Clip__c", value);
							data.put(label, RT);
							System.out.println(
									"Added Data for clip :" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					} else if (label.equals("AccountId") || label.equals("navmnaI__Account__c")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String accoundId = api.getObjectRecordId("Account", value);
							data.put(label, accoundId);
							System.out.println(
									"Added Data for clip :" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Clip :" + i + " Label :" + label + " with value :" + value);
						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println(
									"Added Data for clip :" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					}

				}

				api.createObejectRecordByAPI("navmnaI__Clip__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Clip :" + i);
			}
		}

	}
	
	
	public void ClipRelationObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();
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
							String RT = api.getObjectActiveRecordTypeId("navmnaI__Clip__c", value);
							data.put(label, RT);
							System.out.println("Added Data for clip relation :" + i + " Label :" + label
									+ " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					} else if (label.contains("Clip")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String RT = api.getObjectRecordId("navmnaI__Clip__c", value);
							data.put(label, RT);
							System.out.println("Added Data for clip relation :" + i + " Label :" + label
									+ " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

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
								newId = api.getObjectRecordId("Account", value);

								break;
							case "Contact":
								newId = api.getObjectRecordId("Contact", value);

								break;
							case "Deal":
								newId = api.getObjectRecordId("navmnaI__Deal__c", value);

								break;
							case "Target":
								newId = api.getObjectRecordId("navmnaI__Target__c", value);

								break;
							case "Theme":
								newId = api.getObjectRecordId("Theme__c", value);

								break;
							default:
								break;
							}
							id = id + newId;
							data.put(relatedAss, id);
							System.out.println("Added Data for clip relation :" + i + " Label :" + relatedAss
									+ " with value :" + value);

						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Clip Relation :" + i + " Label :" + label + " with value :" + value);
						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println("Added Data for clip relation :" + i + " Label :" + label
									+ " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					}

				}

				api.createObejectRecordByAPI("Clip_Relation__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Clip Relation :" + i);
			}
		}

	}
	
	@Test
	public void ThemeObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();
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
							String RT = api.getObjectActiveRecordTypeId("navpeII__Theme__c", value);
							data.put(label, RT);
							System.out.println(
									"Added Data for Deal :" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					} else if (label.equals("AccountId") || label.equals("navmnaI__Account__c")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String accoundId = api.getObjectRecordId("Account", value);
							data.put(label, accoundId);
							System.out.println(
									"Added Data for Deal :" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Theme :" + i + " Label :" + label + " with value :" + value);
						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println(
									"Added Data for Deal :" + i + " Label :" + label + " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					}

				}

				api.createObejectRecordByAPI("navpeII__Theme__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Theme :" + i);
			}
		}

	}
	
	@Test
	public void ThemeRelationObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();
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
							String RT = api.getObjectActiveRecordTypeId("navmnaI__Theme_Relation__c", value);
							data.put(label, RT);
							System.out.println("Added Data for Theme relation :" + i + " Label :" + label
									+ " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					} else if (label.equals("navmnaI__Theme__c")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String RT = api.getObjectRecordId("navmnaI__Theme__c", value);
							data.put(label, RT);
							System.out.println("Added Data for Theme relation :" + i + " Label :" + label
									+ " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

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
								newId = api.getObjectRecordId("Account", value);

								break;
							case "Contact":
								newId = api.getObjectRecordId("Contact", value);

								break;
							case "Deal":
								newId = api.getObjectRecordId("navmnaI__Deal__c", value);

								break;
							case "Target":
								newId = api.getObjectRecordId("navmnaI__Target__c", value);

								break;
							case "Theme":
								newId = api.getObjectRecordId("navmnaI__Theme__c", value);

								break;
							case "Clip":
								newId = api.getObjectRecordId("navmnaI__Clip__c", value);

								break;
							default:
								break;
							}
							id = id + newId;
							data.put(relatedAss, id);
							System.out.println("Added Data for Theme relation :" + i + " Label :" + relatedAss
									+ " with value :" + value);

						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Theme Relation :" + i + " Label :" + label + " with value :" + value);
						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							data.put(label, value);
							System.out.println("Added Data for Theme relation :" + i + " Label :" + label
									+ " with value :" + value);
							// log(LogStatus.INFO, "Added Data for Deal :"+i +" Label :"+label +" with value
							// :"+value, YesNo.No);

						}
					}

				}

				api.createObejectRecordByAPI("navmnaI__Theme_Relation__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Theme Relation :" + i);
			}
		}

	}
	
	
	public void FundraisingObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {
						String RT = api.getObjectActiveRecordTypeId("navpeII__Fundraising__c", value);
						data.put(label, RT);
						System.out.println(
								"Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value);
					} else if (label.equals("AccountId") || label.equals("Account")
							|| label.equals("navpeII__Legal_Name__c")) {
						String accoundId = api.getObjectRecordId("Account", value);
						data.put(label, accoundId);
						System.out.println(
								"Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value);
					} else if (label.equals("navpeII__Fund_Name__c")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
						String dealId = api.getObjectRecordId("navpeII__Fund__c", value);
						data.put(label, dealId);
						System.out.println(
								"Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value);
						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value);
						}
					} else {
						data.put(label, value);
						System.out.println(
								"Added Data for Fundraising :" + i + " Label :" + label + " with value :" + value);
					}


				}

				api.createObejectRecordByAPI("navpeII__Fundraising__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Fundraising :" + i);
			}
		}

	}
	
	
	public void TargetContactRoleObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {
						String RT = api.getObjectActiveRecordTypeId("navmnaI__Target__c", value);
						data.put(label, RT);
					} else if (label.contains("Target")) {
						String accoundId = api.getObjectRecordId("navmnaI__Target__c", value);
						data.put(label, accoundId);
					} else if (label.contains("Contact")) {
						String dealId = api.getObjectRecordId("Contact", value);
						data.put(label, dealId);
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Target Role:" + i + " Label :" + label + " with value :" + value);
						}
					}  else {
						data.put(label, value);
					}

					System.out.println("Added Data for Target Role :" + i + " Label :" + label + " with value :" + value);

				}

				api.createObejectRecordByAPI("navmnaI__Contact_Role__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Target Role :" + i);
			}
		}

	}
	
	
	public void TaskObjectDataUpload(String filePath,String sheetName) {
	
		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();

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

							String RT = api.getObjectActiveRecordTypeId("Task", value);
							data.put(label, RT);
							System.out.println(
									"Added Data for Task :" + i + " Label :" + label + " with value :" + value);

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
								newId = api.getObjectRecordId("Account", value);

								break;

							case "Deal":
								newId = api.getObjectRecordId("navmnaI__Deal__c", value);

								break;
							case "Target":
								newId = api.getObjectRecordId("navmnaI__Target__c", value);

								break;
							case "Theme":
								newId = api.getObjectRecordId("navmnaI__Theme__c", value);

								break;
							default:
								break;
							}
							id = id + newId;
							data.put(relatedAss, id);
							System.out.println(
									"Added Data for Task :" + i + " Label :" + relatedAss + " with value :" + value);

						}
					} else if (label.equals("OwnerId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							if(value.equalsIgnoreCase("User1")) {
								String query="SELECT Id FROM User WHERE IsActive = true  AND FirstName = 'CRM1'";
								String user1=api.getQueryResult(query, "Id");
								data.put(label, user1);
								System.out.println(
										"Added Data for Task :" + i + " Label :" + label + " with value :" + value);
							}else if(value.equalsIgnoreCase("User2")) {
								String query="SELECT Id FROM User WHERE IsActive = true  AND FirstName = 'CRM2'";
								String user2=api.getQueryResult(query, "Id");
								data.put(label, user2);
								System.out.println(
										"Added Data for Task :" + i + " Label :" + label + " with value :" + value);
							}else {
								String query="SELECT Id FROM User WHERE IsActive = true AND Username = '"+ExcelUtils.readDataFromPropertyFile("SuperAdminUsername").trim()+"'";
								String admin=api.getQueryResult(query, "Id");
								data.put(label, admin);
								System.out.println(
										"Added Data for Task :" + i + " Label :" + label + " with value :" + value);
							}
							
						}
					} else if (label.equals("WhoId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String accoundId = api.getObjectRecordId("Contact", value);
							data.put(label, accoundId);
							System.out.println(
									"Added Data for Task :" + i + " Label :" + label + " with value :" + value);
						}
					} else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Task :" + i + " Label :" + label + " with value :" + value);
						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							data.put(label, value);
							System.out.println(
									"Added Data for Task :" + i + " Label :" + label + " with value :" + value);

						}
					}
				}

				api.createObejectRecordByAPI("Task", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Task :" + i);
			}
		}
		
	}
	
	
	public void TaskRelationshipObjectDataUpload(String filePath,String sheetName) {
	
		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();

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

							String RT = api.getTaskObjectRecordId(value);
							data.put(label, RT);
							System.out.println(
									"Added Data for Task Relation:" + i + " Label :" + label + " with value :" + value);

						}
					} else if (label.equals("RelationId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String accoundId = api.getObjectRecordId("Contact", value);
							data.put(label, accoundId);
							System.out.println("Added Data for Task Relation :" + i + " Label :" + label
									+ " with value :" + value);
						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Task Relation :" + i + " Label :" + label + " with value :" + value);
						}
					}  else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							data.put(label, value);
							System.out.println("Added Data for Task Relation :" + i + " Label :" + label
									+ " with value :" + value);

						}
					}
				}

				api.createObejectRecordByAPI("TaskRelation", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Task Relation :" + i);
			}
		}

	}
	
	
	public void EventObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();

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

							String RT = api.getObjectActiveRecordTypeId("Event", value);
							data.put(label, RT);
							System.out.println(
									"Added Data for Task :" + i + " Label :" + label + " with value :" + value);

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
								newId = api.getObjectRecordId("Account", value);

								break;

							case "Deal":
								newId = api.getObjectRecordId("navpeII__Pipeline__c", value);

								break;
							case "Fund":
								newId = api.getObjectRecordId("navpeII__Fund__c", value);

								break;
							case "Fundraising":
								newId = api.getObjectRecordId("navpeII__Fundraising__c", value);

								break;
							case "Theme":
								newId = api.getObjectRecordId("navpeII__Theme__c", value);

								break;
							default:
								break;
							}
							id = id + newId;
							data.put(relatedAss, id);
							System.out.println(
									"Added Data for Event :" + i + " Label :" + relatedAss + " with value :" + value);

						}
					} else if (label.equals("OwnerId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							if(value.equalsIgnoreCase("User1")) {
								String query="SELECT Id FROM User WHERE IsActive = true  AND FirstName = 'CRM1'";
								String user1=api.getQueryResult(query, "Id");
								data.put(label, user1);
								System.out.println(
										"Added Data for Event :" + i + " Label :" + label + " with value :" + value);
							}else if(value.equalsIgnoreCase("User2")) {
								String query="SELECT Id FROM User WHERE IsActive = true  AND FirstName = 'CRM2'";
								String user2=api.getQueryResult(query, "Id");
								data.put(label, user2);
								System.out.println(
										"Added Data for Event :" + i + " Label :" + label + " with value :" + value);
							}else {
								String query="SELECT Id FROM User WHERE IsActive = true AND Username = '"+ExcelUtils.readDataFromPropertyFile("SuperAdminUsername").trim()+"'";
								String admin=api.getQueryResult(query, "Id");
								data.put(label, admin);
								System.out.println(
										"Added Data for Event :" + i + " Label :" + label + " with value :" + value);
							}
							
						}
					} else if (label.equals("WhoId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String accoundId = api.getObjectRecordId("Contact", value);
							data.put(label, accoundId);
							System.out.println(
									"Added Data for Event :" + i + " Label :" + label + " with value :" + value);
						}
					} else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Event :" + i + " Label :" + label + " with value :" + value);
						}
					} else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							data.put(label, value);
							System.out.println(
									"Added Data for Event :" + i + " Label :" + label + " with value :" + value);

						}
					}
				}

				api.createObejectRecordByAPI("Event", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Event :" + i);
			}
		}

	}
	
	
	public void EventRelationshipObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();

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

							String RT = api.getEventObjectRecordId(value);
							data.put(label, RT);
							System.out.println("Added Data for Event Relation:" + i + " Label :" + label
									+ " with value :" + value);

						}
					} else if (label.contains("RelationId")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {
							String accoundId = "";
							String label1 = label.split("<break>")[0];
							String obj = label.split("<break>")[1];
							if (obj.equalsIgnoreCase("user")) {
								accoundId = api.getObjectRecordId("User", value);
							} else {
								accoundId = api.getObjectRecordId("Contact", value);

							}
							data.put(label1, accoundId);
							System.out.println("Added Data for Event Relation :" + i + " Label :" + label
									+ " with value :" + value);
						}
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Event Relation :" + i + " Label :" + label + " with value :" + value);
						}
					}  else {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							data.put(label, value);
							System.out.println("Added Data for Event Relation :" + i + " Label :" + label
									+ " with value :" + value);

						}
					}
				}

				api.createObejectRecordByAPI("EventRelation", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Event Relation :" + i);
			}
		}

	}
	
    public void ThemeTeamsObjectDataUpload(String filePath,String sheetName) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		APIUtils api = new APIUtils();
		int lastcol = ExcelUtils.getLastColumn(filePath, sheetName, 0);

		int row = ExcelUtils.getLastRow(filePath, sheetName);
		for (int i = 1; i < row + 1; i++) {
			String status = ExcelUtils.readData(filePath, sheetName, i, 0);
			if (!status.equalsIgnoreCase("Created")) {
				for (int j = 1; j < lastcol; j++) {

					String label = ExcelUtils.readData(filePath, sheetName, 0, j).trim();
					String value = ExcelUtils.readData(filePath, sheetName, i, j).trim();
					if (label.equals("RecordTypeId")) {
						String RT = api.getObjectActiveRecordTypeId("navmnaI__Theme__c", value);
						data.put(label, RT);
					} else if (label.contains("Theme")) {
						String accoundId =api.getObjectRecordId("navmnaI__Theme__c", value);
						data.put(label, accoundId);
					} else if (label.contains("Member")) {
						String user="";
						String query="";
						if (value.equalsIgnoreCase("user1")) {
							query ="SELECT Id FROM User WHERE IsActive = true AND FirstName = 'CRM1'"; 									
							user=api.getQueryResult(query, "Id");


						} else if (value.equalsIgnoreCase("user2")) {

							query ="SELECT Id FROM User WHERE IsActive = true AND FirstName = 'CRM2'"; 									
							user=api.getQueryResult(query, "Id");
						}else if (value.equalsIgnoreCase("user3")) {

							query ="SELECT Id FROM User WHERE IsActive = true AND FirstName = 'CRM3'"; 									
							user=api.getQueryResult(query, "Id");
						} else if (value.equalsIgnoreCase("user4")) {

							query ="SELECT Id FROM User WHERE IsActive = true AND FirstName = 'CRM4'"; 									
							user=api.getQueryResult(query, "Id");
						}   else  {

							query ="SELECT Id FROM User WHERE IsActive = true AND Username = '"+ExcelUtils.readDataFromPropertyFile("SuperAdminUsername")+"'"; 									
							user=api.getQueryResult(query, "Id");


						}
						
						data.put(label, user);
					}else if (label.contains("Date")) {
						if (value != null && !value.equalsIgnoreCase("") && !value.isBlank() && !value.isEmpty()) {

							String date = api.convertDate(value);
							data.put(label, date);
							System.out.println(
									"Added Data for Target Role:" + i + " Label :" + label + " with value :" + value);
						}
					}  else {
						data.put(label, value);
					}

					System.out.println("Added Data for Target Role :" + i + " Label :" + label + " with value :" + value);

				}

				api.createObejectRecordByAPI("navmnaI__Theme_Team__c", data);
				ExcelUtils.writeDataInExcel(filePath, "Created", sheetName, i, 0);
			} else {
				System.out.println("Data already created for Target Role :" + i);
			}
		}

	}
	
    
}

