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
package org.openehealth.tewepo.twp.dmp.dmc.client.text;

import org.openehealth.tewepo.twp.dmp.dmc.client.gui.Sending;
import org.openehealth.tewepo.twp.dmp.dmc.client.interfaces.ITextualTab;

import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

/**
 * Textual description for the languages
 * 
 * @author devmis
 *
 */
public class TextualDescription_heart extends Tab implements ITextualTab {

	// String-constants
	// //german
	private final String DE_STRING_00 = "<h2>Herz - CT</h2>";
	private final String DE_STRING_01 = "<h3>Sehr geehrte Patientin, sehr geehrter Patient,<br> vor der Durchführung einer Herz-CT"
			+ " möchten wir Sie um einige Informationen bitten, die es uns erleichtern, die Untersuchung für Sie zu optimieren. Wir"
			+ " möchten Sie bitten, den folgenden Fragebogen ausgefüllt an uns zu senden. Die komplette Datenübertragung <br> erfolgt "
			+ "verschlüsselt und ist von Dritten nicht einsehbar! Ihre Daten werden vertraulich behandelt. Vielen Dank! </p> Bitte "
			+ "füllen Sie das nachfolgende Formular aus.<br> Beachten Sie: Felder mit einem Sternchen (*) sind Pflicht und müssen ausgefüllt werden!</h3><br><br>";
	private final String DE_STRING_02 = "<b>Patient auswählen*</b>";
	private final String DE_STRING_03 = "Name*";
	private final String DE_STRING_04 = "Vorname*";
	private final String DE_STRING_05 = "männlich";
	private final String DE_STRING_06 = "weiblich";
	private final String DE_STRING_07 = "unbekannt";
	private final String DE_STRING_08 = "Geschlecht*";
	private final String DE_STRING_09 = "Geburtsdatum*";
	private final String DE_STRING_10 = "MM/TT/JJJJ";
	private final String DE_STRING_11 = "Gewicht";
	private final String DE_STRING_12 = "Größe";
	private final String DE_STRING_13 = "<b>Bitte füllen Sie das folgende Formular aus</b>";

	private final String DE_STRING_14 = "Aufgrund welcher Beschwerden soll die Herz-CT durchgeführt werden?*";
	private final String DE_STRING_15 = "Ja";
	private final String DE_STRING_16 = "Nein";
	private final String DE_STRING_17 = "Sind Herzrhythmusstörungen bekannt?*";
	private final String DE_STRING_18 = "Wenn ja, welche?";
	private final String DE_STRING_19 = "Regelmäßige Medikamente, die Sie einnehmen?";
	private final String DE_STRING_20 = "Ist bei Ihnen eine Zuckerkrankheit (Diabetes Mellitus) bekannt?*";
	private final String DE_STRING_21 = "Ist bei Ihnen ein erhöhter Blutdruck bekannt?";
	private final String DE_STRING_22 = "Sind bei Ihnen erhöhte Blutfettwerte (z.B. erhöhtes Cholesterin) festgestellt worden?";
	private final String DE_STRING_23 = "Sind in Ihrer Familie (Eltern oder Geschwister) Herzerkrankungen bekannt?";
	private final String DE_STRING_24 = "Rauchen Sie oder haben Sie früher geraucht?";
	private final String DE_STRING_25 = "Wenn ja, wie viele Jahre lang und wie viele Packungen pro Tag etwa im Durchschnitt?";
	// //english
	private final String ENG_STRING_00 = "<h2>Heart - CT</h2>";
	private final String ENG_STRING_01 = "<h3>Dear patient,<br> before carrying out a Heart - CT, "
			+ "we want to ask for some information, that will make it us easier to optimize the medical "
			+ "examination for you. Therefore please send us the filled in form. The data is "
			+ "transferred in encrypted form and cannot be seen by third parties. <br>Your data will be "
			+ "kept strictly confidential. Thanks!</p> Please fill in the following form.<br> Note: Fields marked "
			+ "with an asterisk (*) are mandatory!</h3><br><br>";
	private final String ENG_STRING_02 = "<b>Please choose a patient:</b>";
	private final String ENG_STRING_03 = "Last Name*";
	private final String ENG_STRING_04 = "First Name*";
	private final String ENG_STRING_05 = "male";
	private final String ENG_STRING_06 = "female";
	private final String ENG_STRING_07 = "unknown";
	private final String ENG_STRING_08 = "Sex*";
	private final String ENG_STRING_09 = "Date of Birth*";
	private final String ENG_STRING_10 = "MM/DD/YYYY";
	private final String ENG_STRING_11 = "weight";
	private final String ENG_STRING_12 = "height";
	private final String ENG_STRING_13 = "<b>Please fill in the form</b>";
	private final String ENG_STRING_14 = "Because of which diseases should the Heart-CT be conducted?*";
	private final String ENG_STRING_15 = "Yes";
	private final String ENG_STRING_16 = "No";
	private final String ENG_STRING_17 = "Is any cardiac arrhythmia known?*";
	private final String ENG_STRING_18 = "If so, please specify!";
	private final String ENG_STRING_19 = "What medication do you regularly take?";
	private final String ENG_STRING_20 = "Do you have diabetes?*";
	private final String ENG_STRING_21 = "Do you have an elevated blood pressure?";
	private final String ENG_STRING_22 = "Do you have an elevated cholesterol?";
	private final String ENG_STRING_23 = "Are any heart diseases known in your family (parents or siblings?";
	private final String ENG_STRING_24 = "Do/did you you smoke?";
	private final String ENG_STRING_25 = "If so, how many years and how many packages a day in average?";

	// private fields
	private HeaderItem header = new HeaderItem();
	private HLayout layout0 = new HLayout();
	private VLayout layout1 = new VLayout();
	protected TextItem lastname = new TextItem();
	protected TextItem forename = new TextItem();
	protected RadioGroupItem sex = new RadioGroupItem();
	protected DateItem dateOfBirth = new DateItem();
	protected TextItem weight = new TextItem();
	protected TextItem height = new TextItem();
	protected TextAreaItem complaint = new TextAreaItem();
	protected TextAreaItem textAreaItem = new TextAreaItem();
	protected TextAreaItem medication = new TextAreaItem();
	protected TextAreaItem textAreaItem2 = new TextAreaItem();
	protected TextAreaItem smoking_duration = new TextAreaItem();
	protected RadioGroupItem heartrhy = new RadioGroupItem();
	protected RadioGroupItem diabetes = new RadioGroupItem();
	protected RadioGroupItem blood_pressure = new RadioGroupItem();
	protected RadioGroupItem cholesterol = new RadioGroupItem();
	protected RadioGroupItem family = new RadioGroupItem();
	protected RadioGroupItem smoke = new RadioGroupItem();
	protected DynamicForm profileForm0 = new DynamicForm();
	protected DynamicForm profileForm = new DynamicForm();
	protected DynamicForm profileForm2 = new DynamicForm();
	protected DynamicForm profileForm3 = new DynamicForm();
	private Label info = new Label("");

	private static TextualDescription_heart instance = null;

	/**
	 * Gets the instance to the Textual Description of heart
	 * 
	 * @return an instance of Type TextualDescription
	 */
	public static TextualDescription_heart getInstance() {

		if (instance == null) {

			instance = new TextualDescription_heart();
		}
		return instance;
	}

	
	/**
	 * Constructor of the class
	 * 
	 */
	private TextualDescription_heart() {

		header.setDefaultValue(DE_STRING_01);
		info.setContents(DE_STRING_02);

		profileForm0.setFields(new FormItem[] { header });

		lastname.setTitle(DE_STRING_03);
		lastname.setLength(50);
		lastname.setRequired(true);
		lastname.setKeyPressFilter("[a-z && A-Z && ä-ü && Ä-Ü && - && ´`^]");

		forename.setTitle(DE_STRING_04);
		forename.setLength(50);
		forename.setRequired(true);
		forename.setKeyPressFilter("[a-z && A-Z && ä-ü && Ä-Ü && - && ´`^]");

		sex.setValueMap(DE_STRING_05, DE_STRING_06, DE_STRING_07);
		sex.setVertical(false);
		sex.setWidth(150);
		sex.setTitle(DE_STRING_08);

		dateOfBirth.setTitle(DE_STRING_09);
		dateOfBirth.setUseTextField(true);
		dateOfBirth.setRequired(true);
		dateOfBirth.setHint(DE_STRING_10);

		weight.setTitle(DE_STRING_11);
		weight.setLength(3);
		weight.setKeyPressFilter("[0-9]");
		weight.setHint("kg");

		height.setTitle(DE_STRING_12);
		height.setLength(3);
		height.setKeyPressFilter("[0-9]");
		height.setHint("cm");

		// profileForm
		profileForm.setFields(new FormItem[] { lastname, forename, sex,
				dateOfBirth, weight, height });
		profileForm.setIsGroup(true);
		profileForm.setGroupTitle(DE_STRING_00);
		profileForm.setPadding(30);

		complaint.setTitle(DE_STRING_14);
		complaint.setWidth(170);
		complaint.setIconWidth(100);
		complaint.setLength(350);
		complaint.setRequired(true);

		heartrhy.setValueMap(DE_STRING_15, DE_STRING_16);
		heartrhy.setColSpan(99);
		heartrhy.setVertical(false);
		heartrhy.setWidth(120);
		heartrhy.setTitle(DE_STRING_17);
		heartrhy.setRequired(true);

		textAreaItem.setTitle(DE_STRING_18);
		textAreaItem.setWidth(170);
		textAreaItem.setLength(350);

		medication.setTitle(DE_STRING_19);
		medication.setWidth(170);
		medication.setLength(350);

		diabetes.setValueMap(DE_STRING_15, DE_STRING_16);
		diabetes.setVertical(false);
		diabetes.setWidth(120);
		diabetes.setTitle(DE_STRING_20);
		diabetes.setRequired(true);

		blood_pressure.setValueMap(DE_STRING_15, DE_STRING_16);
		blood_pressure.setVertical(false);
		blood_pressure.setWidth(120);
		blood_pressure.setTitle(DE_STRING_21);

		// profileForm2
		profileForm2.setFields(new FormItem[] { complaint, heartrhy,
				textAreaItem, medication, diabetes, blood_pressure });
		profileForm2.setCellPadding(5);
		// profileForm2.setIsGroup(true);

		cholesterol.setValueMap(DE_STRING_15, DE_STRING_16);
		cholesterol.setVertical(false);
		cholesterol.setWidth(120);
		cholesterol.setTitle(DE_STRING_22);

		family.setValueMap(DE_STRING_15, DE_STRING_16);
		family.setVertical(false);
		family.setWidth(120);
		family.setTitle(DE_STRING_23);

		textAreaItem2.setTitle(DE_STRING_18);
		textAreaItem2.setWidth(170);
		textAreaItem2.setLength(350);

		smoke.setValueMap(DE_STRING_15, DE_STRING_16);
		smoke.setVertical(false);
		smoke.setWidth(120);
		smoke.setTitle(DE_STRING_24);

		smoking_duration.setTitle(DE_STRING_25);
		smoking_duration.setWidth(170);
		smoking_duration.setLength(350);

		// profileForm3
		profileForm3.setFields(new FormItem[] { cholesterol, family,
				textAreaItem2, smoke, smoking_duration });
		// profileForm3.setIsGroup(true);
		profileForm3.setCellPadding(7);

		LayoutSpacer ls = new LayoutSpacer();
		ls.setWidth(100);

		layout0.addMember(profileForm2);
		layout0.addMember(ls);
		layout0.addMember(profileForm3);
		layout0.setLayoutTopMargin(45);

		layout0.setPadding(65);
		layout0.setIsGroup(true);
		layout0.setBackgroundColor("#efefef");

		layout1.addMember(profileForm0);
		layout1.addMember(info);
		layout1.addMember(profileForm);
		layout1.addMember(layout0);
		layout1.setPadding(20);
		layout1.setBackgroundColor("#efefef");

		this.setPane(layout0);
		this.setPane(layout1);
	}

	
	/**
	 * 
	 * Sets Strings in english or german
	 * 
	 */
	public void setStrings() {
		// english
		if (S.getCurrentLanguage().equals(S.LANGUAGE_ENG)) {
			header.setDefaultValue(ENG_STRING_00);
			info.setContents(ENG_STRING_01);
			profileForm.setGroupTitle(ENG_STRING_02);
			lastname.setTitle(ENG_STRING_03);
			forename.setTitle(ENG_STRING_04);
			sex.setValueMap(ENG_STRING_05, ENG_STRING_06, ENG_STRING_07);
			sex.setTitle(ENG_STRING_08);
			dateOfBirth.setTitle(ENG_STRING_09);
			dateOfBirth.setHint(ENG_STRING_10);
			weight.setTitle(ENG_STRING_11);
			height.setTitle(ENG_STRING_12);
			layout0.setGroupTitle(ENG_STRING_13);
			complaint.setTitle(ENG_STRING_14);
			heartrhy.setValueMap(ENG_STRING_15, ENG_STRING_16);
			heartrhy.setTitle(ENG_STRING_17);
			textAreaItem.setTitle(ENG_STRING_18);
			medication.setTitle(ENG_STRING_19);
			diabetes.setValueMap(ENG_STRING_15, ENG_STRING_16);
			diabetes.setTitle(ENG_STRING_20);
			blood_pressure.setValueMap(ENG_STRING_15, ENG_STRING_16);
			blood_pressure.setTitle(ENG_STRING_21);
			cholesterol.setValueMap(ENG_STRING_15, ENG_STRING_16);
			cholesterol.setTitle(ENG_STRING_22);
			family.setValueMap(ENG_STRING_15, ENG_STRING_16);
			family.setTitle(ENG_STRING_23);
			textAreaItem2.setTitle(ENG_STRING_18);
			smoke.setValueMap(ENG_STRING_15, ENG_STRING_16);
			smoke.setTitle(ENG_STRING_24);
			smoking_duration.setTitle(ENG_STRING_25);
		}
		// more languages
		// //else if(language.equals(...){...}

		// default = german
		else {
			header.setDefaultValue(DE_STRING_00);
			info.setContents(DE_STRING_01);
			profileForm.setGroupTitle(DE_STRING_02);
			lastname.setTitle(DE_STRING_03);
			forename.setTitle(DE_STRING_04);
			sex.setValueMap(DE_STRING_05, DE_STRING_06, DE_STRING_07);
			sex.setTitle(DE_STRING_08);
			dateOfBirth.setTitle(DE_STRING_09);
			dateOfBirth.setHint(DE_STRING_10);
			weight.setTitle(DE_STRING_11);
			height.setTitle(DE_STRING_12);
			layout0.setGroupTitle(DE_STRING_13);
			complaint.setTitle(DE_STRING_14);
			heartrhy.setValueMap(DE_STRING_15, DE_STRING_16);
			heartrhy.setTitle(DE_STRING_17);
			textAreaItem.setTitle(DE_STRING_18);
			medication.setTitle(DE_STRING_19);
			diabetes.setValueMap(DE_STRING_15, DE_STRING_16);
			diabetes.setTitle(DE_STRING_20);
			blood_pressure.setValueMap(DE_STRING_15, DE_STRING_16);
			blood_pressure.setTitle(DE_STRING_21);
			cholesterol.setValueMap(DE_STRING_15, DE_STRING_16);
			cholesterol.setTitle(DE_STRING_22);
			family.setValueMap(DE_STRING_15, DE_STRING_16);
			family.setTitle(DE_STRING_23);
			textAreaItem2.setTitle(DE_STRING_18);
			smoke.setValueMap(DE_STRING_15, DE_STRING_16);
			smoke.setTitle(DE_STRING_24);
			smoking_duration.setTitle(DE_STRING_25);
		}
	}

	/**
	 * Checjs if Form of the Profile is valid
	 * 
	 * @return true; when profile Forms are valid
	 * 			false; when profile Forms aren't valid
	 * 
	 */
	public boolean isValid() {
		return profileForm.validate() && profileForm2.validate();
	}

	/**
	 * Shows the textual description
	 * 
	 */
	public void showTextualDescription() {
		String mp_lastname = (String) lastname.getValue();
		String mp_forename = (String) forename.getValue();
		String mp_sex = (String) sex.getValue();
		Object mp_dateOfBirth = dateOfBirth.getValue();
		String mp_weight = (String) weight.getValue();
		if (mp_weight == null) {
			mp_weight = "---";
		} else {
			mp_weight = mp_weight + " kg";
		}
		String mp_height = (String) height.getValue();
		if (mp_height == null) {
			mp_height = "---";
		} else {
			mp_height = mp_height + " cm";
		}
		String mp_complaint = (String) complaint.getValue();
		String mp_textAreaItem = (String) textAreaItem.getValue();
		if (mp_textAreaItem == null) {
			mp_textAreaItem = "---";
		}
		String mp_heartrhy = (String) heartrhy.getValue();
		String mp_medication = (String) medication.getValue();
		if (mp_medication == null) {
			mp_medication = "---";
		}
		String mp_diabetes = (String) diabetes.getValue();
		String mp_blood_pressure = (String) blood_pressure.getValue();
		if (mp_blood_pressure == null) {
			mp_blood_pressure = "---";
		}
		String mp_cholesterol = (String) cholesterol.getValue();
		if (mp_cholesterol == null) {
			mp_cholesterol = "---";
		}
		String mp_family = (String) family.getValue();
		if (mp_family == null) {
			mp_family = "---";
		}
		String mp_textAreaItem2 = (String) textAreaItem2.getValue();
		if (mp_textAreaItem2 == null) {
			mp_textAreaItem2 = "---";
		}
		String mp_smoke = (String) smoke.getValue();
		if (mp_smoke == null) {
			mp_smoke = "---";
		}
		String mp_smoking_duration = (String) smoking_duration.getValue();
		if (mp_smoking_duration == null) {
			mp_smoking_duration = "---";
		}

		// depends on language
		// //english
		if (S.getCurrentLanguage().equals(S.LANGUAGE_ENG)) {
			Sending.getInstance().textual_description.setValue(ENG_STRING_03
					+ ": " + mp_lastname + "\n" + ENG_STRING_04 + ": "
					+ mp_forename + "\n" + ENG_STRING_08 + ": " + mp_sex + "\n"
					+ ENG_STRING_09 + ": " + mp_dateOfBirth + "\n"
					+ ENG_STRING_11 + ": " + mp_weight + "\n" + ENG_STRING_12
					+ ": " + mp_height + "\n" + "\n" + "\n" + "1.) "
					+ ENG_STRING_14 + "\n" + "   - " + mp_complaint + "\n"
					+ "\n" + "2.) " + ENG_STRING_17 + "\n" + "   - "
					+ mp_heartrhy + "\n" + "\n" + "3.) " + ENG_STRING_18 + "\n"
					+ "   - " + mp_textAreaItem + "\n" + "\n" + "4.) "
					+ ENG_STRING_19 + "\n" + "   - " + mp_medication + "\n"
					+ "\n" + "5.) " + ENG_STRING_20 + "\n" + "   - "
					+ mp_diabetes + "\n" + "\n" + "6.) " + ENG_STRING_21 + "\n"
					+ "   - " + mp_blood_pressure + "\n" + "\n" + "7.) "
					+ ENG_STRING_22 + "\n" + "   - " + mp_cholesterol + "\n"
					+ "\n" + "8.) " + ENG_STRING_23 + "\n" + "   - "
					+ mp_family + "\n" + "\n" + "9.) " + ENG_STRING_18 + "\n"
					+ "   - " + mp_textAreaItem2 + "\n" + "\n" + "10.) "
					+ ENG_STRING_24 + "\n" + "   - " + mp_smoke + "\n" + "\n"
					+ "11.) " + ENG_STRING_20 + "\n" + "   - " + mp_diabetes
					+ "\n" + "\n" + "12.) " + ENG_STRING_25 + "\n" + "   - "
					+ mp_smoking_duration + "\n" + "\n");
		}
		// more languages
		// //else if(language.equals(...){...}

		// //default = german
		else {
			Sending.getInstance().textual_description.setValue(DE_STRING_03
					+ ": " + mp_lastname + "\n" + DE_STRING_04 + ": "
					+ mp_forename + "\n" + DE_STRING_08 + ": " + mp_sex + "\n"
					+ DE_STRING_09 + ": " + mp_dateOfBirth + "\n"
					+ DE_STRING_11 + ": " + mp_weight + "\n" + DE_STRING_12
					+ ": " + mp_height + "\n" + "\n" + "\n" + "1.) "
					+ DE_STRING_14 + "\n" + "   - " + mp_complaint + "\n"
					+ "\n" + "2.) " + DE_STRING_17 + "\n" + "   - "
					+ mp_heartrhy + "\n" + "\n" + "3.) " + DE_STRING_18 + "\n"
					+ "   - " + mp_textAreaItem + "\n" + "\n" + "4.) "
					+ DE_STRING_19 + "\n" + "   - " + mp_medication + "\n"
					+ "\n" + "5.) " + DE_STRING_20 + "\n" + "   - "
					+ mp_diabetes + "\n" + "\n" + "6.) " + DE_STRING_21 + "\n"
					+ "   - " + mp_blood_pressure + "\n" + "\n" + "7.) "
					+ DE_STRING_22 + "\n" + "   - " + mp_cholesterol + "\n"
					+ "\n" + "8.) " + DE_STRING_23 + "\n" + "   - " + mp_family
					+ "\n" + "\n" + "9.) " + DE_STRING_18 + "\n" + "   - "
					+ mp_textAreaItem2 + "\n" + "\n" + "10.) " + DE_STRING_24
					+ "\n" + "   - " + mp_smoke + "\n" + "\n" + "11.) "
					+ DE_STRING_20 + "\n" + "   - " + mp_diabetes + "\n" + "\n"
					+ "12.) " + DE_STRING_25 + "\n" + "   - "
					+ mp_smoking_duration + "\n" + "\n");
		}
	}
}
