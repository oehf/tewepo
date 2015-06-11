/*
 * Copyright 2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openehealth.tewepo.twp.dmp.dmc.client;


import gwtupload.client.IUploader;
import gwtupload.client.IUploadStatus.Status;

import org.openehealth.tewepo.twp.dmp.dmc.client.data.BasicDataSet;
import org.openehealth.tewepo.twp.dmp.dmc.client.data.ReferencePortalDataSet;
import org.openehealth.tewepo.twp.dmp.dmc.client.gui.CheckEmailContentPanel;
import org.openehealth.tewepo.twp.dmp.dmc.client.jsni.JSNInterface;
import org.openehealth.tewepo.twp.dmp.dmc.client.rpc.SendDicomMailsService;
import org.openehealth.tewepo.twp.dmp.dmc.client.rpc.SendDicomMailsServiceAsync;
import org.openehealth.tewepo.twp.dmp.dmc.client.utils.GuiValidator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author devmis
 * 
 */
public class GwtGuiDemo implements EntryPoint {
	

	private ListBox listBoxRecipientFacility = new ListBox();
	private ListBox listBoxRecipientRecipient = new ListBox();
	private ListBox listBoxRecipientLastSelected = new ListBox();
	private ListBox listBoxRecipientLocation = new ListBox();

	// Remote call
	private final SendDicomMailsServiceAsync sendDicomMailService = (SendDicomMailsServiceAsync) GWT
			.create(SendDicomMailsService.class);

	private TextBox txtbxVorname = new TextBox();
	private TextBox txtbxNachname = new TextBox();
	
	private TextArea textBox_ClinicalIsue = new TextArea();
	private TextArea textBox_ClinicalIndication = new TextArea();
	private TextArea textBox_Comments = new TextArea();
	
	private TextBox textBox_ElectedRecipient = new TextBox();
	
	//Radio Buttons gender
	private RadioButton rdbtnMale = new RadioButton("sex", "männlich");
	private RadioButton rdbtnFemale = new RadioButton("sex", "weiblich");
	private RadioButton rdbtnUnknown = new RadioButton("sex", "unknown");
	
	//Panel recipient and  textual description
	private CaptionPanel cptnpnlRecipient = new CaptionPanel("Empfänger");
	
	private CaptionPanel cptnpnlDescription = new CaptionPanel("Angaben zum Patienten");
	
	private CheckEmailContentPanel checkPanel = new CheckEmailContentPanel();

	
	//Buttons
	private Button btnButtoncheck = new Button("buttonCheck");
	private Button btnButtonBack = new Button("buttonBack");
	private Button btnButtonSend = new Button("buttonSend");

	
	private TextBox textBox_Birthdate = new TextBox();
	
	
	private Label lblforename = new Label("Vorname:*");

	//Panel files
	private CaptionPanel captionPanel = new CaptionPanel("Dateien");
	
	private AbsolutePanel absolutePanel = new AbsolutePanel();
	private Label label = new Label("Dateien:");
	private ScrollPanel scrollPanel = new ScrollPanel();
	private FlexTable flexTable_Result_Recipients = new FlexTable();

	private Button btnButtonShowUploadApplet = new Button("Auswahl");
	private final Label label_Message = new Label("");
	private RootPanel rootPanel = null;

	private int counterUploads = 0;

	private boolean uploadAppletIsVisible = false;

	private long userId = -1;
	
	/**
	 * This method returns the rootPanel, null if rootPanel is not initialized
	 * 
	 * @return rootPanel
	 */
	public Widget getGuiWidget() {
		this.onModuleLoad();
		return rootPanel;
	}
	
	/**
	 * This is the entry point method. All other components of the page is
	 * initialized and added to the root panel here.
	 */
	public void onModuleLoad() {
		userId = Long.parseLong(JSNInterface.getUserId());
		JSNInterface.exportStaticRefreshFileListMethod();
		uploadAppletIsVisible = false;

		rootPanel = RootPanel.get("form");
		rootPanel.setSize("450", "960");
		rootPanel.add(label_Message, 5, -10);
		
		label_Message.setStyleName("LabelMessage");
		label_Message.setSize("535", "535");
		captionPanel.setStyleName("groupBox");
		captionPanel.setCaptionHTML("Dateien");

		rootPanel.add(captionPanel, 5, 7);// 33
		captionPanel.setSize("535px", "150px");
		absolutePanel.setStyleName("absolutPanel");

		captionPanel.setContentWidget(absolutePanel);
		absolutePanel.setSize("100%", "100%");

		absolutePanel.add(label, 10, 10);

		absolutePanel.add(scrollPanel, 115, 5);
		scrollPanel.setSize("410px", "115px");

		scrollPanel.setWidget(flexTable_Result_Recipients);
		flexTable_Result_Recipients.setSize("100%", "");

		btnButtonShowUploadApplet.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!uploadAppletIsVisible) {
					btnButtonShowUploadApplet.setText("Abbrechen");
					JSNInterface.viewUploadApplet();
					btnButtoncheck.setEnabled(false);
					uploadAppletIsVisible = true;
				} else {
					btnButtonShowUploadApplet.setText("Auswahl");
					JSNInterface.hideUploadApplet();
					btnButtoncheck.setEnabled(true);
					uploadAppletIsVisible = false;
				}

			}
		});

		absolutePanel.add(btnButtonShowUploadApplet, 10, 80);
		cptnpnlRecipient.setSize("535px", "279px");

		cptnpnlRecipient.setStyleName("groupBox");
		rootPanel.add(cptnpnlRecipient, 5, 165);// 33

		AbsolutePanel absolutePanel_2 = new AbsolutePanel();
		absolutePanel_2.setStyleName("absolutPanel");
		cptnpnlRecipient.setContentWidget(absolutePanel_2);
		absolutePanel_2.setSize("100%", "100%");

		
		absolutePanel_2.add(listBoxRecipientLastSelected, 10, 29);
		listBoxRecipientLastSelected.setSize("515px", "64px");
		//setting itemcount value to 1 turns listbox into a drop-down list
		listBoxRecipientLastSelected.setVisibleItemCount(3);
		
		
		listBoxRecipientLastSelected.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				listBoxRecipientFacility.setEnabled(false);
				listBoxRecipientRecipient.setEnabled(false);
				listBoxRecipientLocation.setEnabled(true);
				
				listBoxRecipientFacility.setSelectedIndex(-1);
				listBoxRecipientLocation.setSelectedIndex(-1);
				listBoxRecipientRecipient.setSelectedIndex(-1);
				

				if (listBoxRecipientLastSelected
						.getItemText(listBoxRecipientLastSelected
								.getSelectedIndex()) != null)
					textBox_ElectedRecipient
							.setText(listBoxRecipientLastSelected
									.getItemText(listBoxRecipientLastSelected
											.getSelectedIndex()));
			}
		});

		absolutePanel_2.add(listBoxRecipientLocation, 10, 118);
		listBoxRecipientLocation.setSize("120px", "32px");
		
		//setting itemcount value to 1 turns listbox into a drop-down list
		listBoxRecipientLocation.setVisibleItemCount(1);
		listBoxRecipientLocation.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
				listBoxRecipientLastSelected.setSelectedIndex(-1);
				listBoxRecipientRecipient.setSelectedIndex(-1);
				listBoxRecipientRecipient.setEnabled(false);
				
				listBoxRecipientFacility.setEnabled(true);
				listBoxRecipientFacility.setSelectedIndex(-1);

				textBox_ElectedRecipient.setText("");
				fillListBoxInstitution();
				listBoxRecipientFacility.clear();
				listBoxRecipientRecipient.clear();
				
			}
		});
		

		absolutePanel_2.add(listBoxRecipientFacility, 136, 118);
		listBoxRecipientFacility.setSize("215px", "32px");
		
		//setting itemcount value to 1 turns listbox into a drop-down list
		listBoxRecipientFacility.setVisibleItemCount(1);
		
		listBoxRecipientFacility.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				listBoxRecipientLastSelected.setSelectedIndex(-1);
				
		

				textBox_ElectedRecipient.setText("");

				fillListBoxRecipient();
				listBoxRecipientRecipient.setEnabled(true);
				listBoxRecipientRecipient.setSelectedIndex(-1);
				//listBoxRecipientRecipient.clear();
			}
		});

		absolutePanel_2.add(listBoxRecipientRecipient, 357, 118);
		listBoxRecipientRecipient.setSize("168px", "32px");
		//setting itemcount value to 1 turns listbox into a drop-down list
		listBoxRecipientRecipient.setVisibleItemCount(1);
		listBoxRecipientRecipient.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				String electecRecipientString = listBoxRecipientLocation
						.getValue(listBoxRecipientLocation.getSelectedIndex())
						+ " ; "
						+ listBoxRecipientFacility
								.getValue(listBoxRecipientFacility
										.getSelectedIndex())
						+ " ; "
						+ listBoxRecipientRecipient
								.getValue(listBoxRecipientRecipient
										.getSelectedIndex());
				textBox_ElectedRecipient.setText(electecRecipientString);
			}
		});

		Label lblRecipient = new Label("Empfänger");
		absolutePanel_2.add(lblRecipient, 357, 99);

		Label lblZuletztGewhlt = new Label("Zuletzt gewählt:");
		absolutePanel_2.add(lblZuletztGewhlt, 10, 10);

		Label lblFacility = new Label("Einrichtung:");
		absolutePanel_2.add(lblFacility, 142, 99);

		Label lblLocation = new Label("Ort:");
		absolutePanel_2.add(lblLocation, 10, 97);

		Label lblElectedRecipient = new Label("Gewählter Empfänger:*");
		absolutePanel_2.add(lblElectedRecipient, 10, 197);

		absolutePanel_2.add(textBox_ElectedRecipient, 10, 223);
		textBox_ElectedRecipient.setSize("509px", "16px");
		textBox_ElectedRecipient.setEnabled(false);

		// end data check page

		rootPanel.add(cptnpnlDescription, 5, 452);// 33
		cptnpnlDescription.setStyleName("groupBox");
		cptnpnlDescription.setSize("535px", "441px");

		AbsolutePanel absolutePanel_clinical = new AbsolutePanel();
		absolutePanel_clinical.setStyleName("absolutPanel");
		cptnpnlDescription.setContentWidget(absolutePanel_clinical);
		absolutePanel_clinical.setSize("100%", "100%");
		txtbxNachname.setText("");//Surname
		txtbxNachname.setName("textBox_Surname");

		absolutePanel_clinical.add(txtbxNachname, 187, 10);
		txtbxNachname.setSize("147px", "16px");
		txtbxNachname.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (!GuiValidator.validate(txtbxNachname.getText(), 2, 50)) {
					txtbxNachname.setStyleName("textBoxHighlighted");
				}
			}
		});
		txtbxVorname.setText("");//forename
		txtbxVorname.setName("textBox_Forename");

		absolutePanel_clinical.add(txtbxVorname, 187, 38);
		txtbxVorname.setSize("147px", "16px");
		txtbxVorname.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (!GuiValidator.validate(txtbxVorname.getText(), 2, 50)) {
					txtbxVorname.setStyleName("textBoxHighlighted");
				}
			}
		});

		absolutePanel_clinical.add(rdbtnMale, 187, 72);

		rdbtnFemale.setHTML(" weiblich");
		absolutePanel_clinical.add(rdbtnFemale, 274, 72);//279

		rdbtnUnknown.setHTML(" unbekannt");
		absolutePanel_clinical.add(rdbtnUnknown, 361, 72);//364
		
		
		textBox_Birthdate.setText("");
		textBox_Birthdate.setName("birthdate");


		absolutePanel_clinical.add(textBox_Birthdate, 187, 98);
		
		textBox_Birthdate.setSize("73px", "16px"); //73
		
		textBox_Birthdate.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (!GuiValidator.validateDate(textBox_Birthdate.getText())) {
					textBox_Birthdate.setStyleName("textBoxHighlighted");
					//GuiSingleton.getInstance().setMessageText("Ungültiger Datum.");
				} else {
					textBox_Birthdate.setStyleName("gwt-TextBox");
				}
			}
		});
		textBox_ClinicalIsue.setStyleName("customisedTextArea");

		absolutePanel_clinical.add(textBox_ClinicalIsue, 10, 156);
		textBox_ClinicalIsue.setSize("506px", "60px");
		textBox_ClinicalIndication.setStyleName("customisedTextArea");

		absolutePanel_clinical.add(textBox_ClinicalIndication, 10, 249);
		textBox_ClinicalIndication.setSize("506px", "60px");
		textBox_Comments.setStyleName("customisedTextArea");

		absolutePanel_clinical.add(textBox_Comments, 10, 342);
		textBox_Comments.setSize("506px", "60px");

		Label lblClinicalIsue = new Label("Klinische Fragestellung:");
		absolutePanel_clinical.add(lblClinicalIsue, 10, 137);

		Label lblClinicalIndication = new Label("Klinische Angaben:");
		absolutePanel_clinical.add(lblClinicalIndication, 10, 230);

		Label lblComments = new Label("Anmerkungen:");
		absolutePanel_clinical.add(lblComments, 10, 323);

		Label lblSex = new Label("Geschlecht:*");
		lblSex.setHeight("24");
		absolutePanel_clinical.add(lblSex, 10, 68);

		Label lblBirthdate = new Label("Geburtsdatum:*");
		lblBirthdate.setHeight("24");
		absolutePanel_clinical.add(lblBirthdate, 10, 96);

		absolutePanel_clinical.add(lblforename, 10, 38);
		lblforename.setHeight("24");

		Label lblSurname = new Label("Name:*");
		absolutePanel_clinical.add(lblSurname, 10, 10);
		lblSurname.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblSurname.setHeight("24");

		Label lblTtmmjjjj = new Label("(TT.MM.JJJJ)");
		absolutePanel_clinical.add(lblTtmmjjjj, 279, 98);
		checkPanel.setSize("540", "698");

		
		// start data check page
		rootPanel.add(checkPanel, 5, 165); // 33

		btnButtoncheck.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (checkFields()) {
					cptnpnlRecipient.setVisible(false);
					cptnpnlDescription.setVisible(false);
					
					checkPanel.setDataSet(getTextuellyInformation());
					checkPanel.initialize();
					checkPanel.setVisible(true);
					btnButtoncheck.setVisible(false);
					btnButtonBack.setVisible(true);
					btnButtonSend.setVisible(true);
					btnButtonShowUploadApplet.setVisible(false);
					showFilesToSend(false);

				}
			}
		});
		btnButtoncheck.setText("Daten prüfen");
		rootPanel.add(btnButtoncheck, 5, 908); // 33
		btnButtoncheck.setSize("170px", "25px");

		btnButtonBack.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cptnpnlRecipient.setVisible(true);
				cptnpnlDescription.setVisible(true);
				checkPanel.setVisible(false);
				btnButtoncheck.setVisible(true);
				btnButtonBack.setVisible(false);
				btnButtonSend.setVisible(false);
				btnButtonShowUploadApplet.setVisible(true);
				showFilesToSend(true);

			}
		});
		btnButtonBack.setText("Daten ändern");
		rootPanel.add(btnButtonBack, 5, 718); // 33
		btnButtonBack.setSize("170px", "25px");
		btnButtonSend.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				startSending();
			}
		});
		btnButtonSend.setText("Daten senden");
		rootPanel.add(btnButtonSend, 370, 718);
		btnButtonSend.setSize("170px", "25px");
		btnButtonSend.setVisible(false);
		btnButtonBack.setVisible(false);
		checkPanel.setVisible(false);

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");

		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		initializeComponents();

		showFilesToSend(true);

		GuiSingleton.setInstance(this);

	}

	/**
	 * This method fills the location list-box on 'Send Files' page with the
	 * locations of the saved recipients in the system.
	 */
	private void fillListBoxLocation() {
		listBoxRecipientLocation.clear();
		ServiceDefTarget endpoint = (ServiceDefTarget) sendDicomMailService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "sendData.sdm");

		sendDicomMailService
				.getDicomMailRecipientsLocation(new AsyncCallback<String>() {

					public void onFailure(Throwable e) {
						// resultLabel.setText("Server call failed");
						
						// pb.setTitle("<font color=\"red\"> <b> Server call failed </b></font>");

					}

					public void onSuccess(String result) {
						if (result != null) {
							// check if file deleted
							String resultLocations = result;
							if (resultLocations.length() > 0)
							{
								listBoxRecipientLocation.addItem("");
								for (String location : resultLocations
										.split(";")) {

									listBoxRecipientLocation.addItem(location);
								}	}
							else {
								listBoxRecipientLocation
										.addItem("Kein Empfänger verfügbar!");							
							}
						} else {
							
						}
					}
				});

	}

	/**
	 * This method fills the recent recipients list-box on 'Send Files' page
	 * with last selected recipients address(es).
	 */
	private void fillListBoxRecentRecipients() {
		listBoxRecipientFacility.clear();
		ServiceDefTarget endpoint = (ServiceDefTarget) sendDicomMailService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "sendData.sdm");

		sendDicomMailService.getDicomMailRecipientsRecentRecipients(String
				.valueOf(userId), new AsyncCallback<String>() {

			public void onFailure(Throwable e) {
				// resultLabel.setText("Server call failed");
				
				// pb.setTitle("<font color=\"red\"> <b> Server call failed </b></font>");
			}

			public void onSuccess(String result) {
				if (result != null) {
					// check if file deleted
					if (result.length() > 0)

						for (String entry : result.split("-")) {

							listBoxRecipientLastSelected.addItem(entry);
						}
					else {
						listBoxRecipientLastSelected
								.addItem("Kein Empfänger verfügbar!");
					}
				} else {
					
				}
			}
		});

	}

	/**
	 * This method fills the institution list-box on 'Send Files' page with the
	 * institutions of the saved recipients in the system.
	 */
	private void fillListBoxInstitution() {
		listBoxRecipientFacility.clear();
		ServiceDefTarget endpoint = (ServiceDefTarget) sendDicomMailService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "sendData.sdm");

		sendDicomMailService.getDicomMailRecipientsInstitution(
				listBoxRecipientLocation.getItemText(listBoxRecipientLocation
						.getSelectedIndex()), new AsyncCallback<String>() {

					public void onFailure(Throwable e) {
						// resultLabel.setText("Server call failed");
						
						// pb.setTitle("<font color=\"red\"> <b> Server call failed </b></font>");
					}

					public void onSuccess(String result) {
						if (result != null) {
							// check if file deleted
							String resultInstitutions = result;
							if (resultInstitutions.length() > 0){
								listBoxRecipientFacility
								.addItem("");
								for (String institution : resultInstitutions
										.split(";")) {

									listBoxRecipientFacility
											.addItem(institution);
								}
								}
							else {
								listBoxRecipientFacility
										.addItem("Kein Empfänger verfügbar!");
							}
						} else {
							
						}
					}
				});

	}

	/**
	 * This method fills the recipient list-box on 'Send Files' page with the
	 * recipients (Ziel) of the saved recipients in the system.
	 */
	private void fillListBoxRecipient() {
		listBoxRecipientRecipient.clear();
		ServiceDefTarget endpoint = (ServiceDefTarget) sendDicomMailService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "sendData.sdm");

		sendDicomMailService.getDicomMailRecipientsRecipient(
				listBoxRecipientLocation.getItemText(listBoxRecipientLocation
						.getSelectedIndex()), listBoxRecipientFacility
						.getItemText(listBoxRecipientFacility
								.getSelectedIndex()),
				new AsyncCallback<String>() {

					public void onFailure(Throwable e) {
						// resultLabel.setText("Server call failed");
						
						// pb.setTitle("<font color=\"red\"> <b> Server call failed </b></font>");
					}

					public void onSuccess(String result) {
						if (result != null) {
							// check if file deleted
							String resultRecipients = result;
							if (resultRecipients.length() > 0)
							{
								listBoxRecipientRecipient
								.addItem("");
								for (String recipient : resultRecipients
										.split(";")) {	
								
										listBoxRecipientRecipient
										.addItem(recipient);
										
								}	}
							else {
								listBoxRecipientRecipient
										.addItem("Kein Empfänger verfügbar!");
							}
						} else {
							
						}
					}
				});

	}

	/**
	 * This method initializes the components of the recipient container.
	 */
	private void initializeComponents() {

		// initialize recipient container
		listBoxRecipientFacility.setEnabled(false);
		listBoxRecipientRecipient.setEnabled(false);
		listBoxRecipientLastSelected.setEnabled(true);
		listBoxRecipientLocation.setEnabled(true);

		// initialize demo data
		fillListBoxLocation();
		fillListBoxRecentRecipients();

		this.rdbtnUnknown.setValue(true);

		// defaultUploader.addOnStartUploadHandler(onStartUploadHandler);

	}

	private IUploader.OnStartUploaderHandler onStartUploadHandler = new IUploader.OnStartUploaderHandler() {

		public void onStart(IUploader uploader) {

			counterUploads++;
			refreshSendButton();
		}
	};

	// Load the image in the document and in the case of success attach it to
	// the viewer
	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {

				counterUploads--;
				refreshSendButton();
			}
		}
	};

	/**
	 * Enables/Disables the send-button.
	 */
	private void refreshSendButton() {
		if (counterUploads == 0){
			btnButtonSend.setEnabled(true);
		}else{
			btnButtonSend.setEnabled(false);
		}
	}
	
	/**
	 * Gets the entered textual information.
	 * 
	 * @return completeDataSet, an object of {@link ReferencePortalDataSet}
	 */
	private ReferencePortalDataSet getTextuellyInformation() {
		BasicDataSet.SEX sex = BasicDataSet.SEX.UNKNOWN;
		if (this.rdbtnFemale.getValue()){
			sex = BasicDataSet.SEX.FEMALE;
		}else if (this.rdbtnMale.getValue()){
			sex = BasicDataSet.SEX.MALE;
		}
		
		BasicDataSet basicDataSet = new BasicDataSet(this.txtbxVorname
				.getText(), this.txtbxNachname.getText(), sex,
				this.textBox_Birthdate.getText(), this.textBox_ElectedRecipient
						.getValue(), userId);

		ReferencePortalDataSet completeDataSet = new ReferencePortalDataSet(
				textBox_ClinicalIsue.getValue(), textBox_ClinicalIndication
						.getValue(), textBox_Comments.getValue(), basicDataSet);
		return completeDataSet;
	}

	/**
	 * Sets the message text.
	 * 
	 * @param message
	 */
	public void setMessageText(String message) {
		this.label_Message.setText(message);
	}

	/**
	 * This method checks the text fields which must not be null and changes
	 * it's style if necessary.
	 * 
	 * @return true, if there are missing values, false, else
	 */
	private boolean checkFields() {

		boolean missingValues = false;

		if (!GuiValidator.validateDate(textBox_Birthdate.getText())) {
			textBox_Birthdate.setStyleName("textBoxHighlighted");
			missingValues = true;
		} else {
			textBox_Birthdate.setStyleName("gwt-TextBox");
		}
		if (!GuiValidator.validate(txtbxNachname.getText(), 2, 30)) {
			txtbxNachname.setStyleName("textBoxHighlighted");
			missingValues = true;
		} else {
			txtbxNachname.setStyleName("gwt-TextBox");
		}
		if (!GuiValidator.validate(txtbxVorname.getText(), 2, 30)) {
			txtbxVorname.setStyleName("textBoxHighlighted");
			missingValues = true;
		} else {
			txtbxVorname.setStyleName("gwt-TextBox");
		}
		if (textBox_ElectedRecipient.getText().length() == 0) {
			textBox_ElectedRecipient.setStyleName("textBoxHighlighted");
			missingValues = true;
		} else {
			textBox_ElectedRecipient.setStyleName("gwt-TextBox");
		}

		if (missingValues == false)
			return true;
		else
			return false;
	}

	/**
	 * Start sending the files.
	 */
	public void startSending() {
		GuiSingleton.getInstance().setMessageText(
				"Dateien werden gesendet, bitte warten...");
		ServiceDefTarget endpoint = (ServiceDefTarget) sendDicomMailService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "sendData.sdm");
		// Call send method
		sendDicomMailService.sendDicomMails(this.getTextuellyInformation(),
				new AsyncCallback<String>() {

					public void onFailure(Throwable e) {
						rootPanel.clear();
						GuiSingleton.setInstance(new GwtGuiDemo());
						GuiSingleton
								.getInstance()
								.setMessageText(
										"Beim Senden ist ein Fehler aufgetreten, bitte versuchen Sie es erneut. Falls der Fehler erneut auftritt, wenden Sie sich bitte an den Administrator.");
							
						
						rootPanel
								.add(GuiSingleton.getInstance().getGuiWidget());
	
						System.out.println("Server call failed");
					}

					public void onSuccess(String obj) {
						Window.alert(obj);
						Window.Location.assign(GWT.getHostPageBaseURL()
								+ "Dispatcher?identity=dicomemailpage");
						
						// if (obj != null) {
						// Window
						// .alert("Die Datei(en) wurde(n) erfolgreich versendet!");
						// Window.Location
						// .assign("Dispatcher?identity=dicomemailpage");
						// } else {
						// Window
						// .alert("Beim Senden ist ein Fehler aufgetreten, bitte versuchen Sie es erneut. Falls der Fehler erneut auftritt, wenden Sie sich bitte an den Administrator.");
						// Window.Location
						// .assign("Dispatcher?identity=dicomemailpage");
						// }
					}
				});

	}

	public void showFileSize(){
		sendDicomMailService.getFileSize(new AsyncCallback<String>() {

			public void onFailure(Throwable caught) {
	
			}

			public void onSuccess(String result) {
				
				int row = flexTable_Result_Recipients
				.getRowCount();
				flexTable_Result_Recipients.setWidget(row+2, 0, new Label("Gesamte Größe: "+result));
				
			}
		});
	}
	


	/**
	 * This method shows the uploaded files.
	 * 
	 * @param showDeleteButton
	 */
	public void showFilesToSend(boolean showDeleteButton) {

		uploadAppletIsVisible = false;
		btnButtoncheck.setEnabled(true);
		flexTable_Result_Recipients.clear(true);
		ServiceDefTarget endpoint = (ServiceDefTarget) sendDicomMailService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "sendData.sdm");
		// Call send method
		if (showDeleteButton) {
			sendDicomMailService.getFileListString(new AsyncCallback<String>() {

				public void onFailure(Throwable e) {
							
					// resultLabel.setText("Server call failed");
					// pb.setTitle("<font color=\"red\"> <b> Server call failed </b></font>");
				}

				public void onSuccess(String obj) {
					
					if (obj != null) {
						String[] resultset = obj.split("\r\n");

						for (String fileName : resultset) {
							final String name = fileName;
							if (!name.equals("textualDescriptionAdvancedData.xml")) {
								int row = flexTable_Result_Recipients
										.getRowCount();
								flexTable_Result_Recipients.setWidget(
										flexTable_Result_Recipients
												.getRowCount(), 0, new Label(
												fileName));
								if (flexTable_Result_Recipients.getRowCount() % 2 == 0)
									flexTable_Result_Recipients
											.getCellFormatter().addStyleName(
													row, 0, "FlexTable-Cell");
								// flexTable_Result_Recipients.getFlexCellFormatter().setStyleName(row,
								// 0, "filename");
								Button deleteButton = new Button("Löschen");
								deleteButton
										.addClickHandler(new ClickHandler() {
											public void onClick(ClickEvent event) {

												if (Window
														.confirm("Möchten Sie "
																+ name
																+ " löschen?")) {
													deleteTmpFile(name);
												}
												// ------
												// SC.confirm("", "Möchten Sie "
												// + name+ " löschen?", new
												// BooleanCallback() {
												// public void execute(Boolean
												// value) {
												// if (value != null && value) {
												// //
												// labelAnswer.setContents("OK");
												// deleteTmpFile(name);
												// } else {
												// //
												// labelAnswer.setContents("Cancel");
												// }
												// }
												// });

												// ------
											}
										});

								flexTable_Result_Recipients.setWidget(row, 1,
										deleteButton);
							}
						}
					} else {
						flexTable_Result_Recipients.setWidget(
								flexTable_Result_Recipients.getRowCount(), 0,
								new Label("Keine Datei vorhanden..."));

					}
				}
			});
		} else {
			sendDicomMailService.getFileListString(new AsyncCallback<String>() {

				public void onFailure(Throwable e) {

					// resultLabel.setText("Server call failed");
					// pb.setTitle("<font color=\"red\"> <b> Server call failed </b></font>");
				}

				public void onSuccess(String obj) {
					if (obj != null) {
						String[] resultset = obj.split("\r\n");

						for (String fileName : resultset) {
							final String name = fileName;
							if (!name
									.equals("textualDescriptionAdvancedData.xml")) {
								int row = flexTable_Result_Recipients
										.getRowCount();
								flexTable_Result_Recipients.setWidget(
										flexTable_Result_Recipients
												.getRowCount(), 0, new Label(
												fileName));
								if (flexTable_Result_Recipients.getRowCount() % 2 == 0)
									flexTable_Result_Recipients
											.getCellFormatter().addStyleName(
													row, 0, "FlexTable-Cell");
							}
						}
						
						
						showFileSize();
					} else {
						flexTable_Result_Recipients.setWidget(
								flexTable_Result_Recipients.getRowCount(), 0,
								new Label("Keine Datei vorhanden..."));
						
					}
				}
			});
		}
	}

	
	/**
	 * This method deletes the chosen file from the list of uploaded files.
	 * 
	 * @param filename
	 */
	private void deleteTmpFile(String filename) {

		sendDicomMailService.deleteFileOnTmpFolder(filename,
				new AsyncCallback<Boolean>() {

					public void onFailure(Throwable e) {
						// resultLabel.setText("Server call failed");
						
						// pb.setTitle("<font color=\"red\"> <b> Server call failed </b></font>");
					}

					public void onSuccess(Boolean obj) {
						if (obj != null) {
							// check if file deleted
							boolean deleted = obj;
							if (deleted){
								showFilesToSend(true);
								showFileSize();
							
							}else {
								
							}
						} else {
							
						}
					}
				});
	}
}
