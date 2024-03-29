package com.navatar.pageObjects;

import static com.navatar.generic.CommonLib.*;
import static com.navatar.generic.SmokeCommonVariables.Smoke_STDTask1Subject;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.BaseLib;
import com.navatar.generic.CommonLib;
import com.navatar.generic.ExcelUtils;
import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.PageLabel;
import com.navatar.generic.EnumConstants.PageName;
import com.navatar.generic.EnumConstants.PopUpName;
import com.navatar.generic.EnumConstants.TaskRayProjectButtons;
import com.navatar.generic.EnumConstants.YesNo;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.excelLabel;
import com.relevantcodes.extentreports.LogStatus;

import com.relevantcodes.extentreports.LogStatus;

import static com.navatar.generic.CommonLib.*;

public class TaskPageBusinessLayer extends TaskPage {

	public TaskPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param pageName
	 * @param fieldsWithValues
	 * @param action
	 * @param timeOut
	 * @return true/false
	 * @description this is used to verify fields present on task page
	 */
	public boolean fieldVerificationForTaskInViewMode(String projectName, PageName pageName,
			String[][] fieldsWithValues, action action, int timeOut) {
		refresh(driver);
		String label;
		String value;
		String actualValue = "";
		WebElement ele;
		boolean flag = true;
		for (String[] fieldWithValue : fieldsWithValues) {

			value = fieldWithValue[1];
			label = fieldWithValue[0];
			if (label.equalsIgnoreCase(PageLabel.Related_Associations.toString())
					|| label.equalsIgnoreCase(PageLabel.Related_Contacts.toString()))
				ThreadSleep(10000);
			switchToFrame(driver, 10, getFrame(PageName.TaskPage, 30));
			ele = getLabelForTaskInViewMode(projectName, pageName, label, action, timeOut);
			label = fieldWithValue[0].replace("_", " ");

			if (ele != null) {
				if (fieldWithValue[0].equalsIgnoreCase(PageLabel.Watchlist.toString())
						|| fieldWithValue[0].equalsIgnoreCase(PageLabel.Watch_list.toString()))
					actualValue = ele.getAttribute("alt");
				else
					actualValue = ele.getText().trim();
				if (value == null || value.equals("")) {
					flag = actualValue.equals(value);
				} else {
					if (fieldWithValue[0].equalsIgnoreCase(PageLabel.Due_Date.toString())) {
						flag = verifyDate(value, actualValue);
						// flag=value.contains(actualValue);
					} else if (fieldWithValue[0].equalsIgnoreCase(PageLabel.Comments.toString())
							|| fieldWithValue[0].equalsIgnoreCase(PageLabel.Watchlist.toString())
							|| (fieldWithValue[0].equalsIgnoreCase(PageLabel.Watch_list.toString()))) {
						flag = actualValue.equals(value);
					} else {
						flag = actualValue.contains(value);
					}

				}
				/*
				 * if (label.equalsIgnoreCase(ActivityRelatedLabel.Due_Date.toString()))
				 * flag=value.contains(actualValue);
				 */
				if (flag) {
					log(LogStatus.INFO, actualValue + " matched with " + value + " For Label : " + label, YesNo.No);
				} else {
					log(LogStatus.ERROR,
							"Actual : " + actualValue + " Expected : " + value + " not matched For Label : " + label,
							YesNo.Yes);
					BaseLib.sa.assertTrue(false,
							"Actual : " + actualValue + " Expected : " + value + " not matched For Label : " + label);
				}
			} else {
				flag = false;
				log(LogStatus.ERROR, "Label Not Found : " + label + " so can not verify value : " + value, YesNo.No);
				BaseLib.sa.assertTrue(false, "Label Not Found : " + label + " so can not verify value : " + value);
			}
			switchToDefaultContent(driver);
		}
		return flag;

	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param pageName
	 * @param label
	 * @param action
	 * @param timeOut
	 * @return WebElement
	 * @description this method is used to return text of fields present on task
	 *              page in view mode
	 */
	public WebElement getLabelForTaskInViewMode(String projectName, PageName pageName, String label, action action,
			int timeOut) {

		String xpath = "";
		String fieldLabel = label.replace("_", " ");
		;
		switchToDefaultContent(driver);
		if (PageLabel.Related_Associations.toString().equals(label)
				|| PageLabel.Related_Contacts.toString().equals(label)) {
			switchToDefaultContent(driver);
			switchToFrame(driver, 20, getTaskPageFrame(projectName, timeOut));
			xpath = "//label[text()='" + fieldLabel + "']//following-sibling::div";

		} else if (PageLabel.Comments.toString().equals(label)) {
			xpath = "//span[text()='" + fieldLabel + "']/../following-sibling::div//span/span";
		} else if (PageLabel.Name.toString().equals(label)) {
			xpath = "//span[text()='" + fieldLabel + "']/../following-sibling::div";
		} else if (PageLabel.Watchlist.toString().equals(label) || PageLabel.Watch_list.toString().equals(label)) {
			xpath = "//span[text()='" + fieldLabel + "']/../following-sibling::div//img";
		} else {
			xpath = "//span[text()='" + fieldLabel + "']/../following-sibling::div";
		}
		WebElement ele = FindElement(driver, xpath, fieldLabel, action, timeOut);
		scrollDownThroughWebelement(driver, ele, fieldLabel);
		ele = isDisplayed(driver, ele, "Visibility", timeOut, fieldLabel);
		return ele;

	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param taskName
	 * @return String
	 * @description this is used to return successful task created message
	 */
	public String taskCreatesMsg(String projectName, String taskName) {
		return "Task \"" + taskName + "\" was saved." + "Task \"" + taskName + "\" was created." + "Task " + taskName
				+ " was created" + "Task " + taskName + " was created.";

	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param pageLabel
	 * @param text
	 * @return String
	 * @description this is used to return String by replacing '_'
	 */
	public String Comment(String projectName, PageLabel pageLabel, String text) {
		String label = pageLabel.toString().replace("_", " ");
		String value = label + ": " + text;
		return value;

	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param taskName
	 * @return String
	 * @description this is used to return successful task created message
	 */
	public String taskSavedMsg(String projectName, String taskName) {
		return "Task " + taskName + " was saved";

	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param timeOut
	 * @return true if Related Contact Field Value is Blank on Task Page
	 */
	public boolean isRelatedContactEmpty(String projectName, int timeOut) {
		WebElement ele = null;
		String actual = null;
		boolean flag = false;
		switchToDefaultContent(driver);
		switchToFrame(driver, 10, getFrame(PageName.TaskPage, 30));
		ele = getRelatedContactsLabel(projectName, timeOut);
		try {
			if (ele != null) {
				actual = ele.getText().trim();

				if (actual.isEmpty() || actual.equals("")) {
					log(LogStatus.INFO, "Related Contact is empty ", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Related Contact Should be empty : Actual Value : " + actual, YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Related Contact Ele not Found", YesNo.Yes);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log(LogStatus.ERROR, "Exception For Related Contact Ele As it is blank", YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param timeOut
	 * @return true if comment is empty
	 */
	public boolean isCommentsEmpty(String projectName, int timeOut) {
		WebElement ele = null;
		String actual = null;
		boolean flag = false;
		switchToDefaultContent(driver);
		switchToFrame(driver, 10, getFrame(PageName.TaskPage, 30));
		ele = getCommentsLabel(projectName, timeOut);
		try {
			if (ele != null) {
				actual = ele.getText().trim();

				if (actual.isEmpty() || actual.equals("")) {
					log(LogStatus.INFO, "Comments is empty ", YesNo.No);
					flag = true;
				} else {
					log(LogStatus.ERROR, "Comments Should be empty : Actual Value : " + actual, YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Comments Ele not Found", YesNo.Yes);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log(LogStatus.ERROR, "Exception For Comments Ele As it is blank", YesNo.Yes);
		}
		switchToDefaultContent(driver);
		return flag;
	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param taskName
	 * @return String
	 * @description this is used to return successful task deleted message
	 */
	public String taskDeletedMsg(String projectName, String taskName) {
		// return "Task \""+taskName+"\" was deleted";
		return "was deleted";

	}

	/**
	 * @author Azhar Alam
	 * @param projectName
	 * @param taskName
	 * @return String
	 * @description this is used to return successful task restored message
	 */
	public String restoreItemMsg(String projectName, String restoreItem) {
		// return "\""+restoreItem+"\" was restored";
		return "was restored";

	}

	/**
	 * @author Akul Bhutani
	 * @param projectName
	 * @param pageName
	 * @param fieldsWithValues
	 * @param action
	 * @param timeOut
	 * @return true/false
	 * @description this is used to verify fields present on task page
	 */
	public boolean fieldVerificationForTaskInViewMode1(String projectName, PageName pageName,
			String[][] fieldsWithValues, action action, int timeOut) {
		refresh(driver);
		String label;
		String value;
		String actualValue = "";
		WebElement ele;
		boolean flag = false;
		for (String[] fieldWithValue : fieldsWithValues) {
			value = fieldWithValue[1];
			label = fieldWithValue[0];
			if (label.equalsIgnoreCase(PageLabel.Related_Associations.toString())
					|| label.equalsIgnoreCase(PageLabel.Related_Contacts.toString()))
				ThreadSleep(10000);
			switchToFrame(driver, 10, getFrame(PageName.TaskPage, 30));
			ele = getLabelForTaskInViewMode(projectName, pageName, label, action, timeOut);
			label = fieldWithValue[0].replace("_", " ");

			if (ele != null) {
				if (fieldWithValue[0].equalsIgnoreCase(PageLabel.Watchlist.toString())
						|| fieldWithValue[0].equalsIgnoreCase(PageLabel.Watch_list.toString()))
					actualValue = ele.getAttribute("alt");
				else
					actualValue = ele.getText().trim();
				if (value == null || value.equals("")) {
					flag = actualValue.equals(value);
				} else {
					if (fieldWithValue[0].equalsIgnoreCase(PageLabel.Due_Date.toString())) {
						flag = verifyDate(value, actualValue);
					} else if (fieldWithValue[0].equalsIgnoreCase(PageLabel.Comments.toString())
							|| fieldWithValue[0].equalsIgnoreCase(PageLabel.Watchlist.toString())
							|| (fieldWithValue[0].equalsIgnoreCase(PageLabel.Watch_list.toString()))) {
						flag = actualValue.equals(value);
					} else {
						flag = actualValue.contains(value);
					}

				}
			}
			switchToDefaultContent(driver);
		}
		return flag;
	}

	public boolean EditEnterDueDateAndSave(String projectName, String task, String dueDate) {
		boolean flag = false;
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		for (int j = 0; j < 2; j++) {
			if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Edit Button For : " + task, YesNo.No);
				if (sendKeys(driver, tp.getdueDateTextBoxInNewTask(projectName, 20), dueDate, "Due Date",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Value Entered to Due Date " + dueDate, YesNo.No);
					ThreadSleep(2000);
					click(driver, gp.getLabelTextBoxForGobalAction(projectName, GlobalActionItem.New_Task,
							PageLabel.Subject.toString(), 5), "", action.BOOLEAN);
					ThreadSleep(2000);
					if (clickUsingJavaScript(driver, getCustomTabSaveBtn(projectName, 20), "save",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "successfully Updated task : " + task, YesNo.No);
						ThreadSleep(5000);
						flag = true;
						String[][] fieldsWithValues = { { PageLabel.Due_Date.toString(), dueDate } };
						flag = tp.fieldVerificationForTaskInViewMode1(projectName, PageName.TaskPage, fieldsWithValues,
								action.BOOLEAN, 30);
						if (flag) {
							return flag;
						} else {
						}
					} else {
						log(LogStatus.ERROR, "save button is not clickable so task not Updated : " + task, YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Entered Value to Due Date " + dueDate, YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Edit Button For : " + task, YesNo.Yes);
			}
		}

		return flag;
	}

	public boolean EditEnterNameAndSave(String projectName, String task, String Name, boolean removeOtherName) {
		boolean flag = false;
		TaskPageBusinessLayer tp = new TaskPageBusinessLayer(driver);
		GlobalActionPageBusinessLayer gp = new GlobalActionPageBusinessLayer(driver);
		for (int j = 0; j < 2; j++) {
			if (click(driver, tp.getEditButton(projectName, 30), task, action.BOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Edit Button For : " + task, YesNo.No);

				if (removeOtherName) {
					int i = 0;
					List<WebElement> list = tp.getCrossIconOfSelectedItem(excelLabel.Name.toString(), 30);
					for (WebElement ele : list) {
						i++;
						click(driver, ele, "delete icon", action.BOOLEAN);
						log(LogStatus.INFO, "Remove conatct name from name field:" + i, YesNo.No);

					}

				}
				ThreadSleep(2000);
				click(driver, FindElement(driver, "//span[text()='Name']/ancestor::label/following-sibling::div", "",
						action.BOOLEAN, 30), "", action.BOOLEAN);
				ThreadSleep(2000);
				if (sendKeys(driver, tp.getnameTextBoxInNewTask(projectName, 30), Name, "Name Text box",
						action.BOOLEAN)) {
					log(LogStatus.INFO, "Value Entered to Name " + Name, YesNo.No);
					ThreadSleep(2000);
					WebElement ele = FindElement(driver, "//div[@title='" + Name + "']", "", action.BOOLEAN, 30);
					clickUsingJavaScript(driver, ele, "", action.BOOLEAN);
					ThreadSleep(2000);
					if (clickUsingJavaScript(driver, getCustomTabSaveBtn(projectName, 20), "save",
							action.SCROLLANDBOOLEAN)) {
						log(LogStatus.INFO, "successfully Updated task : " + task, YesNo.No);
						ThreadSleep(5000);
						flag = true;
						String[][] fieldsWithValues = { { PageLabel.Name.toString(), Name } };
						flag = tp.fieldVerificationForTaskInViewMode1(projectName, PageName.TaskPage, fieldsWithValues,
								action.BOOLEAN, 30);
						if (flag) {
							return flag;
						} else {
						}
					} else {
						log(LogStatus.ERROR, "save button is not clickable so task not Updated : " + task, YesNo.Yes);
					}
				} else {
					log(LogStatus.ERROR, "Not Able to Entered Value to name " + Name, YesNo.Yes);
				}
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Edit Button For : " + task, YesNo.Yes);
			}
		}

		return flag;
	}

	public boolean editCommentsIntask(String task, String updatedValue) {
		boolean flag = false;

		if (click(driver, editCommentsButton(30), "", action.BOOLEAN)) {
			log(LogStatus.INFO, "Clicked on Edit Comments Button", YesNo.No);
			if (CommonLib.clearTextBox(commentTextArea(20))) {
				log(LogStatus.INFO, "Cleared the TextArea", YesNo.No);
				if (sendKeys(driver, commentTextArea(20), updatedValue, "TextArea: ", action.BOOLEAN)) {
					log(LogStatus.INFO, "Entered the Value: " + updatedValue, YesNo.No);

					if (click(driver, commentTextAreaSaveButton(30), "", action.BOOLEAN)) {
						log(LogStatus.INFO, "Clicked on Save Button", YesNo.No);
						CommonLib.refresh(driver);
						CommonLib.ThreadSleep(3000);
						String actualCommentValue = CommonLib.getText(driver, commentsLabelValueInTaskDetailPage(20),
								"commentsLabelValueInTaskDetailPage", action.BOOLEAN);
						for (int i = 0; i <= 5; i++) {
							if (actualCommentValue.equals("")) {

								actualCommentValue = CommonLib.getText(driver, commentsLabelValueInTaskDetailPage(20),
										"commentsLabelValueInTaskDetailPage", action.BOOLEAN);
							} else {
								break;
							}
						}
						if (actualCommentValue.equals(updatedValue)) {
							log(LogStatus.INFO, "Value of Comments in Task Detail Matched and i.e. " + updatedValue,
									YesNo.No);
							flag = true;
						} else {
							log(LogStatus.ERROR, "Value of Comments in Task Detail not Matched, Expected: "
									+ updatedValue + " and Actual: " + actualCommentValue, YesNo.Yes);
						}

					} else {
						log(LogStatus.ERROR, "Not Able to Click on Save Button For : " + task, YesNo.Yes);

					}

				} else {
					log(LogStatus.ERROR, "Not able to Enter the Value: " + updatedValue, YesNo.Yes);
				}

			} else {
				log(LogStatus.ERROR, "Not Able to clear the text Area for task: " + task, YesNo.Yes);
			}
		} else {
			log(LogStatus.ERROR, "Not Able to Click on Edit Comments Button For : " + task, YesNo.Yes);

		}
		return flag;

	}

	/**
	 * @author Ankur Huria
	 * @param buttonName
	 * @param timeOut
	 * @return true if able to click on button
	 */
	public boolean clickOnRecordPageButtonOrInDownArrowButton(String buttonName, int timeOut) {
		boolean flag = false;
		if (recordDetailPageButton(buttonName, timeOut) != null) {
			if (click(driver, recordDetailPageButton(buttonName, timeOut), "Button " + buttonName,
					action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Button: " + buttonName + " on Record Form Page", YesNo.No);
				flag = true;
			} else {
				log(LogStatus.ERROR, "Not Able to Click on Button: " + buttonName + " on Record Form Page", YesNo.Yes);

			}
		}

		else if (recordDetailPageDownArrowButton(timeOut) != null) {
			log(LogStatus.INFO, "Down Arrow Button is Present", YesNo.No);
			if (click(driver, recordDetailPageDownArrowButton(timeOut), "DownArrowButton", action.SCROLLANDBOOLEAN)) {
				log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);
				CommonLib.ThreadSleep(2000);

				List<String> dropDownButtonsList = downArrowButtonList().stream()
						.map(x -> x.getAttribute("title").trim()).collect(Collectors.toList());

				if (!dropDownButtonsList.isEmpty()) {
					log(LogStatus.INFO, "No. of Buttons Present on DownArrow Button are: " + dropDownButtonsList.size(),
							YesNo.No);

					int i = 0;
					for (String button : dropDownButtonsList) {
						if (button.equalsIgnoreCase(buttonName)) {
							log(LogStatus.INFO, "Button Found in Down Arrow Button", YesNo.No);

							if (click(driver, downArrowButtonList().get(i), "DownArrowButton",
									action.SCROLLANDBOOLEAN)) {
								log(LogStatus.INFO, "Clicked on Down Arrow Button", YesNo.No);
								flag = true;
								break;
							}

							else {
								log(LogStatus.ERROR,
										"Not Able to Click on Button: " + buttonName + " on Record Form Page",
										YesNo.Yes);

							}

						}

						i++;

					}

				}

				else {
					log(LogStatus.ERROR, "No Buttons Are Present in DownArrow Button", YesNo.Yes);

				}

			} else {
				log(LogStatus.INFO, "Not able to Click on Down Arrow Button", YesNo.No);
			}
		}

		else {
			log(LogStatus.ERROR, "Down Arrow Button is not Present on this Page, So No button: " + buttonName
					+ " present on this Page", YesNo.Yes);

		}
		return flag;
	}

}
