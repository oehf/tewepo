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
package org.openehealth.tewepo.twp.dmp.dmc.client.gui;

import org.openehealth.tewepo.twp.dmp.dmc.client.data.BasicDataSet;
import org.openehealth.tewepo.twp.dmp.dmc.client.data.ReferencePortalDataSet;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;


/**
 * This class represents the panel where entered/selected e-mail content can be
 * checked.
 * 
 * @author devmis
 * 
 */
public class CheckEmailContentPanel extends AbsolutePanel {

	private ReferencePortalDataSet dataSet = null;
	private Label label_Result_Birthdate = new Label("Geburtsdatum");
	private Label label_Result_Sex = new Label("Geschlecht");
	private Label label_Result_Forename = new Label("Vorname");
	private Label label_Result_Surname = new Label("Nachname");
	private Label label_Result_Recipient = new Label("Empfänger");
	private TextArea textArea_Result_Comment = new TextArea();
	private TextArea textArea_Result_ClinicalIndication = new TextArea();
	private TextArea textArea_Result_ClinicalIsue = new TextArea();

	/**
	 * Creates a new panel with entered/selected e-mail content.
	 */
	public CheckEmailContentPanel() {
		setSize("540px", "548px");

		CaptionPanel captionPanel = new CaptionPanel("Empfänger");
		captionPanel.setStyleName("groupBox");
		add(captionPanel, 0, 8);
		captionPanel.setSize("535px", "80px");

		AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.setStyleName("absolutPanel");
		captionPanel.setContentWidget(absolutePanel);
		absolutePanel.setSize("100%", "100%");

		Label lblEmpfnger = new Label("Empfänger:");
		absolutePanel.add(lblEmpfnger, 10, 10);

		absolutePanel.add(label_Result_Recipient, 115, 10);
		label_Result_Recipient.setSize("410px", "47px");

		CaptionPanel captionPanel_1 = new CaptionPanel("Angaben zum Patienten");
		captionPanel_1.setStyleName("groupBox");
		add(captionPanel_1, 0, 96);
		captionPanel_1.setSize("535px", "441px");

		AbsolutePanel absolutePanel_1 = new AbsolutePanel();
		absolutePanel_1.setStyleName("absolutPanel");
		captionPanel_1.setContentWidget(absolutePanel_1);
		absolutePanel_1.setSize("100%", "100%");

		textArea_Result_ClinicalIsue.setStyleName("flowPanel");
		absolutePanel_1.add(textArea_Result_ClinicalIsue, 10, 156);
		textArea_Result_ClinicalIsue.setSize("506px", "60px");
		textArea_Result_ClinicalIsue.setEnabled(false);

		textArea_Result_ClinicalIndication.setStyleName("flowPanel");
		absolutePanel_1.add(textArea_Result_ClinicalIndication, 10, 249);
		textArea_Result_ClinicalIndication.setSize("506px", "60px");
		textArea_Result_ClinicalIndication.setEnabled(false);

		textArea_Result_Comment.setStyleName("flowPanel");
		absolutePanel_1.add(textArea_Result_Comment, 10, 342);
		textArea_Result_Comment.setSize("506px", "60px");
		textArea_Result_Comment.setEnabled(false);

		Label label_5 = new Label("Klinische Fragestellung:");
		absolutePanel_1.add(label_5, 10, 137);

		Label label_6 = new Label("Klinische Angaben:");
		absolutePanel_1.add(label_6, 10, 230);

		Label label_7 = new Label("Anmerkungen:");
		absolutePanel_1.add(label_7, 10, 323);

		Label lblGeschlecht = new Label("Geschlecht:");
		lblGeschlecht.setHeight("24");
		absolutePanel_1.add(lblGeschlecht, 10, 66);

		Label lblGeburtsdatum = new Label("Geburtsdatum:");
		lblGeburtsdatum.setHeight("24");
		absolutePanel_1.add(lblGeburtsdatum, 10, 96);

		Label lblVorname = new Label("Vorname:");
		lblVorname.setHeight("24");
		absolutePanel_1.add(lblVorname, 10, 38);

		Label lblName = new Label("Name:");
		lblName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblName.setHeight("24");
		absolutePanel_1.add(lblName, 10, 10);

		absolutePanel_1.add(label_Result_Surname, 196, 10);

		absolutePanel_1.add(label_Result_Forename, 196, 38);

		absolutePanel_1.add(label_Result_Sex, 196, 66);

		absolutePanel_1.add(label_Result_Birthdate, 196, 96);

	}

	/**
	 * This method gets the entered files and texts from the previous page, if
	 * not null.
	 */
	public void initialize() {
		if (this.dataSet == null)
			return;
		if (this.dataSet.getBasicDataSet().getRecipient() != null)
			label_Result_Recipient.setText(this.dataSet.getBasicDataSet()
					.getRecipient());
		if (this.dataSet.getBasicDataSet().getSurname() != null)
			label_Result_Surname.setText(this.dataSet.getBasicDataSet()
					.getSurname());
		if (this.dataSet.getBasicDataSet().getForename() != null)
			label_Result_Forename.setText(this.dataSet.getBasicDataSet()
					.getForename());
		if (this.dataSet.getBasicDataSet().getBirthdate() != null)
			label_Result_Birthdate.setText(this.dataSet.getBasicDataSet()
					.getBirthdate());
		if (this.dataSet.getBasicDataSet().getSex() != null) {
			if ((this.dataSet.getBasicDataSet().getSex()
					.compareTo(BasicDataSet.SEX.FEMALE)) == 0)
				label_Result_Sex.setText("weiblich");
			else if ((this.dataSet.getBasicDataSet().getSex()
					.compareTo(BasicDataSet.SEX.MALE)) == 0)
				label_Result_Sex.setText("männlich");
			else
				label_Result_Sex.setText("unbekannt");
		}
		if (this.dataSet.getClinicalIssue() != null)
			textArea_Result_ClinicalIsue.setText(this.dataSet
					.getClinicalIssue());
		if (this.dataSet.getClinicalIndication() != null)
			textArea_Result_ClinicalIndication.setText(this.dataSet
					.getClinicalIndication());
		if (this.dataSet.getComments() != null)
			textArea_Result_Comment.setText(this.dataSet.getComments());

	}

	/**
	 * Sets data set with the given attribute.
	 * 
	 * @param dataSet
	 *            {@link ReferencePortalDataSet}
	 */
	public void setDataSet(ReferencePortalDataSet dataSet) {
		this.dataSet = dataSet;
	}

}
