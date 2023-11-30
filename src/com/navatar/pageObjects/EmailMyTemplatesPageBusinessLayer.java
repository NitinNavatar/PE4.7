package com.navatar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navatar.generic.EnumConstants.Mode;
import com.navatar.generic.EnumConstants.action;
import com.navatar.generic.EnumConstants.object;
import com.navatar.generic.EnumConstants.ObjectFeatureName;

import static com.navatar.generic.AppListeners.appLog;
import static com.navatar.generic.CommonLib.*;

public class EmailMyTemplatesPageBusinessLayer extends EmailMyTemplatesPage {

	public EmailMyTemplatesPageBusinessLayer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author Azhar Alam
	 * @param environment
	 * @param mode
	 * @param emailTemplateFolderName
	 * @param folderUniqueName
	 * @param folderAccess
	 * @return true if able to create Email Folder Template
	 */
	public boolean createCustomEmailFolder(String environment, String mode, String emailTemplateFolderName,
			FolderAccess folderAccess) {
		boolean flag = false;
		if(searchStandardOrCustomObject(environment, mode, object.CommunicationTemplates)) {
			if (clickOnObjectFeature(environment, mode, object.CommunicationTemplates, ObjectFeatureName.myTemplates)) {
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
					
				}
				System.err.println("1>>>>>>>>>>");
				if (click(driver, getCreateNewFolderLink(environment, mode, 30), "Create New Folder Link",
						action.SCROLLANDBOOLEAN)) {
					System.err.println("2>>>>>>>>>>>>>>>>>>>");
					appLog.info("Clicked on Create New Folder Link");
					
					if (sendKeys(driver, getEmailTemplateFolderLabelTextBox(environment, mode, 30), emailTemplateFolderName,
							emailTemplateFolderName, action.SCROLLANDBOOLEAN)) {
						appLog.info("Entered value on email Template Folder Name : " + emailTemplateFolderName);
						
						if (selectVisibleTextFromDropDown(driver, getpublicFolderAccesDropdown(environment, mode, 10),
								folderAccess + "   : Access dropdown", folderAccess)) {
							appLog.info("Selected Folder Access : " + folderAccess);
							
							if (click(driver, getSaveButtonforFolderTempalte(environment, mode, 60), "Save Button",
									action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on Save Button");
								
								WebElement ele = FindElement(driver,
										"//h1[contains(text(),'" + emailTemplateFolderName + "')]", "Header",
										action.SCROLLANDBOOLEAN, 30);
								if (ele != null) {
									appLog.info("Folder created and Matched :  " + emailTemplateFolderName);
									flag = true;
								} else {
									appLog.error("Folder created but not Matched :  " + emailTemplateFolderName);
								}
								
							} else {
								appLog.error("Not Able to Click on Save Button");
							}
						} else {
							appLog.error("Not Able to Select from drop down : " + folderAccess);
						}
						
					} else {
						appLog.error("Not Able to Enter value on Email Template Folder Label Text Box");
					}
				} else {
					appLog.error("Not Able to Click on Create New Folder Link");
				}
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToDefaultContent(driver);
				}
			} else {
				appLog.error("Not Able to Click on My Templates on Email Setup");
			}
		}else {
			appLog.error("Not able to search Object : "+object.Email+" so cannot create new email template folder: "+emailTemplateFolderName);
		}

		return flag;

	}
	
	/**
	 * @param environment
	 * @param mode
	 * @param emailfolderName
	 * @param templateType
	 * @param emailTemplateName
	 * @param description
	 * @param emailSubject
	 * @param emailBody
	 * @return true if ble to create Email Template
	 */
	public boolean createCustomEmailTemplate(String environment, String mode, String emailfolderName,
			EmailTemplateType templateType, String emailTemplateName, String description, String emailSubject, String emailBody) {
		boolean flag = false;
		
		if (clickOnObjectFeature(environment, mode, object.CommunicationTemplates, ObjectFeatureName.myTemplates)) {
			if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
				switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
			}
			if (selectVisibleTextFromDropDown(driver, getViewDropdown(60), "View dropdown : " + emailfolderName,
					emailfolderName)) {
				appLog.info("Selected Email folder : " + emailfolderName);
				
				if (click(driver, getCreateNewTempalteButton(environment, mode, 30), "Create New Template Button",
						action.SCROLLANDBOOLEAN)) {
					appLog.info("Clicked on New Template Button");
					
					WebElement ele = FindElement(driver, "//label[contains(text(),'" + templateType + "')]/../input",
							templateType.toString(), action.SCROLLANDBOOLEAN, 20);
					if (click(driver, ele, templateType + " Radio Button", action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked on Radio Button for : "+templateType);
						
						if (click(driver, getNextButton(environment, mode, 10), "Next Button",
								action.SCROLLANDBOOLEAN)) {
							appLog.info("Clicked on Next Button ");
							
							if (sendKeys(driver, getEmailTemplateNameTextBox(environment, mode, 30), emailTemplateName,
									emailTemplateName, action.SCROLLANDBOOLEAN)) {
								appLog.info("Entered value on Email Template Text Box : "+emailTemplateName);
								
								if (click(driver, getAvailableForUseCheckBox(environment, mode, 10), "Next Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.info("Clicked on Available Use CheckBox");
									
									if (sendKeys(driver, getDescriptionTextBox(environment, mode, 30), description,
											"Description ", action.SCROLLANDBOOLEAN)) {
										appLog.info("Entered value on description Text Box : "+description);
										
										if (sendKeys(driver, getSubjectTextBox(environment, mode, 30), emailSubject,
												"Subject ", action.SCROLLANDBOOLEAN)) {
											appLog.info("Entered value on Subject Text Box : "+emailSubject);
											
											if (sendKeys(driver, getEmailBodyTextArea(environment, mode, 30), emailBody,
													"Body Text Area ", action.SCROLLANDBOOLEAN)) {
												appLog.info("Entered value on Body Text Area : "+emailBody);
												
												if (click(driver, getSaveButtonforFolderTempalte(environment, mode, 60), "Save Button",
														action.SCROLLANDBOOLEAN)) {
													appLog.info("Clicked on Save Button");
													
													ele = FindElement(driver,
															"//h2[contains(text(),'" + emailTemplateName + "')]", "Header",
															action.SCROLLANDBOOLEAN, 30);
													if (ele != null) {
														appLog.info("Template created and Matched :  "+emailTemplateName);
														flag = true;
													} else {
														appLog.error("Template created but not Matched :  "+emailTemplateName);
													}
													
												} else {
													appLog.error("Not Able to Clicked on Save Button");
												}

											} else {
												appLog.error("Not Able to Enter value on Body Text Area");
											}

										} else {
											appLog.error("Not Able to Enter value on Subject Text Box");
										}

									} else {
										appLog.error("Not Able to Enter value on Description Text Box");
									}
								} else {
									appLog.error("Not Able to Click on CheckBox");
								}
							} else {
								appLog.error("Not Able to Enter value on Email Template Text Box");
							}
						} else {
							appLog.error("Not Able to Click on next Button");
						}
					} else {
						appLog.error("Not Able to Click on Radio Button for : " + templateType);
					}
				} else {
					appLog.error("Not Able to Click on Create New Template Button");
				}
			} else {
				appLog.error("Not Able to Select from drop down : " + emailfolderName);
			}

			if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
				switchToDefaultContent(driver);
			}
		}
		return flag;

	}
	
	 public boolean createCustomEmailTemplateHTML(String environment, String mode, String emailfolderName,
				EmailTemplateType templateType, String emailTemplateName, String description, String emailSubject, String emailBody) {
			boolean flag = false;
			if(searchStandardOrCustomObject(environment, mode, object.CommunicationTemplates)) {
			if (clickOnObjectFeature(environment, mode, object.CommunicationTemplates, ObjectFeatureName.myTemplates)) {
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
				}
				if (selectVisibleTextFromDropDown(driver, getViewDropdown(60), "View dropdown : " + emailfolderName,
						emailfolderName)) {
					appLog.info("Selected Email folder : " + emailfolderName);
					
					if (click(driver, getCreateNewTempalteButton(environment, mode, 30), "Create New Template Button",
							action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked on New Template Button");
						
						WebElement ele = FindElement(driver, "//label[contains(text(),'" + templateType + "')]/../input",
								templateType.toString(), action.SCROLLANDBOOLEAN, 20);
						if (click(driver, ele, templateType + " Radio Button", action.SCROLLANDBOOLEAN)) {
							appLog.info("Clicked on Radio Button for : "+templateType);
							
							if (click(driver, getNextButton(environment, mode, 10), "Next Button",
									action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on Next Button ");
								ThreadSleep(5000);
								
								if (click(driver, getGotoLetterheadButton(environment, mode, 10), "Go to Letterhead Button",
										action.SCROLLANDBOOLEAN)) {
									appLog.info("Clicked on Go to Letterhead Button");
									
									if (click(driver, getNextButton(environment, mode, 10), "Next Button",
											action.SCROLLANDBOOLEAN)) {
										appLog.info("Clicked on Next Button ");
										
										if (click(driver, getCreateNewLetterheadButton(environment, mode, 30), "Create NewLetterhead Button",
												action.SCROLLANDBOOLEAN)) {
											appLog.info("Clicked on New Letterhead Button");
										
								if (sendKeys(driver, getLetterheadLabelTextBox(environment, mode, 30), emailTemplateName,
										emailTemplateName, action.SCROLLANDBOOLEAN)) {
									appLog.info("Entered value on Email Template Text Box : "+emailTemplateName);
									
									if (click(driver, getAvailableForUseCheckBox(environment, mode, 10), "Next Button",
											action.SCROLLANDBOOLEAN)) {
										appLog.info("Clicked on Available Use CheckBox");
												
												if (sendKeys(driver, getDescriptionTextArea(environment, mode, 30), emailBody,
														"Body Text Area ", action.SCROLLANDBOOLEAN)) {
													appLog.info("Entered value on Body Text Area : "+emailBody);
													
													
													if (click(driver, getSaveButtonforFolderTempalte(environment, mode, 60), "Save Button",
															action.SCROLLANDBOOLEAN)) {
														appLog.error("Clicked on Save Button");
														
														if (click(driver, getSaveButtonforFolderTempalte(environment, mode, 60), "Save Button",
																action.SCROLLANDBOOLEAN)) {
															appLog.error("Clicked on Save Button");
														
														ele = FindElement(driver,
																"//h2[contains(text(),'" + emailTemplateName + "')]", "Header",
																action.SCROLLANDBOOLEAN, 30);
														if (ele != null) {
															appLog.info("Template created and Matched :  "+emailTemplateName);
															flag = true;
														} else {
															appLog.error("Template created but not Matched :  "+emailTemplateName);
														}
														} else {
															appLog.error("Not Able to Clicked on Save Button");
														}
													} else {
														appLog.error("Not Able to Clicked on Save Button");
													}

												} else {
													appLog.error("Not Able to Enter value on Description Text Area");
												}
									} else {
										appLog.error("Not Able to Click on CheckBox");
									}
								} else {
									appLog.error("Not Able to Enter value on Email Template Text Box");
								}
										} else {
											appLog.error("Not Able to Click on New Letterhead Button");
										}
							} else {
								appLog.error("Not Able to Click on next Button");
							}
								} else {
									appLog.error("Not Able to Click on Go to Letterhead Button");
								}
							} else {
								appLog.error("Not Able to Click on next Button");
							}
						} else {
							appLog.error("Not Able to Click on Radio Button for : " + templateType);
						}
					} else {
						appLog.error("Not Able to Click on Create New Template Button");
					}
				} else {
					appLog.error("Not Able to Select from drop down : " + emailfolderName);
				}

				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToDefaultContent(driver);
				}
			}
			}
			return flag;

		}
	 
	 public boolean createCustomEmailTemplateCustom(String environment, String mode, String emailfolderName,
				EmailTemplateType templateType, String emailTemplateName, String description, String emailSubject, String emailBody) {
			boolean flag = false;
			if(searchStandardOrCustomObject(environment, mode, object.CommunicationTemplates)) {
			if (clickOnObjectFeature(environment, mode, object.CommunicationTemplates, ObjectFeatureName.myTemplates)) {
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
				}
				if (selectVisibleTextFromDropDown(driver, getViewDropdown(60), "View dropdown : " + emailfolderName,
						emailfolderName)) {
					appLog.info("Selected Email folder : " + emailfolderName);
					
					if (click(driver, getCreateNewTempalteButton(environment, mode, 30), "Create New Template Button",
							action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked on New Template Button");
						
						WebElement ele = FindElement(driver, "//label[contains(text(),'" + templateType + "')]/../input",
								templateType.toString(), action.SCROLLANDBOOLEAN, 20);
						if (click(driver, ele, templateType + " Radio Button", action.SCROLLANDBOOLEAN)) {
							appLog.info("Clicked on Radio Button for : "+templateType);
							
							if (click(driver, getNextButton(environment, mode, 10), "Next Button",
									action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on Next Button ");
								
								if (sendKeys(driver, getEmailTemplateNameTextBox(environment, mode, 30), emailTemplateName,
										emailTemplateName, action.SCROLLANDBOOLEAN)) {
									appLog.info("Entered value on Email Template Text Box : "+emailTemplateName);
									
									if (click(driver, getAvailableForUseCheckBox(environment, mode, 10), "Next Button",
											action.SCROLLANDBOOLEAN)) {
										appLog.info("Clicked on Available Use CheckBox");
										
										if (click(driver, getNextButton(environment, mode, 10), "Next Button",
												action.SCROLLANDBOOLEAN)) {
											appLog.info("Clicked on Next Button ");
											
											if (sendKeys(driver, getSubjectTextBox(environment, mode, 30), emailSubject,
													"Subject ", action.SCROLLANDBOOLEAN)) {
												appLog.info("Entered value on Subject Text Box : "+emailSubject);
												
												if (sendKeys(driver, getHTMLBodyTextArea(environment, mode, 30), emailBody,
														"Body Text Area ", action.SCROLLANDBOOLEAN)) {
													appLog.info("Entered value on Body Text Area : "+emailBody);
													
													if (click(driver, getNextButton(environment, mode, 10), "Next Button",
															action.SCROLLANDBOOLEAN)) {
														appLog.info("Clicked on Next Button ");
													
													if (click(driver, getSaveButtonforFolderTempalte(environment, mode, 60), "Save Button",
															action.SCROLLANDBOOLEAN)) {
														appLog.error("Clicked on Save Button");
														
														ele = FindElement(driver,
																"//h2[contains(text(),'" + emailTemplateName + "')]", "Header",
																action.SCROLLANDBOOLEAN, 30);
														if (ele != null) {
															appLog.info("Template created and Matched :  "+emailTemplateName);
															flag = true;
														} else {
															appLog.error("Template created but not Matched :  "+emailTemplateName);
														}
														
													} else {
														appLog.error("Not Able to Clicked on Save Button");
													}
													} else {
														appLog.error("Not Able to Click on next Button");
													}

												} else {
													appLog.error("Not Able to Enter value on Body Text Area");
												}

											} else {
												appLog.error("Not Able to Enter value on Subject Text Box");
											}

										} else {
											appLog.error("Not Able to Click on next Button");
										}
									} else {
										appLog.error("Not Able to Click on CheckBox");
									}
								} else {
									appLog.error("Not Able to Enter value on Email Template Text Box");
								}
							} else {
								appLog.error("Not Able to Click on next Button");
							}
						} else {
							appLog.error("Not Able to Click on Radio Button for : " + templateType);
						}
					} else {
						appLog.error("Not Able to Click on Create New Template Button");
					}
				} else {
					appLog.error("Not Able to Select from drop down : " + emailfolderName);
				}

				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToDefaultContent(driver);
				}
			}
			}
			return flag;

		}
	 
	 public boolean createCustomEmailTemplateVisualforce(String environment, String mode, String emailfolderName,
				EmailTemplateType templateType, String emailTemplateName, String description, String emailSubject, String recipienttype) {
			boolean flag = false;
			if(searchStandardOrCustomObject(environment, mode, object.CommunicationTemplates)) {
			if (clickOnObjectFeature(environment, mode, object.CommunicationTemplates, ObjectFeatureName.myTemplates)) {
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
				}
				if (selectVisibleTextFromDropDown(driver, getViewDropdown(60), "View dropdown : " + emailfolderName,
						emailfolderName)) {
					appLog.info("Selected Email folder : " + emailfolderName);
					
					if (click(driver, getCreateNewTempalteButton(environment, mode, 30), "Create New Template Button",
							action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked on New Template Button");
						
						WebElement ele = FindElement(driver, "//label[contains(text(),'" + templateType + "')]/../input",
								templateType.toString(), action.SCROLLANDBOOLEAN, 20);
						if (click(driver, ele, templateType + " Radio Button", action.SCROLLANDBOOLEAN)) {
							appLog.info("Clicked on Radio Button for : "+templateType);
							
							if (click(driver, getNextButton(environment, mode, 10), "Next Button",
									action.SCROLLANDBOOLEAN)) {
								appLog.info("Clicked on Next Button ");
								
								if (sendKeys(driver, getEmailTemplateNameTextBox(environment, mode, 30), emailTemplateName,
										emailTemplateName, action.SCROLLANDBOOLEAN)) {
									appLog.info("Entered value on Email Template Text Box : "+emailTemplateName);
									
									if (click(driver, getAvailableForUseCheckBox(environment, mode, 10), "Next Button",
											action.SCROLLANDBOOLEAN)) {
										appLog.info("Clicked on Available Use CheckBox");
										
										if (sendKeys(driver, getDescriptionTextBox(environment, mode, 30), description,
												"Description ", action.SCROLLANDBOOLEAN)) {
											appLog.info("Entered value on description Text Box : "+description);
											
											if (sendKeys(driver, getemailSubjectTextBox(environment, mode, 30), emailSubject,
													"Subject ", action.SCROLLANDBOOLEAN)) {
												appLog.info("Entered value on Subject Text Box : "+emailSubject);
												
												if (selectVisibleTextFromDropDown(driver, getrecipienttypeDropdown(60), "View dropdown : " + recipienttype,
														recipienttype)) {
													appLog.info("Selected Email folder : " + recipienttype);
													
													if (click(driver, getSaveButtonforFolderTempalte(environment, mode, 60), "Save Button",
															action.SCROLLANDBOOLEAN)) {
														appLog.error("Clicked on Save Button");
														
														ele = FindElement(driver,
																"//h2[contains(text(),'" + emailTemplateName + "')]", "Header",
																action.SCROLLANDBOOLEAN, 30);
														if (ele != null) {
															appLog.info("Template created and Matched :  "+emailTemplateName);
															flag = true;
														} else {
															appLog.error("Template created but not Matched :  "+emailTemplateName);
														}
														
													} else {
														appLog.error("Not Able to Clicked on Save Button");
													}

												} else {
													appLog.error("Not Able to Select from drop down : " + recipienttype);
												}

											} else {
												appLog.error("Not Able to Enter value on Subject Text Box");
											}

										} else {
											appLog.error("Not Able to Enter value on Description Text Box");
										}
									} else {
										appLog.error("Not Able to Click on CheckBox");
									}
								} else {
									appLog.error("Not Able to Enter value on Email Template Text Box");
								}
							} else {
								appLog.error("Not Able to Click on next Button");
							}
						} else {
							appLog.error("Not Able to Click on Radio Button for : " + templateType);
						}
					} else {
						appLog.error("Not Able to Click on Create New Template Button");
					}
				} else {
					appLog.error("Not Able to Select from drop down : " + emailfolderName);
				}

				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToDefaultContent(driver);
				}
			}
			}
			return flag;

		}
	 
	 public boolean EditCustomEmailTemplate(String environment, String mode, String emailfolderName,
				String emailTemplateName) {
			boolean flag = false;
			if(searchStandardOrCustomObject(environment, mode, object.CommunicationTemplates)) {
			if (clickOnObjectFeature(environment, mode, object.CommunicationTemplates, ObjectFeatureName.myTemplates)) {
				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToFrame(driver, 20, getEditPageLayoutFrame_Lighting(20));
				}
				if (selectVisibleTextFromDropDown(driver, getViewDropdown(60), "View dropdown : " + emailfolderName,
						emailfolderName)) {
					appLog.info("Selected Email folder : " + emailfolderName);
					
					if (click(driver, EmailTemplateEditButton(emailTemplateName, 30), "Edit Template Button",
							action.SCROLLANDBOOLEAN)) {
						appLog.info("Clicked on Edit Template Button");
									
									if (click(driver, getAvailableForUseCheckBox(environment, mode, 10), "Available For Use CheckBox",
											action.SCROLLANDBOOLEAN)) {
										appLog.info("Clicked on Available Use CheckBox");
													
													if (click(driver, getSaveButtonforFolderTempalte(environment, mode, 60), "Save Button",
															action.SCROLLANDBOOLEAN)) {
														appLog.error("Clicked on Save Button");
														flag = true;	
													} else {
														appLog.error("Not Able to Clicked on Save Button");
													}
													
									} else {
										appLog.error("Not Able to Click on CheckBox");
									}
					} else {
						appLog.error("Not Able to Click on Edit Email  Template Button");
					}
				} else {
					appLog.error("Not Able to Select from drop down : " + emailfolderName);
				}

				if (mode.equalsIgnoreCase(Mode.Lightning.toString())) {
					switchToDefaultContent(driver);
				}
			}
			}
			return flag;

		}
}
