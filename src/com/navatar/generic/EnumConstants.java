package com.navatar.generic;

import com.navatar.generic.EnumConstants.Mode;

public class EnumConstants {

	/**
	 * 
	 * @author Ankur Rana
	 * @description static enums
	 */
	public static enum action {
		ACCEPT, DECLINE, GETTEXT, THROWEXCEPTION, BOOLEAN, SCROLL, SCROLLANDTHROWEXCEPTION, SCROLLANDBOOLEAN;
	}

	public static enum excelLabel {
		Variable_Name,Institutions_Name,Contact_FirstName,Contact_LastName,Contact_EmailId, Contact_password_updated,Registered,Fund_Name,FundRaising_Name,
		Limited_Partner,PartnerShip_Name,Commitment_ID,User_First_Name,User_Last_Name,User_Email,User_Profile,User_License,Fund_Type,Firm_Name,UploadedFileInternal,UploadedFileShared,UploadedFileStandard,UploadedFileCommon,UpdatedFileCommon,UpdatedFileInternal,UpdatedFileShared,UpdatedFileStandard,TestCases_Name ,Alert_Count,AllDocument_Count,
		Folder_Template_Name,Fund_Contact,Fund_Phone,Fund_Email,Firm_Description,ContactNew_fName, ContactNew_lName, Contact_Title, Contact_Phone, Facebook, Mailing_Street, Linkedin, Institution_Type_MyProfile, Industry_Dropdown_myprofile, FundType_myprofile, IndustrySelectBox_myprofile,Investment_Category, GeoFocus_myprofile, Firm_Description_Firmprofile, Billing_street_Firmprofile,
		Billing_city_firmprofile, Billing_state_firmprofile, Billing_country_firmprofile, Industries_selectbox, FundType_selectbox, Geofocus_selectbox, Industry_dropdown, InstType_dropdown, Min_investment, Max_investment, AUM,Contact_updated_firmname,
		Folder_Description,Fund_Size,Fund_VintageYear,Fund_Description,Disclaimer_Name,Disclaimer_Description,StandardPath,CommonPath,SharedPath,InternalPath,MyProfile_FName,MyProfile_LName,Updated_FirmName,Updated_FirstName,Updated_LastName,Title,Business_Phone,Mailing_City,Mailing_State,Mailing_Zip,Mailing_Country,Firm_Contact,KeyWord_For_Search,AllFirms_Count,OnlineImportPath,AdvisorInvolvementID,FOLDER_NAME,INVALID_FOLDER_NAME,
		TargetRegistrationURL,TargetLoginURL,Watermarking,UpdateInstitution_NameFormManageInvestor,UpdatedLimitedPartner_NameFormManageInvestor,HomePageAlertCount,FundsPageALertCount,ContactPageALertCount,BillingZip,UpdateFund_NameFromUpdateInfoIWR,UpdateFund_NameFromUpdateInfoFR,ContactName,FundName,Module_Name,Execute,Statistics,FRW_Value,INV_Value,FRW_DocumentsName,
		INV_DocumentsName,Updated_InstitutionName_From_InvestorSide,Activity_Count,Viewed_Or_DownloadedAnyFile,Account_Name,Logo_Name,Contact_Access,ContactUpdatedEmailID, Path, DrawdownID, CapitalCalllID, CapitalAmount,ManagementFee, OtherFee, CallAmount, CallDate, DueDate, CallAmountReceived,ReceivedDate, AmountDue,CapitalReturn,Dividends,RealizedGain,OtherProceeds,TotalDistributions,FundDistributionID,InvestorDistributionID,Capital_Returned_Recallable,
		Capital_Returned_NonRecallable,Start_Time,End_Time,Test_Custom_Object_Name,Average_Deal_Quality_Score,Total_Deals_Shown,Deal_Quality_Score,Meeting_Type,Item_ID {
				@Override
			    public String toString() {
			      return "Item Id";
			    }
		}, Item_Name{
			@Override
			public String toString(){
				return "Item Name";
			}
		},Record_Type,Street,City,State,Postal_Code,Country,Other_Street,Other_City,Other_State,Other_Zip,Other_Country,Report_Folder_Name,Report_Name,Select_Report_Type,Show,Range,Email_Template_Folder_Label,Public_Folder_Access,Type,Available_For_Use,Description,Subject,Email_Body,Email_Template_Name,Marketing_InitiativeName,Target_Commitments,Vintage_Year,Fax,
		Frist_Closing_Date{
			@Override
			public String toString() {
				return "1st Closing Date";
			}
		}
		, Pipeline_Name, Company_Name, Stage, Source, Source_Firm, Source_Contact, Deal_Type, Employees, Website, Email, Legal_Name, Name, Investment_Size, Log_In_Date, Our_Role, Last_Name, Last_Stage_Change_Date, Highest_Stage_Reached, Age_of_Current_Stage, Date_Stage_Changed, Changed_Stage, Age, First_Stage_Changed, Second_Stage_Changed, Office_Location_Name, State_Province, ZIP, Organization_Name, Primary, Updated_Primary, Start, Related_To, Due_Date,Investment_Likely_Amount,Total_Fundraising_Contacts,Fundraising_Contact_ID,Fundraising,Role, Other_Address,Label,
		Mailing_Address,Total_Commitments,Commitment_Amount,Partner_Type,Tax_Forms,Final_Commitment_Date,Company,Bank_Name,Placement_Fee,Fund_Investment_Category,Total_CoInvestment_Commitments,Total_Fund_Commitments, Institution_Type, Fund_Preferences, Industry_Preferences, Shipping_Street, Shipping_City, Shipping_State, Shipping_Zip, Shipping_Country, Mobile_Phone, Assistant, Asst_Phone, Phone,Total_Call_Amount_Received, Total_Amount_Called,Total_Amount_Received,Total_Uncalled_Amount,Total_Commitment_Due,Commitment_Called,Called_Due,Preferred_Mode_of_Contact,Percent, 
		Parent_Field_Name,Override_Label,APIName,FieldOrder,URL,Region,Industry,Attendee_Staff,Marketing_Event,Notes,DTID,Member,File,
		TotalCommitment, Priority, Project_Type, Project_Name, Task_Name, Status, Fund_InvestmentCategory,Comment, Updated_Priority, Updated_Subject, Updated_Comment,Updated_Stage, Start_Date, End_Date, Data_Type, Field_Label, Length, Decimal_Places, Options, Deal_Name, SDG_Name, sObjectName, SDG_Tag, Object_Name, Field_Set_Label, NameSpace_PreFix, Fields_Name, Toggle_Button, Request, Date_Requested, Request_Tracker_ID, Marketing_Event_Name, Date, TabName, Item, RelatedTab, ToggleButton, Column_Name1, Column_Name2,Field_Type, FieldLabel_SubString, ExtraFieldsName, Column_Name, Navigation_Label_Name, Redirection_Label_Name, Update_Navigation_Label_Name, Updated_Order, Parent, Navigation_Label_With_Parent, List_View_Object, List_View_Name, Action_Object, Coverage_Name,Action_Order,Event,Event_Payload,Action_Type;
};

	public static enum fileDistributor {
		BulkUpload,FileSplitter;
	}

	public static enum FolderType{
		Common,
		Shared,
		Internal,
		Standard,
		Global;
	}
	
	public static enum accessType {
		InternalUserAccess, AdminUserAccess,ExternalUserAccess;
	}

	public static enum userType {
		CRMUser, SuperAdmin;
	}

	public static enum WorkSpaceAction{
		CREATEFOLDERTEMPLATE,
		IMPORTFOLDERTEMPLATE,
		WITHOUTEMPLATE,
		WITHTARGET,
		WITHOUTTARGET,
		ACTIVE,
		INACTIVE,
		UPDATE,
		CHECKERRORMSG,
		UPLOAD;
	}
	
	public static enum OnlineImportFileAddTo{
		SingleInstitute, MultipleInstitute
	}
	
	public static enum ContentGridArrowKeyFunctions{
		Update,
		Delete,
		ManageVersions,
		Open;
	}
	
	public static enum PercentOrValue {
		Percent,Value;
	}
	public static enum SortOrder {
		Assecending,
		Decending;
	}
	
	public static enum multiInstance{
		AllInvestor,ThisInvestorOnly;
	}

	public static enum TabName {
		InstituitonsTab, FundraisingsTab, FundsTab, NIMTab, CommitmentsTab, PartnershipsTab, 
		NavatarInvestorAddOns, CurrentInvesment, PotentialInvesment, RecentActivities, AllDocuments, HomeTab, 
		FolderTemplate, FundDistributions, InvestorDistributions, MarketingInitiatives, MarketingProspects, 
		NavatarSetup, Pipelines, FundDrawdowns, CapitalCalls, FundraisingContacts, LimitedPartne, ReportsTab, LimitedPartner,CompaniesTab, TaskRayTab,TaskRay,TestCustomObjectTab, TaskTab,
		Other,Object1Tab,Object2Tab,Object3Tab,Object4Tab,RecycleBinTab,SDGTab,DealTab, Object5Tab,Object6Tab,AttendeeTab,Object7Tab,Entities,Deals,Marketing_Events,Deal_Team, Object8Tab, ContactTab;
	}
	
	public static enum Mode{
		Lightning,Classic;
	}
	public static enum Environment{
		Sandbox,Testing,Dev;
	}
	
	public static enum Workspace{

		FundraisingWorkspace,InvestorWorkspace,CurrentInvestment,Other,PotentialInvestment;

	}
	
	public static enum customTabActionType{
		Add,Remove;
	}
	public static enum investorSideWorkSpace{
		CurrentInvestment,PotentialInvestment;
	}
	
	
	public static enum PageName{
		FundsPage,InstitutionsPage,CommitmentsPage,HomePage,NavatarInvestorManager,ManageFolderPopUp,ManageApprovalsPopUp,ProjectDetailsPoPUp,NavatarInvestorAddOnsPage,NewProjectPopUp,PipelineCustomPage
		,CompanyPage,CreateFundraisingPage,CreateCommitmentFundType,CreateCommitmentCoInvestmentType,FundraisingPage,PartnershipsPage, DealPage,TaskRayPage, LimitedPartnerPage, BuildStep2Of3,PipelinesPage,NewTaskPage,
	CRMUserPage,   TaskPage, NewTaskPopUP,TestCustomObjectPage, NewEventPopUp,ActivitiesOrMeetings,SearchActivitiesAttachment,EmailUploadPage,Object1Page,Object1PagePopup,Object2Page,Object3Page,Object4Page,ListEmail,
	CompactLayout, MeetingType, AddPickListMeetingType,ActivityLayoutPage, SDGPage,Object5Page,AccountCustomFieldStatusPage,MEPageFromCalender,SharingSettingsPage,DashboardDeadDeals,AccountReferral,VisualForcePage, WarningPopUp;



	}
	
	public static enum IconType{
		updatePhoto,DeletePhoto;
	}
	
	public static enum NavatarSetupSideMenuTab{
		DealCreation, IndividualInvestorCreation, CommitmentCreation, ContactTransfer, AccountAssociations, OfficeLocations, CoInvestmentSettings, PipelineStageLog, BulkEmail
		}
	
	
	public static enum UploadFileActions{
		BulkUploaderOrFileSplitter,Upload,Update;
	}
	
	public static enum DisclaimerGrid{
		Activate,Deactivate,View,LastActivatedOn,CreatedOn;
	}
	
	public static enum Stage {
		NDA_Signed,Deal_Received,Management_Meeting,IOI,IndicationOfInterest{
			@Override
			public String toString() {
				return "Indication of Interest";
		}
		},LOI,Due_Diligence,Parked,Closed,NonDisclosureAgreement{
			@Override
			public String toString() {
				return "Non-Disclosure Agreement";
		}
		}
		,DeclinedDead{
			@Override
			public String toString() {
				return "Declined/Dead";
		}
		};
	}
	public static enum YesNo {
		Yes,No,YesWinium;
	}
	public static enum EnableDisable {
		Enable,Disable;
	}
	public static enum ErrorMessageType{
		BlankErrorMsg,PrefixErrorMsg,SpiecalCharErrorMsg,FolderCreationRestrictionErrorMsg,DuplicateFolder;
	}
	public static enum EditViewMode {
		Edit,View;
	}
	public static enum ManageApprovalTabs {
		PendingDocuments, ApprovedDocuments;
	}
	
	public static enum Fields {
		Name,Title,Email,Phone,Referrals,Task,Role;
	}
	public static enum ExpandCollapse{
		Expand,Collapse
	}
	
	public static enum CheckUncheck{
		Check, UnCheck;
	}
	public static enum UpdateIgnore {
		Update, Ignore;
	}
	public static enum Status {
		Pending, Approved, Both, Completed,Not_Started{
			@Override
			public String toString() {
				return "Not Started";
		}
		};
	}
	
	public static enum Watchlist{
		True,False;
	}
	public static enum profileUpdatedAlert{
		FirmProfileUpdated,ContactProfileUpdated;
		
	}
	
	public static enum columnName{
		fundName,contactName,institutionName,AccountName,Fundraising_Name;
	}
	
	public static enum viewDownload{
		view,Download;
	}
	
	public static enum SelectDeselect{
		Select,Deselect
	};
	
	public static enum AllOr1By1{
		All,OneByOne
	};
	
	public static enum LeftRight{
		Left,Right
	};
	
	
	
	
	public static enum object{
		Account{
			@Override
			public String toString() {
				//				if(ExcelUtils.readDataFromPropertyFile("Mode").equalsIgnoreCase(Mode.Lighting.toString())) {
				//					return "Institution";
				//				} else {
				return "Accounts";
				//	}
			}
		},Contact,Deal,Fund,Fundraising,Fundraising_Contact{
			@Override
			public String toString() {
				return "Fundraising Contact";
			}
		},Email,InstalledPackage{
			@Override
			public String toString() {
				return "Installed Packages";
			}
		},Task,Global_Actions{
			@Override
			public String toString() {
				return "Global Actions";
			}
		},Activity_Setting{
			@Override
			public String toString() {
				return "Activity Setting";
			}
		}, Activity,Entity,Marketing_Event{
			@Override
			public String toString() {
				return "Marketing Event";
			}
		}, Apps,Pipeline,
		App_Manager{
			@Override
			public String toString() {
				return "App Manager";
			}
		},Lightning_App_Builder{
			@Override
			public String toString() {
				return "Lightning App Builder";
			}
		}, Profiles, Custom_Object, Tabs, Create, Flows, Users,Sharing_Settings,Institution
		,CommunicationTemplates{
			@Override
			public String toString() {
				return "Communication Templates";
		}
	},Test_Custom_Object{
		@Override
		public String toString() {
			return "Test Custom Object";
		}
	}
	};

	
	public static enum ObjectFeatureName{
		
		pageLayouts{
			@Override
			public String toString() {
					return "Page Layouts";
		}
	},myTemplates{
			@Override
			public String toString() {
				if(ExcelUtils.readDataFromPropertyFile("Mode").equalsIgnoreCase(Mode.Classic.toString())){
					return "My Templates";
				}else {
					return "My Templates";
				}
		}
	},compactLayouts{
		@Override
		public String toString() {
				return "Compact Layouts";
	}
		
		
},FieldAndRelationShip{
	@Override
	public String toString() {
			return "Fields & Relationships";
}		
},FieldSets{
	@Override
	public String toString() {
		return "Field Sets";
	}
},recordTypes{
	@Override
	public String toString() {
		return "Record Types";
	}
}
};

public static enum PermissionType{
	removePermission,givePermission;
}

//*************************************************************** Pages Field Labels*********************************************//
	public static enum InstitutionPageFieldLabelText {
		Street,Referral_Source_Description,Legal_Name,Description,
		Shipping_State{
			@Override
			public String toString() {
				return "Shipping State/Province";
			};
		},Shipping_Zip{
			@Override
			public String toString() {
				return "Shipping Zip/Postal Code";
			};
		},Parent_Institution,Parent_Entity;
		
	}
	
	public static enum LimitedPartnerPageFieldLabelText {
		Street,Referral_Source_Description,Legal_Name,Description,
		Total_CoInvestment_Commitments{
			@Override
			public String toString() {
				return "Total Co-investment Commitments (mn)";
			};
		},
		Total_Fund_Commitments{
			@Override
			public String toString() {
				return "Total Fund Commitments (mn)";
			};
		}
		
	}
	
	public static enum ContactPageFieldLabelText {
		Legal_Name,Description,Mailing_Street,Other_Street,Candidate_Notes,First_Name,Last_Name,Contact_Referral_Source,Mobile,
		Mailing_State{
			@Override
			public String toString() {
				return "Mailing State/Province";
			};
		},Mailing_Zip{
			@Override
			public String toString() {
				return "Mailing Zip/Postal Code";
			};
		},Other_State{
			@Override
			public String toString() {
				return "Other State/Province";
			};
		},Other_Zip{
			@Override
			public String toString() {
				return "Other Zip/Postal Code";
			};
		}
		
	}
	
	public static enum CapitalCallPageFieldLabelText {
		CC_No,Fund_Drawdown,Commitment,Capital_Amount,Management_Fee,Other_Fee,Call_Amount,Call_Date,Due_Date,Call_Amount_Received,Received_Date,Amount_Due,Commitment_Called,Called_Due,Total_Commitment_Due
	}

	public static enum FundDrawdownPageFieldLabelText {
		DD_No,Fund_Name,Call_Amount,Call_Date,Due_Date,Amount_Due,Total_Call_Amount_Received,Capital_Amount,Management_Fee,Other_Fee
	}
	
	public static enum InvestorDistributionPageFieldLabelText {
		ID_No,Fund_Distribution, Commitment, Capital_Return, Dividends, Realized_Gain, Other_Proceeds, Total_Distributions, Capital_Recallable, Distribution_Date;
	}
	public static enum CommitmentPageFieldLabelText {
		Commitment_ID,Partner_Type,Limited_Partner,Final_Commitment_Date,Tax_Forms,Commitment_Amount,Total_Amount_Called,Total_Amount_Received,
		Total_Uncalled_Amount,Total_Commitment_Due,Partnership,Commitment_Called{
			@Override
			public String toString() {
				return "% Commitment Called";
			}
		},Called_Due{
			@Override
			public String toString() {
				return "% Called Due";
			}
		}
		,Total_Distributions{
			@Override
			public String toString() {
				return "Total Distributions";
			}
		}
		,Capital_Returned_Recallable{
			@Override
			public String toString() {
				return "Capital Returned (Recallable)";
			}
		}
		,Capital_Returned_NonRecallable{
			@Override
			public String toString() {
				return "Capital Returned (Non-Recallable)";
			}
		};
	}
	public static enum FundPageFieldLabelText{
		Fund_Name,Fund_Type,Investment_Category,Vintage_Year,
		Frist_Closing_Date{
			@Override
			public String toString() {
					return "1st Closing Date";
		}
		},Second_Closing_Date{
			@Override
			public String toString() {
					return "2nd_Closing_Date";
		}
		}
		,Third_Closing_Date{
			@Override
			public String toString() {
					return "3rd_Closing_Date";
		}
		},Fourth_Closing_Date{
			@Override
			public String toString() {
					return "4th_Closing_Date";
		}
		},Fivth_Closing_Date{
			@Override
			public String toString() {
					return "5th_Closing_Date";
		}
		},Sixth_Closing_Date{
			@Override
			public String toString() {
					return "6th_Closing_Date";
		}
		},Final_Closing_Date,Termination_Date,Dissolution_Date,Step_Down_Date,Investment_Period_End_Date,Target_Commitments{
			@Override
			public String toString() {
					return "Target Commitments (mn)";
		}
		};
	}
	public static enum SDGLabels{
		APIName,Override_Label,FieldOrder,Parent_Field_Name,URL,Actions;
	}
	
	public static enum AttendeeLabels {
		Attendee_Staff,Marketing_Event,Status,Notes,Attendee
	}
	public static enum CreateCommitmentPageFieldLabelText{
		Legal_Name,Fundraising_Name, Investment_Likely_Amount{
			@Override
			public String toString() {
					return "Investment Likely Amount (mn)";
		}
		},Fund_Name,Company;
		
	}
	
	
	//*****************************************************************************************************************************************//
	
	public static enum PrimaryContact{
		Yes,No;
	}
	
	public static enum CreationPage{
		AccountPage,ContactPage;
	}
	
	public static enum UserLicense{
		Salesforce_Platform{
			@Override
			public String toString() {
					return "Salesforce Platform";
		}
		}
	}
	
	public static enum UserProfile{
		PE_Standard_User{
			@Override
			public String toString() {
					return "PE Standard User";
		}
		}
		
	}
	
	public static enum EmailTemplateType{
		Text{
			@Override
			public String toString() {
				return "Text";
			}
		},HTML{
			@Override
			public String toString() {
				return "HTML";
			}
		},Custom{
			@Override
			public String toString() {
				return "Custom";
			}
		},Visualforce{
			@Override
			public String toString() {
				return "Visualforce";
			}
		};
	}
	
	public static enum ReportDashboardFolderType{
		ReportFolder{
			@Override
			public String toString() {
				return "New Report Folder";	
			}
		},DashBoardFolder{
			@Override
			public String toString() {
				return "New Dashboard Folder";
			}
		}
	};
	
	public static enum ReportField{
		ContactID{
			@Override
			public String toString() {
				return "Contact ID";
			}
		}
	};
	
	public static enum FolderAccess{
		ReadOnly{
			@Override
			public String toString() {
				return "Read Only";
			}
		},ReadWrite{
			@Override
			public String toString() {
				return "Read/Write";
			}
		}
	};

	public static enum NavatarQuickLink{
		CreateDeal{
			@Override
			public String toString() {
				return "Create New Deal";
			}
		},CreateFundRaising{
			@Override
			public String toString() {
				return "Create Fundraisings";
			}
		},CreateCommitment{
			@Override
			public String toString() {
				return "Create New Commitment";
			}
		},Bulk_Email,BulkEmail{
			@Override
			public String toString() {
				return "Send Bulk Email";
			}
		},CreateIndiviualInvestor{
			@Override
			public String toString() {
				return "Create New Individual Investor";
			}
		}
	};

	public static enum DealCreationPageLayout{
		Deal_Information,New_Source_Firm,New_Source_Contact;
	};
	

	public static enum Existing{
		Yes,No
	};
	
	public static enum newSourceFirmPopUpFieldLabelText{
		Description, Street
	};
	
	public static enum HTMLTAG{
		select,input
	};

	public static enum AddProspectsTab{
		AccountAndContacts,PastMarketingInitiatives,Report;
	}
	
	public static enum NavatarSetupSideMenuTabLayoutSection{
		DealCreation_DealInformation,DealCreation_NewSourceFirm,DealCreation_NewSourceContact,
		CommitmentCreation_FundRaisingInformation,CommitmentCreation_AdditionalInformation,CommitmentCreation_FieldsForNewLimitedPartner
		,CommitmentCreation_FieldsForNewPartnership,CommitmentCreation_GeneralInformation,CommitmentCreation_CommitmentInformation,Contact_Information,Address_Information,Additional_Information;
	}

	public static enum RecordType{
		Company,Institution,IndividualInvestor,Contact, PipeLine, Fund, Fundraising,Partnerships;
	}
	
	public static enum searchContactInEmailProspectGrid{
		Yes,No
	};
	
	public static enum Locator{
		Xpath,Name;
	}

	public static enum CheckBox{
		Checked,Unchecked
	};
	
	public static enum OfficeLocationLabel{
		Office_Location_Name,Street,City,ZIP,Country,Phone,Fax,Primary,State_Province{
			@Override
			public String toString() {
				return "State/Province";
			}
	}, State
	};
	
	public static enum PrimaryOfficeLocation{
	Yes,No
	};
	
	public static enum LookUpIcon{
		OfficeLocation{
			@Override
			public String toString() {
				return "Office Location Lookup (New Window)";
			}
		},
		selectFundFromCreateFundraising{
			@Override
			public String toString() {
				return "Fund Name Lookup (New Window)";
			}
		},newTaskProject{
			@Override
			public String toString() {
				return "Project Lookup (New Window)";
			}
		}
		};
		
	public static enum RelatedList {
		Fundraising_Contacts{
			@Override
			public String toString() {
				return "Fundraising Contacts";
			}
		},Office_Locations, Open_Activities,Affiliations,Contacts, Activities,Activity_History,Commitments,Partnerships,Fundraisings,FundDistribution {
			@Override
			public String toString() {
				return "Fund Distribution";
				
			}
			}, InvestorDistributions{
				@Override
				public String toString() {
					return "Investor Distributions";
				}
			}, FundDrawdown {
		@Override
		public String toString() {
			return "Fund Drawdown";
			
		}
		}, CapitalCalls{
			@Override
			public String toString() {
				return "Capital Calls";
			}
		},
			Deals_Sourced{
			@Override
			public String toString() {
				return "Deals Sourced";
			}
		},
			Pipeline_Stage_Logs{
			@Override
			public String toString() {
				return "Pipeline Stage Logs";
			}
		},Correspondence_Lists{
			@Override
			public String toString() {
				return "Correspondence Lists";
			}
		}
	};

	public static enum ActivityRelatedButton {
		Task,Event,Call,Email;
	};
	
	public static enum ActivityRelatedLabel {
		Assigned_To {
			@Override
			public String toString() {
				return "Assigned To";
			}
		},
		Status, Subject, Name, Start_Date {
			@Override
			public String toString() {
				return "Start Date";
			}
		},
		Related_To {
			@Override
			public String toString() {
				return "Related To";
			}
		},
		Due_Date {
			@Override
			public String toString() {
				return "Due Date";
			}
		},
		Priority, Comments, Start, End, Description;
	};

		public static enum fundraisingContactActions{
			Role_None{
				@Override
				public String toString() {
					return "--None--";
				}
			},
			Role_DecisionMaker{
				@Override
				public String toString() {
					return "Decision Maker";
				}
			},Role_Evaluator{
				@Override
				public String toString() {
					return "Evaluator";
				}
			},Role_ExecutiveSponsor{
				@Override
				public String toString() {
					return "Executive Sponsor";
				}
			},Role_Gatekeeper{
				@Override
				public String toString() {
					return "Gatekeeper";
				}
			},Role_Influencer{
				@Override
				public String toString() {
					return "Influencer";
				}
			},Role_Other{
				@Override
				public String toString() {
					return "Other";
				}
			},Role_BusinessUser{
				@Override
				public String toString() {
					return "Business User";
				}
			},Remove,PrimaryContact,AddNewContactInFundraisingContact;
			
		};
		
		public static enum DataLoader {
			Institutions,Contacts,Funds,Fundraisings,Commitments,Partnerships,Advisor,CorrespondanceList,Pipeline,MarketingInitiative,MarketingProspect,FundraisingContact;
		}

	public static enum FundraisingContactPageTab{
			SearchBasedOnExistingFunds,SearchBasedOnAccountsAndContacts;
		}
		
		public static enum PopUpName{
			selectFundPopUp, WarningPopUp,SelectFundPopUpFromCompmayPage;
		}
		
		public static enum GridSectionName{
			
		}
		public static enum AddressAction {
			Clear,Retain, CrossIcon;
		};
		
		public static enum SearchBasedOnExistingFundsOptions{
			OnlyFundraisingContacts{
				@Override
				public String toString() {
					return "Only Fundraising Contacts";
				}
			},AllContacts{
				@Override
				public String toString() {
					return "All Contacts";
				}
			}
		}
	public static enum CommitmentType{
		fund,coInvestment,fundraisingName;
	}
	
	public static enum CreatedOrNot{
		AlreadyCreated,NotCreated;
	}
	
	public static enum RequiredFieldListForSection{
		Fundraising_Information,Additional_Information,Fields_for_New_Limited_Partner,Fields_for_New_Partnership
	};
	
	public static enum RevertToDefaultPopUpButton{
		YesButton,NoButton,CrossIcon
		};
	
	public static enum ShowMoreActionDropDownList{
		 New_Task,Edit, Delete, New_Meeting,LogCaLLWithMultiple{
				@Override
				public String toString() {
					return "Log a Call with Multiple Associations";
				}
			},NewTaskWithMultiple{
				@Override
				public String toString() {
					return "New Task with Multiple Associations";
				}
			}, Contact_Transfer,Change_Date,Change_Priority,Change_Status,Edit_Comments,New_Attendee,New
			};
		
	public static enum IndiviualInvestorSectionsName{
		Contact_Information,Address_Information,Additional_Information;
				};
	
	public static enum IndiviualInvestorFieldLabel{
		First_Name,Last_Name,Contact_Description,Business_Phone,Business_Fax,Mobile_Phone,Email,Mailing_Street,Mailing_City,
		Mailing_State{
			@Override
			public String toString() {
				return "Mailing State/Province";
			}
		},Mailing_Zip{
			@Override
			public String toString() {
				return "Mailing Zip/Postal Code";
			}
		}, Other_State{
			@Override
			public String toString() {
				return "Other State/Province";
			}
		},Other_Zip{
			@Override
			public String toString() {
				return "Other Zip/Postal Code";
			}
		},Assistant{
		@Override
		public String toString() {
			return "Assistant's Name";
		}
	},Asst_Phone{
		@Override
		public String toString() {
			return "Asst. Phone";
		}
	},Mailing_Country,Other_Street,Other_City,Other_Country,Fund_Preferences,Industry_Preferences,Website,Preferred_Mode_of_Contact
};	

	public static enum NotApplicable{
		NA;
	}
	
	public static enum ClickOrCheckEnableDisableCheckBox{
		Click,EnableOrDisable;
	}
	
	public static enum sideListOnLayout{
		Related_Lists{
			@Override
			public String toString() {
				return "Related Lists";
			}
		},other{
			@Override
			public String toString() {
				return null;
			}
		}
	}
	
	public static enum ViewFieldsName{
		All_Fields,Contact_Fields{
			@Override
			public String toString() {
				return "Contact Fields";
			}
		},Account_Fields{
			@Override
			public String toString() {
				return "Account Fields";
			}
		}, Marketing_Prospect_Fields{
			@Override
			public String toString() {
				return "Marketing Prospect Fields";
			}
		},Fundraising_Contact_Fields{
			@Override
			public String toString() {
				return "Fundraising Contact Fields";
			}
		},Fundraising_Fields{
			@Override
			public String toString() {
				return "Fundraising Fields";
			}
		},Commitment_Fields{
			@Override
			public String toString() {
				return "Commitment Fields";
			}
		},Investor_Distribution_Fields{
			@Override
			public String toString() {
				return "Investor Distribution Fields";
			}
		}
		;
	}
	

	public static enum TopOrBottom{
	TOP,BOTTOM;	  
	};
	
	public static enum CancelOrCross{
		Cancel,Cross;	  
		};
		
		public static enum TaskRayProjectButtons {
			AddPlus, Search,Other;
		}
		
		public static enum AddPlusIconDropDownList {
			New_Project;
		};
	
	public static enum taskSubTab{
		Details,Chatter,Files,Checklist,Navatar_Documents{
			@Override
			public String toString() {
				return "Navatar Documents";
			}
		}
	}
		
	public static enum Buttons{
		SaveNext,SaveClose,Cancel,Cross,Delete_Photo,close;	  
	};
	
	public static enum ProjectLabel{
		Pipeline, Fund, Project_Name, Project_Type;	  
	};
	
	public static enum RelatedTab{
		Related,Details,Tasks,Meetings,Activities,Documents,Box,Investment,
		QandA{
			@Override
			public String toString() {
				return "Q&A";
			}
		}, Overview,Events;	  
	};
	
	public static enum Header{
		Project,OpenTask{
			@Override
			public String toString() {
				return "Tasks: Open";
			}
		}  ,
		CompletedTask{
			@Override
			public String toString() {
				return "Tasks: Completed";
			}
		},Company,Contact 
	};
	
	public static enum ProjectName{
		PE,MNA,PEEdge;	  
	};
	
	
	public static enum PageLabel{
		First_Name,Last_Name,Email, Fund_Name, Deal_Name,Status, Investment_Type,Meeting_Type{
			@Override
			public String toString() {
				return "Meeting Type";
			}
		}  ,Priority,Under_Evaluation,RenameWatchlist{
			@Override
			public String toString() {
			return "Rename-Watchlist";
		}},RenameUnder_Evaluation{
			@Override
			public String toString() {
			return "Rename-Under Evaluation";


		}},Watchlist, Profile_Image,Industry,Watch_list,Deal_Conversion_Date,Portfolio_Company,Related_Associations,Name, Subject, Due_Date, New_Task, Related_To, Comments, Edit, Assigned_To, Start_Date, End_Date, End_Time, Start_Time, Type, 
		Date{
			@Override
			public String toString() {
				return "Start Date";
			}},
		Contact_Name, Owner, Activity, Related_Contacts, Account_Name, Length, Decimal_Places, Values, Is_Touchpoint,Description, Request, Date_Requested, Attendee_Staff, Label, Panel_Width, Panel_Height,Deal,Team_Member_Role,Member, Page_Layout_Name,Organizer,Convert_to_Portfolio


	};
	
	public static enum Links{
		View;	  
	};
	
	public static enum Activity{
		Call,Task,Meeting, Event,Email,ListEmail;  
	};
	
	public static enum PlusNewButton {
	EntityOrAccountNewButton,ContactNewButton,FundOrDealNewButton,TestCustomObjectNewButton;
	}
	
	public static enum TaskPageFields{
		Date,Name,Contact_Name ,Related_To, Status,Owner,Meeting_Type,Priority  ,Activity,Comments;
	}
	
	public static enum GlobalActionItem{
		New_Event,New_Task,Log_a_Call;
	}
	
	public static enum Priority {
		Normal;
	}
	
	public static enum KeepActivityEnum {
		OldInstitutionOnly,OldAndNewInstitutionBoth;
	}
	
	
	public static enum InculdeActivityEnum {
		ContactOnly,ContactAndInstitution,ContactInstitutionAndCustomObject;
	}
	public static enum ActivityTimeLineItem{
        New_Meeting,New_Call,Log_a_Call_with_Multiple_Associations,New_Task_with_Multiple_Associations, Expand_All,Refresh,Collapse_All, Filter, New_Task;     
    }
	public static enum DueDate{
        No_due_date{
            @Override
            public String toString() {
                return "No due date";
            }
        }, Tomorrow,Today,Yesterday;    
    };
    
    public static enum SubjectElement{
        CheckBox, StrikedText,SubjectLink,RedFlag,ExpandIcon,CollapseIcon,Attachment;     
    };
    
    public static enum ActivityType{
        Next, Past;     
    };
    public static enum ShowMoreAction{
    	Edit,Delete,FollowUpTask{
            @Override
            public String toString() {
                return "Create Follow-Up Task";
            }
        },Show_More;
    };
    
    public static enum DateRange{
    	All_time,Last_7_Days,Next_7_Days,Last_30_Days;
    };

    public static enum ActivitiesToShow{
    	All_Activities,My_Activities;
    };

    public static enum ActivityTypes{
    	All_Types,List_Email,Email,Logged_Calls,Events,Tasks;
    };
    
    public static enum ActivityPopupFields{
    	Company_Name,Active,Institution_Owner,Legal_Name,Account_Name,Primary_Coverage_Officer,Email,Website,Title,Phone,Type, Firm, Entity_Owner;
    };
    
    public static enum TaskTimeLine{
    	Past,Upcoming;
    };
    
    public static enum SDGCreationLabel{
    	SDG_Name,SDG_Tag,sObjectName,Parent_Field_Name,Filter,Image_Field_API;
    };
    
    public static enum ToggleButton{
    	CoInvestments{
            @Override
            public String toString() {
                return "Co-Investments";
            }
        }, Fund_Investments{
            @Override
            public String toString() {
                return "Fund Investments";
            }
        }, Open_Questions{
            @Override
            public String toString() {
                return "Open Questions";
            }
        }, Closed{
            @Override
            public String toString() {
                return "Closed";
            }
        }, Third_Party_Event{
            @Override
            public String toString() {
                return "Third Party Event";
            }
        }, Our_Events{
            @Override
            public String toString() {
                return "Our Events";
            }
        };    
    };
    
    public static enum ToggleButtonGroup{
    	SDGButton{
            @Override
            public String toString() {
                return "Open SDG record.";
            }
        }, ToggleFiltersButton{
            @Override
            public String toString() {
                return "Toggle Filters.";
            }
        }, ReloadButton{
            @Override
            public String toString() {
                return "Reload";
            }
        };    
    };
    

	public static enum SearchItemName{
		Data_Import_Wizard;
	}
	
	public static enum SearchItemcategory{
		Import;
	}
	public static enum ObjectName{
		InstitutionAndContacts {
			@Override
			public String toString() {
				return "Institutions and Contacts";
			}},Funds,Fundraisings,Partnerships, Navigation;
	}
	public static enum ObjectType{
		Standard,Custom;
	}
	
	public static enum DataImportType{
		AddNewRecords {
			@Override
			public String toString() {
				return "Add new records";
			}},
		UpdateExistingRecords {
				@Override
				public String toString() {
					return "Update existing records";
				}},
		AddNewAndUpdateExistingRecords {
					@Override
					public String toString() {
						return "Add new and update existing records";
					}}
	}
	
	public static enum AppSetting{
		Utility_Items {
			@Override
			public String toString() {
				return "Utility Items";
			}},
		Navigation_Items {
				@Override
				public String toString() {
					return "Navigation Items";
				}},
		User_Profiles {
					@Override
					public String toString() {
						return "User Profiles";
					}}
	}
	
	public static enum CSVLabel{
		Navigation_Label ,Order,Parent,Action_Object,Action_Record_Type,List_View_Object,List_View_Name,URL,Activities_Button_API_Name, Navigation_Type;
	}

	public static enum EditPageLabel{
		Title,Query,Image_Field_API_Name,Number_of_Records_to_Display,SDG_Name,Popup_Title,Start_DateTime{
			@Override
			public String toString() {
				return "Start Date/Time";
			}},Title_Highlight_Color,Filter,Onclick_Title,Calendar_Filter_1,Calendar_Filter_2,Calendar_Filter_3;
	}
	public static enum ContactPagePhotoActions{
		Update_Photo,Delete_Photo;
	}

	public static enum recordTypeLabel{
		Record_Type_Label {
			@Override
			public String toString() {
				return "Record Type Label";
			}},
		Record_Type_Name {
				@Override
				public String toString() {
					return "Record Type Name";
				}},
		Description {
					@Override
					public String toString() {
						return "Description";
					}},
		Active {
						@Override
						public String toString() {
							return "Active";
						}}
	}
	
	public static enum customObjectLabel{
		Label,
		Plural_Label {
				@Override
				public String toString() {
					return "Plural Label";
				}}
	}
	
	public static enum AppName {
		Files,PE,Navatar;
	}
	public static enum CalenderButton{
		Month,Day,Year,Week,next,prev;
	}
	public static enum SDGActionsCreationLabel{
		Name,Event,Action_Order,Action_Type,Event_Payload;
    };
    
    
    public static enum NavigationMenuItems{
    	Bulk_Actions {
			@Override
			public String toString() {
				return "Bulk Actions";
			}
    	},New_Interactions{
			@Override
			public String toString() {
				return "New Interactions";
			}
    	},Create_New{
			@Override
			public String toString() {
				return "Create New";
			}
    	};
    };
    
    public static enum BulkActions_DefaultValues{
    	Bulk_Email {
			@Override
			public String toString() {
				return "Bulk Email";
			}
    	},Bulk_Fundraising{
			@Override
			public String toString() {
				return "Bulk Fundraising";
			}
    	},Bulk_Commitments{
			@Override
			public String toString() {
				return "Bulk Commitments";
			}
    	},Deal_Creation {
			@Override
			public String toString() {
				return "Deal Creation";
			}
    	},Individual_Investor_Creation {
			@Override
			public String toString() {
				return "Individual Investor Creation";
			}
    	},;
    };
    
    public static enum NewInteractions_DefaultValues{
    	Call {
			@Override
			public String toString() {
				return "Call";
			}
    	},Meeting{
			@Override
			public String toString() {
				return "Meeting";
			}
    	},Task{
			@Override
			public String toString() {
				return "Task";
			}
    	};
    };
    
    public static enum CreateNew_DefaultValues{
    	New_Deal {
			@Override
			public String toString() {
				return "New Deal";
			}
    	},New_Institution{
			@Override
			public String toString() {
				return "New Institution";
			}
    	},New_Contact{
			@Override
			public String toString() {
				return "New Contact";
			}
    	};
    };
    
    public static enum ReportFormatName{
		Matrix,Joined,Summary,Tabular,Null;
	}
    
    
}
