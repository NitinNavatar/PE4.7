package com.navatar.generic;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.navatar.generic.EnumConstants.excelLabel;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import static com.navatar.generic.EnumConstants.*;

public class ExcelUtils {
	static String path = System.getProperty("user.dir") + "/TestCases.xlsx";
	static FileInputStream fis;
	static FileOutputStream fos;
	public static Workbook wb;
	static int lastRow = 0;

	/**
	 * @author Ankur Rana
	 * @return All the Test Case ID which have "No" written in there "Execute"
	 *         column
	 * @Description: This method fetch the Test Case ID of the test cases which we
	 *               don't want to execute.
	 */
	public static List<String> getTcsToRun(String sheetName) {

		List<String> tcs = new ArrayList<String>();
		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			lastRow = wb.getSheet(sheetName).getLastRowNum();
			for (int i = 1; i <= lastRow; i++) {
				if (wb.getSheet(sheetName).getRow(i).getCell(2).getStringCellValue().equalsIgnoreCase("no")) {
					tcs.add(wb.getSheet(sheetName).getRow(i).getCell(0).getStringCellValue());
				}
			}
			fis.close();
		} catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tcs;

	}


	@SuppressWarnings("deprecation")
	public static String readAllDataForARow(String filePath, String sheetName, int cellNum,
			Boolean numericTypeValue) {
		String value = "";
		try {
			File src = new File(filePath);
			FileInputStream fis = new FileInputStream(src);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sh1 = wb.getSheet(sheetName);
			int rowCount = sh1.getLastRowNum();
			System.err.println("rowCount : " + rowCount);
			String a = "";
			DataFormatter df = new DataFormatter();
			for (int row = 1; row <= rowCount; row++) {
				if (numericTypeValue) {
					XSSFRow row1 = sh1.getRow(row);
					XSSFCell cell = row1.getCell(cellNum);
					cell.setCellType(CellType.NUMERIC);
					a = String.valueOf(cell.getNumericCellValue());
//					AppListeners.appLog.info("Id : "+a);
					String[] ss = a.split("E");
//					AppListeners.appLog.info("ss0 >> "+ss[0]);
//					AppListeners.appLog.info("ss1 >> "+ss[1]);
					if (ss.length > 1) {
						Double d = Double.valueOf(ss[0]) * Math.pow(10, Integer.parseInt(ss[1]));
//						System.err.println(d);
						a = String.valueOf(new BigDecimal(Math.round(d)).toBigInteger());
//						AppListeners.appLog.info(" Final Id : "+a);
						value = a + "<break>" + value;
					} else {
//						AppListeners.appLog.info(" Final Id : "+a);
						value = a + "<break>" + value;
					}
				} else {
					a = df.formatCellValue((sh1.getRow(row).getCell(cellNum)));
					if (row < rowCount) {
						value = value + a + "<break>";
					} else {
						value = value + a;
					}

				}
			}
			fis.close();
			wb.close();
		} catch (Exception e) {
			AppListeners.appLog.info("<<<<<<<<<<<<<Exception Error : >>>>>>>>>> :" + e);
			BaseLib.sa.assertTrue(false, "File Not Found : " + filePath);
			value = null;
		}
		return value;

	}
	
	/**
	 * @author Ankur Rana
	 * @param sheetName Name of the sheet which have all the test case ID and the
	 *                  column where you want to write the status
	 * @Description This method write the status of all the test cases.
	 */
	public static void writeStatusInExcel(String sheetName) {
		Set<String> keys = AppListeners.status.keySet();
		Iterator<String> itr = keys.iterator();
		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			fis.close();
			fos = new FileOutputStream(new File(path));
			Sheet sheet = wb.getSheet(sheetName);
			List<String> key = new ArrayList<String>();
			while (itr.hasNext()) {
				key.add(itr.next());
			}
			for (int i = 0; i < key.size(); i++) {
				for (int z = 1; z <= lastRow; z++) {
					if (key.get(i).equalsIgnoreCase(sheet.getRow(z).getCell(0).getStringCellValue())) {
						Row row = sheet.getRow(z);
						row.createCell(3).setCellValue(AppListeners.status.get(key.get(i)));
					}
				}
			}
			wb.write(fos);
			wb.close();
			fos.close();
		} catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author Ankur Rana
	 * @return Void
	 * @Description: Pass the string which you want to write with the sheet name,
	 *               row number and cell number
	 */
	public static void writeDataInExcel(Object msg, String sheetName, int rowNum, int cellNum) {

		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			fis.close();
			fos = new FileOutputStream(new File(path));
			wb.getSheet(sheetName).getRow(rowNum).createCell(cellNum).setCellValue(msg.toString());
			wb.write(fos);
			fos.close();
		} catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author Ankur Rana
	 * @return String
	 * @Description: Pass the sheet name, row number and cell number to fetch data
	 *               from particular cell
	 */
	@SuppressWarnings("deprecation")
	public static String readData(String random, String path, String sheetName, int rowNum, int cellNum) {
		String value = null;
		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
//			wb.getSheet(sheetName).getRow(rowNum).getLastCellNum();
			Cell cell = wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum);
			value = getValueBasedOnCellType(cell);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AppListeners.appLog.info("There is no value at the location you passed.");
			return null;
		}
		return value;

	}

	/**
	 * @author Ankur Rana
	 * @param key
	 * @return string
	 * @Description this method read value from the property file
	 *              credentials.properties.
	 */
	public static String readDataFromPropertyFile(String key) {

		String value = "";
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(new File(System.getProperty("user.dir") + "//credentials.properties")));
			value = prop.getProperty(key);
		} catch (Exception e) {
			System.out.println("property file not found");
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * @author Ankur Rana
	 * @param filePath
	 * @param key
	 * @return String
	 * @description this method read value from the property file of your choice.
	 */
	public static String readDataFromPropertyFile(String filePath, String key) {

		String value = "";
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(new File(filePath)));
			value = prop.getProperty(key);
		} catch (Exception e) {
			System.out.println("property file not found");
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * @author Ankur Rana
	 * @param sheetName
	 * @param rowNum
	 * @return int
	 * @description this method gives the last column count
	 */
	public static int getLastColumn(String sheetName, int rowNum) {
//		return wb.getSheet(sheetName).getPhysicalNumberOfRows();
		return wb.getSheet(sheetName).getRow(rowNum).getLastCellNum();
	}

	// public static void writeDataInPropertyFile(String key, String oldValue,
	// String newValue){
	//
	// Properties prop=new Properties();
	//
	// try {
	// prop.load(new FileInputStream(new File("./credentials.properties")));
	// prop.replace(key, oldValue, newValue);
	// prop.setProperty(key, newValue);
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	/**
	 * @author Ankur Rana
	 * @param sheetName
	 * @param tcName
	 * @param cellNum
	 * @return String
	 * @description This method read the cell number passed according to the test
	 *              case name passed.
	 */
	public static String readData(String sheetName, String tcName, int cellNum) {
		String value = "";
		String testCaseName;
		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			DataFormatter df = new DataFormatter();
			int lastRow = wb.getSheet(sheetName).getLastRowNum();
			System.out.println(lastRow);
			int j = 0;
			for (int i = 0; i <= lastRow; i++) {
				try {
					Cell cell = wb.getSheet(sheetName).getRow(i).getCell(0);
					testCaseName = getValueBasedOnCellType(cell);
					if (!testCaseName.isEmpty() && tcName.contains(testCaseName)) {
						cell = wb.getSheet(sheetName).getRow(i).getCell(cellNum);
						value = getValueBasedOnCellType(cell);
						break;
					} else {
						continue;
					}
				} catch (Exception e) {
					AppListeners.appLog
							.info("Row number '" + i + "' is blank. So will not be able to read data from row number '"
									+ i + "'. Kindly Delete the row or enter some value.");
				}
			}
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AppListeners.appLog
					.info("File Path '" + path + "' or Sheet Name '" + sheetName + "' is wrong kindly re-check.");
			return null;
		}
		return value;

	}

	/**
	 * @author Ankur Rana
	 * @param sheetName
	 * @param columnName
	 * @return String
	 * @description This method read the cell data according to column Name passed.
	 */
	public static String readData(String sheetName, String columnName) {
		String value = "";
		String testCaseName;
		String tcName = AppListeners.currentlyExecutingTC;
		String colName;
		int cellNum = 0;
		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			DataFormatter df = new DataFormatter();
			int lastRow = wb.getSheet(sheetName).getLastRowNum();
			System.out.println(lastRow);
			for (int i = 0; i <= lastRow; i++) {
				try {
					Cell cell = wb.getSheet(sheetName).getRow(i).getCell(0);
					testCaseName = getValueBasedOnCellType(cell);
					if (!testCaseName.isEmpty() && tcName.contains(testCaseName)) {
						int lastColumnNum = wb.getSheet(sheetName).getRow(0).getLastCellNum();
						System.out.println(lastColumnNum);
						for (int j = 0; j <= lastColumnNum; j++) {
							try {
								cell = wb.getSheet(sheetName).getRow(0).getCell(j);
								colName = getValueBasedOnCellType(cell);
								if (!colName.isEmpty() && colName.equalsIgnoreCase(columnName)) {
									cellNum = j;
									break;
								} else {
									if (j == lastColumnNum) {
										AppListeners.appLog.info(columnName + " is not found.");
										return "";
									}
									continue;
								}
							} catch (Exception e) {
								AppListeners.appLog.info("Column number '" + j
										+ "' is blank. So will not be able to read data from column number '" + j
										+ "'. Kindly Delete the column or enter some value.");
							}
						}
						cell = wb.getSheet(sheetName).getRow(i).getCell(cellNum);
						value = getValueBasedOnCellType(cell);
						break;
					} else {
						if (i == lastRow) {
							AppListeners.appLog.info(tcName + " is not found.");
							break;
						}
						continue;
					}
				} catch (Exception e) {
					AppListeners.appLog
							.info("Row number '" + i + "' is blank. So will not be able to read data from row number '"
									+ i + "'. Kindly Delete the row or enter some value.");
				}
			}
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AppListeners.appLog
					.info("File Path '" + path + "' or Sheet Name '" + sheetName + "' is wrong kindly re-check.");
			return null;
		}
		return value;

	}

	// start from here replacing the df.format with getValueBasedOnCellType
	public static String readData(String path, String sheetName, int rowNum, int cellNum) {
		String value = "";
		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
//			wb.getSheet(sheetName).getRow(rowNum).getLastCellNum();
			Cell cell = wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum);
			value = getValueBasedOnCellType(cell);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AppListeners.appLog.info("There is no value at the location you passed.");
			return null;
		}
		return value;

	}

	public static String readData(String sheetName, int columnNum, int cellNum, String value) {
		String excelValue = null;
		String testCaseName;
		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			DataFormatter df = new DataFormatter();
			int lastRow = wb.getSheet(sheetName).getLastRowNum();
//			System.out.println(lastRow);
			for (int i = 0; i <= lastRow; i++) {
				try {
					Cell cell = wb.getSheet(sheetName).getRow(i).getCell(columnNum);
					testCaseName = getValueBasedOnCellType(cell);
					if (!testCaseName.isEmpty() && testCaseName.equalsIgnoreCase(value)) {
						cell = wb.getSheet(sheetName).getRow(i).getCell(cellNum);
						excelValue = getValueBasedOnCellType(cell);
						break;
					} else {
						continue;
					}
				} catch (Exception e) {
					AppListeners.appLog
							.info("Row number '" + i + "' is blank. So will not be able to read data from row number '"
									+ i + "'. Kindly Delete the row or enter some value.");
				}
			}
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AppListeners.appLog
					.info("File Path '" + path + "' or Sheet Name '" + sheetName + "' is wrong kindly re-check.");
			return null;
		}
		return excelValue;

	}

	public static boolean WriteDataBasedOnColumnName(String sheetName, int columnNum, int cellNum, String value,
			String writeValue) {
		String excelValue = null;
		boolean flag = false;
		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			fis.close();
			int lastRow = wb.getSheet(sheetName).getLastRowNum();
			for (int i = 0; i <= lastRow; i++) {
				try {
					Cell cell = wb.getSheet(sheetName).getRow(i).getCell(columnNum);
					excelValue = getValueBasedOnCellType(cell).trim();
					if (!excelValue.isEmpty() && excelValue.equalsIgnoreCase(value)) {
						fos = new FileOutputStream(new File(path));
						wb.getSheet(sheetName).getRow(i).createCell(cellNum).setCellValue(writeValue);
						wb.write(fos);
						flag = true;
						fos.close();
						break;
					} else {
						continue;
					}
				} catch (Exception e) {
					AppListeners.appLog
							.error("Row number '" + i + "' is blank. So will not be able to read data from row number '"
									+ i + "'. Kindly Delete the row or enter some value.");
				}
			}
//			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AppListeners.appLog
					.error("File Path '" + path + "' or Sheet Name '" + sheetName + "' is wrong kindly re-check.");
			return false;
		}
		if (!flag) {
			AppListeners.appLog.error(
					value + " :Value is not present in excel sheet " + sheetName + " in column number " + columnNum);
		}
		return flag;
	}

	public static int getLastRow(String sheetName) {
//		return wb.getSheet(sheetName).getPhysicalNumberOfRows();
		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			return wb.getSheet(sheetName).getLastRowNum();
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	/**
	 * @author Ankur Rana
	 * @param sheetName
	 * @param label
	 * @return column number of the label
	 */
	public static int getColumnNumberBasedOnLabel(String sheetName, excelLabel label) {

		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheet(sheetName);
			int lastColumnNumber = sheet.getRow(0).getLastCellNum();
			for (int i = 0; i < lastColumnNumber; i++) {
				if (getValueBasedOnCellType(sheet.getRow(0).getCell(i)).equalsIgnoreCase(label.toString())) {
					return i;
				} else {
					if (i == lastColumnNumber - 1) {
						AppListeners.appLog.info(label.toString() + " is not present in the excel.");
						System.out.println(label.toString() + " is not present in the excel.");
					}
				}
			}
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * @author Ankur Rana
	 * @param sheetName
	 * @param basedOnLabel
	 * @param basedOnValue
	 * @return row number at which basedOnValue(value) is present under the
	 *         basedOnLabel(Label)
	 */
	public static int getRowNumberBasedOnLabelAndValue(String sheetName, excelLabel basedOnLabel, String basedOnValue) {

		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			String currentlyItreatingValue = "";
			Sheet sheet = wb.getSheet(sheetName);
			int j = 0;
			int searchInColumnNumber = getColumnNumberBasedOnLabel(sheetName, basedOnLabel);
			for (int i = 0; i >= 0; i++) {
				try {
					currentlyItreatingValue = getValueBasedOnCellType(sheet.getRow(i).getCell(searchInColumnNumber));
				} catch (NullPointerException e) {
					j++;
					if (j == 100) {
						break;
					}
					continue;
				}
				if (currentlyItreatingValue.equalsIgnoreCase(basedOnValue.toString())) {
					return i;
				}
			}
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(basedOnValue + " value is not found under label " + basedOnLabel.toString());
		return 0;

	}

	/**
	 * @author Ankur Rana
	 * @param sheetName
	 * @param basedOnLabel:     Label under which primary key is present
	 * @param basedOnValue:     Primary Key based on which value is required.
	 * @param searchUnderLabel: Label under which required value is present
	 * @return Value based on the parameters.
	 */
	public static String readData(String sheetName, excelLabel basedOnLabel, String basedOnValue,
			excelLabel searchUnderLabel) {
		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheet(sheetName);
			Cell cell = sheet.getRow(getRowNumberBasedOnLabelAndValue(sheetName, basedOnLabel, basedOnValue))
					.getCell(getColumnNumberBasedOnLabel(sheetName, searchUnderLabel));
			return getValueBasedOnCellType(cell);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * @author Ankur Rana
	 * @param dataToWrite:     The data which you want to write in excel
	 * @param sheetName:       Name of the sheet in which data has to be written
	 * @param basedOnLabel:    Label which contains the primary key
	 * @param basedOnValue:    Primary key to find the row number
	 * @param writeUnderLabel: Label under which data has to be written
	 * @return boolean: if successfully written then return true otherwise false
	 */
	public static boolean writeData(Object dataToWrite, String sheetName, excelLabel basedOnLabel, String basedOnValue,
			excelLabel writeUnderLabel) {
		try {
//			System.err.println("Row: "+getRowNumberBasedOnLabelAndValue(sheetName, basedOnLabel, basedOnValue)+" Cell: "+getColumnNumberBasedOnLabel(sheetName, writeUnderLabel));
			writeDataInExcel(dataToWrite, sheetName,
					getRowNumberBasedOnLabelAndValue(sheetName, basedOnLabel, basedOnValue),
					getColumnNumberBasedOnLabel(sheetName, writeUnderLabel));
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * @author Ankur Rana
	 * @return Void
	 * @Description: Pass the string which you want to write in the excel at path
	 *               with the sheet name, row number and cell number
	 */
	public static void writeDataInExcel(String path, Object msg, String sheetName, int rowNum, int cellNum) {

		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			fis.close();
			fos = new FileOutputStream(new File(path));
			wb.getSheet(sheetName).getRow(rowNum).createCell(cellNum).setCellValue(msg.toString());
			wb.write(fos);
			wb.close();
			fos.close();
		} catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean writeData(String path, Object dataToWrite, String sheetName, excelLabel basedOnLabel,
			String basedOnValue, excelLabel writeUnderLabel) {
		try {
			writeDataInExcel(path, dataToWrite, sheetName,
					getRowNumberBasedOnLabelAndValue(path, sheetName, basedOnLabel, basedOnValue),
					getColumnNumberBasedOnLabel(path, sheetName, writeUnderLabel));
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}

		return false;
	}

	public static int getRowNumberBasedOnLabelAndValue(String path, String sheetName, excelLabel basedOnLabel,
			String basedOnValue) {
		int k = 0;
		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			String currentlyItreatingValue = "";
			Sheet sheet = wb.getSheet(sheetName);
			int j = 0;
			int searchInColumnNumber = getColumnNumberBasedOnLabel(path, sheetName, basedOnLabel);
			for (int i = 0; i >= 0; i++) {
				try {
					currentlyItreatingValue = getValueBasedOnCellType(sheet.getRow(i).getCell(searchInColumnNumber));
				} catch (NullPointerException e) {
					j++;
					if (j == 100) {
						break;
					}
					continue;
				}
				if (currentlyItreatingValue.equalsIgnoreCase(basedOnValue.toString())) {
					k = i;
					break;
				} else {
//					System.out.println(basedOnValue + " value is not found under label " + basedOnLabel.toString());
//					AppListeners.appLog.error(basedOnValue + " value is not found under label " + basedOnLabel.toString());
				}
			}
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				wb.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}

		return k;

	}

	public static int getColumnNumberBasedOnLabel(String path, String sheetName, excelLabel label) {
		int k = 0;
		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheet(sheetName);
			int lastColumnNumber = sheet.getRow(0).getLastCellNum();
			for (int i = 0; i < lastColumnNumber; i++) {
				if (getValueBasedOnCellType(sheet.getRow(0).getCell(i)).equalsIgnoreCase(label.toString())) {
					k = i;
					break;
				} else {
					if (i == lastColumnNumber - 1) {
						AppListeners.appLog.info(label.toString() + " is not present in the excel.");
						System.out.println(label.toString() + " is not present in the excel.");
					}
				}
			}
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} finally {
			try {
				wb.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
		return k;
	}
	public static int getColumnNumberBasedOnLabel(String path, String sheetName, String label) {
		int k = 0;
		try {
			fis = new FileInputStream(new File(path));
			wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheet(sheetName);
			int lastColumnNumber = sheet.getRow(0).getLastCellNum();
			for (int i = 0; i < lastColumnNumber; i++) {
				if (getValueBasedOnCellType(sheet.getRow(0).getCell(i)).equalsIgnoreCase(label.toString())) {
					k = i;
					break;
				} else {
					if (i == lastColumnNumber - 1) {
						AppListeners.appLog.info(label.toString() + " is not present in the excel.");
						System.out.println(label.toString() + " is not present in the excel.");
					}
				}
			}
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} finally {
			try {
				wb.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
		return k;
	}

	public static int getLastColumn(String filePath, String sheetName, int rowNum) {
//		return wb.getSheet(sheetName).getPhysicalNumberOfRows();
		int lastColumn = 0;
		try {
			fis = new FileInputStream(new File(filePath));
			wb = WorkbookFactory.create(fis);
			lastColumn = wb.getSheet(sheetName).getRow(rowNum).getLastCellNum();
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} finally {
			try {
				wb.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
		return lastColumn;
	}

	/**
	 * @author Ankur Rana
	 * @param sheetName
	 * @param basedOnLabel:     Label under which primary key is present
	 * @param basedOnValue:     Primary Key based on which value is required.
	 * @param searchUnderLabel: Label under which required value is present
	 * @return Value based on the parameters.
	 */
	public static String readData(String filePath, String sheetName, excelLabel basedOnLabel, String basedOnValue,
			excelLabel searchUnderLabel) {
		try {
			fis = new FileInputStream(new File(filePath));
			wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheet(sheetName);
			return getValueBasedOnCellType(
					sheet.getRow(getRowNumberBasedOnLabelAndValue(filePath, sheetName, basedOnLabel, basedOnValue))
							.getCell(getColumnNumberBasedOnLabel(filePath, sheetName, searchUnderLabel)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} finally {
			try {
				wb.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
		return null;
	}

	public static String getValueBasedOnCellType(Cell cell) {
		String value = "";
		DataFormatter df = new DataFormatter();
		try {
			switch (cell.getCellTypeEnum()) {
			case FORMULA:
				switch (cell.getCachedFormulaResultTypeEnum()) {
				case NUMERIC:
					value = String.valueOf((int) cell.getNumericCellValue());
					break;
				default:
					value = cell.getStringCellValue();
					break;
				}
				break;
			default:
				value = df.formatCellValue(cell);
				break;
			}

		} catch (Exception e) {
		}
		return value;
	}

	/**
	 * @author Azhar Alam
	 * @param key
	 * @return string
	 * @Description this method wite value to specific key in property file
	 *              credentials.properties.
	 */
	public static boolean writedDataFromPropertyFile(String key, String value) {
		boolean flag = false;
		try {
			FileInputStream in = new FileInputStream(System.getProperty("user.dir") + "//credentials.properties");
			Properties props = new Properties();
			props.load(in);
			in.close();

			FileOutputStream out = new FileOutputStream(System.getProperty("user.dir") + "//credentials.properties");
			props.setProperty(key, value);
			props.store(out, null);
			out.close();

			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("deprecation")
	public static String readAllDataForAColumn(String filePath, String sheetName, int cellNum,
			Boolean numericTypeValue) {
		String value = "";
		try {
			File src = new File(filePath);
			FileInputStream fis = new FileInputStream(src);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sh1 = wb.getSheet(sheetName);
			int rowCount = sh1.getLastRowNum();
			System.err.println("rowCount : " + rowCount);
			String a = "";
			DataFormatter df = new DataFormatter();
			for (int row = 1; row <= rowCount; row++) {
				if (numericTypeValue) {
					XSSFRow row1 = sh1.getRow(row);
					XSSFCell cell = row1.getCell(cellNum);
					cell.setCellType(CellType.NUMERIC);
					a = String.valueOf(cell.getNumericCellValue());
//					AppListeners.appLog.info("Id : "+a);
					String[] ss = a.split("E");
//					AppListeners.appLog.info("ss0 >> "+ss[0]);
//					AppListeners.appLog.info("ss1 >> "+ss[1]);
					if (ss.length > 1) {
						Double d = Double.valueOf(ss[0]) * Math.pow(10, Integer.parseInt(ss[1]));
//						System.err.println(d);
						a = String.valueOf(new BigDecimal(Math.round(d)).toBigInteger());
//						AppListeners.appLog.info(" Final Id : "+a);
						value = a + "<break>" + value;
					} else {
//						AppListeners.appLog.info(" Final Id : "+a);
						value = a + "<break>" + value;
					}
				} else {
					a = df.formatCellValue((sh1.getRow(row).getCell(cellNum)));
					if (row < rowCount) {
						value = value + a + "<break>";
					} else {
						value = value + a;
					}

				}
			}
			fis.close();
			wb.close();
		} catch (Exception e) {
			AppListeners.appLog.info("<<<<<<<<<<<<<Exception Error : >>>>>>>>>> :" + e);
			BaseLib.sa.assertTrue(false, "File Not Found : " + filePath);
			value = null;
		}
		return value;

	}

	@SuppressWarnings("deprecation")
	public static List<String> readAllDataFromCSVFileIntoList(String filePath, boolean isColumnIncluded) {
		String line = "";
		List<String> csvRecords = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			int i = 0;
			int k = 0;
			while ((line = br.readLine()) != null) {
				// System.err.println("Line: "+line);
				if (isColumnIncluded && k == 0) {
					csvRecords.add(line);
				} else {
					if (k == 0) {
						k++;
					} else {
						csvRecords.add(line.replace("\"", ""));
					}
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			AppListeners.appLog
					.info(filePath + "<<<<<<<<<<<<<Exception Error :while reading/parsing csv file >>>>>>>>>> :" + e);
			BaseLib.sa.assertTrue(false,
					filePath + "<<<<<<<<<<<<<Exception Error :while reading/parsing csv file >>>>>>>>>> :" + e);
		}
		return csvRecords;

	}

	public static int getColumnNumberFromCSVFileBasedOnLabel(String path, String label) {
		int k = 0;
		label = label.replace("_", " ");
		CSVReader reader = null;
		try {
			File inputFile = new File(path);
			reader = new CSVReader(new FileReader(inputFile));
			List<String[]> csvBody = reader.readAll();
			// System.err.println("csvBody: "+csvBody.size());
			for (int i = 0; i < 1; i++) {
				String[] strArray = csvBody.get(i);
				// System.err.println("strArray: "+strArray.length);
				for (int j = 0; j < strArray.length; j++) {
					if (strArray[j].equalsIgnoreCase(label)) {
						k = j;
						break;
					} else {
						if (j == strArray.length - 1) {
							AppListeners.appLog.info(label.toString() + " is not present in the excel.");
							System.out.println(label.toString() + " is not present in the excel.");
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return k;
	}

	public static int getRowNumberFromCSVFileBasedOnLabelAndValue(String path, String basedOnLabel,
			String basedOnValue) {
		int k = 0;
		basedOnLabel = basedOnLabel.replace("_", " ");
		CSVReader reader = null;
		try {
			File inputFile = new File(path);
			reader = new CSVReader(new FileReader(inputFile));
			List<String[]> csvBody = reader.readAll();
			// System.err.println("csvBody: "+csvBody.size());
			String currentlyItreatingValue = "";
			int j = 0;
			int searchInColumnNumber = getColumnNumberFromCSVFileBasedOnLabel(path, basedOnLabel);
			for (int i = 1; i < csvBody.size() - 1; i++) {
				try {
					currentlyItreatingValue = csvBody.get(i)[searchInColumnNumber];
				} catch (NullPointerException e) {
					j++;
					if (j == 100) {
						break;
					}
					continue;
				}
				if (currentlyItreatingValue.equalsIgnoreCase(basedOnValue.toString())) {
					k = i;
					break;
				} else {
//					System.out.println(basedOnValue + " value is not found under label " + basedOnLabel.toString());
//					AppListeners.appLog.error(basedOnValue + " value is not found under label " + basedOnLabel.toString());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}

		return k;

	}

	public static String readDataFromCSVFile(String filePath, String basedOnLabel, String basedOnValue,
			String searchUnderLabel) {
		CSVReader reader = null;
		try {

			int r = ExcelUtils.getRowNumberFromCSVFileBasedOnLabelAndValue(filePath, basedOnLabel, basedOnValue);
			System.err.println("Row : " + r);
			int c = ExcelUtils.getColumnNumberFromCSVFileBasedOnLabel(filePath, searchUnderLabel);
			System.err.println("Column : " + c);

			System.err.println("Row Column : " + r + " " + c);
			File inputFile = new File(filePath);
			reader = new CSVReader(new FileReader(inputFile));
			List<String[]> csvBody = reader.readAll();
			System.err.println("Size: " + csvBody.size());
			return csvBody.get(r)[c];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean writeDataOnCSVFile(String path, Object dataToWrite, String basedOnLabel, String basedOnValue,
			String writeUnderLabel) {
		try {
			writeDataInCSVFile(path, dataToWrite,
					getRowNumberFromCSVFileBasedOnLabelAndValue(path, basedOnLabel, basedOnValue),
					getColumnNumberFromCSVFileBasedOnLabel(path, writeUnderLabel));
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}

		return false;
	}

	public static void writeDataInCSVFile(String path, Object msg, int rowNum, int cellNum) {

		try {
			File inputFile = new File(path);

			// Read existing file
			CSVReader reader = new CSVReader(new FileReader(inputFile));
			List<String[]> csvBody = reader.readAll();
			System.err.println("csvBody: " + csvBody.size());
			csvBody.get(rowNum)[cellNum] = msg.toString();
			reader.close();

			// Write to CSV file which is open
			CSVWriter writer = new CSVWriter(new FileWriter(inputFile));
			writer.writeAll(csvBody);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<String[]> readAllDataFromCSVFile(String path) {
		List<String[]> csvBody = null;
		CSVReader reader = null;
		try {
			File inputFile = new File(path);

			// Read existing file
			reader = new CSVReader(new FileReader(inputFile));
			csvBody = reader.readAll();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return csvBody;
	}

	public static boolean writeOneRecordOnCSV(String path, String recordValue) {
		boolean flag = false;
		try {
			String csv = path;
			CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
			String[] record = recordValue.split(",");
			writer.writeNext(record);
			writer.close();
			flag = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * @author Ankur Huria
	 * @param sheetName
	 * @param basedOnLabel:     Label under which primary key is present
	 * @param basedOnValue:     Primary Key based on which value is required.
	 * @param searchUnderLabel: Label under which required value is present
	 * @param Workbook:         Opened Workbook should be there
	 * @return Value based on the parameters.
	 */
	public static String readData(Workbook wb, String filePath, String sheetName, excelLabel basedOnLabel,
			String basedOnValue, excelLabel searchUnderLabel) {
		try {
			Sheet sheet = wb.getSheet(sheetName);
			return getValueBasedOnCellType(
					sheet.getRow(getRowNumberBasedOnLabelAndValue(wb, filePath, sheetName, basedOnLabel, basedOnValue))
							.getCell(getColumnNumberBasedOnLabel(wb, filePath, sheetName, searchUnderLabel)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @author Ankur Huria
	 * @param wb
	 * @param sheetName
	 * @param label:
	 * @param Value:    
	 * @return Row No. based on Label.
	 */

	public static int getRowNumberBasedOnLabelAndValue(Workbook wb, String path, String sheetName,
			excelLabel basedOnLabel, String basedOnValue) {
		int k = 0;
		try {

			String currentlyItreatingValue = "";
			Sheet sheet = wb.getSheet(sheetName);
			int j = 0;
			int searchInColumnNumber = getColumnNumberBasedOnLabel(wb, path, sheetName, basedOnLabel);
			for (int i = 0; i >= 0; i++) {
				try {
					currentlyItreatingValue = getValueBasedOnCellType(sheet.getRow(i).getCell(searchInColumnNumber));
				} catch (NullPointerException e) {
					j++;
					if (j == 100) {
						break;
					}
					continue;
				}
				if (currentlyItreatingValue.equalsIgnoreCase(basedOnValue.toString())) {
					k = i;
					break;
				} else {
//				System.out.println(basedOnValue + " value is not found under label " + basedOnLabel.toString());
//				AppListeners.appLog.error(basedOnValue + " value is not found under label " + basedOnLabel.toString());
				}
			}
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;

	}

	
	/**
	 * @author Ankur Huria
	 * @param wb
	 * @param sheetName
	 * @param label:    
	 * @return Column No. based on Label.
	 */
	public static int getColumnNumberBasedOnLabel(Workbook wb, String path, String sheetName, excelLabel label) {
		int k = 0;
		try {

			Sheet sheet = wb.getSheet(sheetName);
			int lastColumnNumber = sheet.getRow(0).getLastCellNum();
			for (int i = 0; i < lastColumnNumber; i++) {
				if (getValueBasedOnCellType(sheet.getRow(0).getCell(i)).equalsIgnoreCase(label.toString())) {
					k = i;
					break;
				} else {
					if (i == lastColumnNumber - 1) {
						AppListeners.appLog.info(label.toString() + " is not present in the excel.");
						System.out.println(label.toString() + " is not present in the excel.");
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
//		e.printStackTrace();
		}
		return k;
	}

	public static HashMap<String, ArrayList<String>> dataRead(String filePath, String sheetName, excelLabel basedOnLabel, String basedOnValue) {
		HashMap<String, ArrayList<String>> map=	new HashMap<String,ArrayList<String>>();
		ArrayList<String> headerList= new ArrayList<>();
		ArrayList<String> headervalue= new ArrayList<>();
		try {
			fis = new FileInputStream(new File(filePath));
			wb = WorkbookFactory.create(fis);
			Sheet sheet = wb.getSheet(sheetName);
			int row=ExcelUtils.getRowNumberBasedOnLabelAndValue(filePath, sheetName, basedOnLabel, basedOnValue);
			int lastColumnNumber = sheet.getRow(0).getLastCellNum();
			for(int i=3;i<lastColumnNumber;i++) {
				
				headerList.add(ExcelUtils.getValueBasedOnCellType(sheet.getRow(0).getCell(i)).replaceAll("_", " "));
			}
			
			for(String header:headerList) {
				headervalue.add(ExcelUtils.getValueBasedOnCellType(sheet.getRow(row).getCell(ExcelUtils.getColumnNumberBasedOnLabel(filePath, sheetName, header))));

				
			}
			
			
			System.out.println("List1:"+String.valueOf(headerList));
			System.out.println("List2:"+headervalue);
			map.put("headers", headerList);
			map.put("value", headervalue);
			//return getValueBasedOnCellType();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} finally {
			try {
				wb.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
		return map;
	}
	
	
	 /* @author Ravi kumar
	  * @param filepath
	 * @param sheetName
	 * @param columnName
	 * @return String
	 * @description This method read the cell data according to column Name passed.
	 */
	public static String readDataForResearch(String filepath, String sheetName, String columnName) {
		String value = "";
		String testCaseName;
		String tcName = AppListeners.currentlyExecutingTC;
		String colName;
		int cellNum = 0;
		try {
			fis = new FileInputStream(new File(filepath));
			wb = WorkbookFactory.create(fis);
			int lastRow = wb.getSheet(sheetName).getLastRowNum();
			System.out.println(lastRow);
			for (int i = 1; i <= lastRow; i++) {//changed value of i from 1 to 2
				try {
					Cell cell = wb.getSheet(sheetName).getRow(i).getCell(0);
					testCaseName = getValueBasedOnCellType(cell);
					if (!testCaseName.isEmpty() && tcName.contains(testCaseName)) {
						int lastColumnNum = wb.getSheet(sheetName).getRow(0).getLastCellNum();
						System.out.println(lastColumnNum);
						for (int j = 0; j <= lastColumnNum; j++) {
							try {
								cell = wb.getSheet(sheetName).getRow(0).getCell(j);
								colName = getValueBasedOnCellType(cell);
								if (!colName.isEmpty() && colName.equalsIgnoreCase(columnName)) {
									cellNum = j;
									break;
								} else {
									if (j == lastColumnNum) {
										AppListeners.appLog.info(columnName + " is not found.");
										return "";
									}
									continue;
								}
							} catch (Exception e) {
								AppListeners.appLog.info("Column number '" + j
										+ "' is blank. So will not be able to read data from column number '" + j
										+ "'. Kindly Delete the column or enter some value.");
							}
						}
						cell = wb.getSheet(sheetName).getRow(i).getCell(cellNum);
						value = getValueBasedOnCellType(cell);
						break;
					} else {
						if (i == lastRow) {
							AppListeners.appLog.info(tcName + " is not found.");
							break;
						}
						continue;
					}
				} catch (Exception e) {
					AppListeners.appLog
							.info("Row number '" + i + "' is blank. So will not be able to read data from row number '"
									+ i + "'. Kindly Delete the row or enter some value.");
				}
			}
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AppListeners.appLog
					.info("File Path '" + path + "' or Sheet Name '" + sheetName + "' is wrong kindly re-check.");
			return null;
		}
		return value;

	}
	
	
	public static String readData(Workbook wb,  String sheetName, int rowNum, int cellNum) {
		String value = "";
		try {
			
//			wb.getSheet(sheetName).getRow(rowNum).getLastCellNum();
			Cell cell = wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum);
			value = getValueBasedOnCellType(cell);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AppListeners.appLog.info("There is no value at the location you passed.");
			return null;
		}
		return value;

	}
}
