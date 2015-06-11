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

import java.util.Date;

import org.openehealth.tewepo.twp.dmp.dmc.client.gui.Sending;
import org.openehealth.tewepo.twp.dmp.dmc.client.interfaces.ITextualTab;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;


/**
 * This class is for textual description.
 * 
 * @author devmis
 * 
 */
public class TextualDescription extends Tab implements ITextualTab {

	// String-Constants
	// //german
	private final String DE_STRING_00 = "<b>Patient auswählen*</b>";
	private final String DE_STRING_01 = "Name*";
	private final String DE_STRING_02 = "Vorname*";
	private final String DE_STRING_03 = "männlich";
	private final String DE_STRING_04 = "weiblich";
	private final String DE_STRING_05 = "unbekannt";
	private final String DE_STRING_06 = "Geschlecht*";
	private final String DE_STRING_07 = "Geburtsdatum*";
	private final String DE_STRING_08 = "MM/TT/JJJJ";
	private final String DE_STRING_09 = "Klinische Fragestellung*:";
	private final String DE_STRING_10 = "Klinische Angaben*:";
	private final String DE_STRING_11 = "Anmerkungen:";

	// //english
	private final String ENG_STRING_00 = "<b>Please choose a patient:</b>";
	private final String ENG_STRING_01 = "Last Name*";
	private final String ENG_STRING_02 = "First Name*";
	private final String ENG_STRING_03 = "male";
	private final String ENG_STRING_04 = "female";
	private final String ENG_STRING_05 = "unknown";
	private final String ENG_STRING_06 = "Sex*";
	private final String ENG_STRING_07 = "Date of Birth*";
	private final String ENG_STRING_08 = "MM/DD/JJJJ";
	private final String ENG_STRING_09 = "Clinical Question*:";
	private final String ENG_STRING_10 = "Clinical Facts*:";
	private final String ENG_STRING_11 = "Notes:";

	// private fields
	private VLayout layout0 = new VLayout();
	private DynamicForm profileForm0 = new DynamicForm();
	private DynamicForm profileForm1 = new DynamicForm();
	private DynamicForm profileForm2 = new DynamicForm();
	private DynamicForm profileForm3 = new DynamicForm();
	private TextItem lastname = new TextItem();
	private TextItem forename = new TextItem();
	private RadioGroupItem sex = new RadioGroupItem();
	private DateItem dateOfBirth = new DateItem();
	private TextAreaItem clinicalQuestion = new TextAreaItem();
	private TextAreaItem clinicalFacts = new TextAreaItem();
	private TextAreaItem notes = new TextAreaItem();

	private static TextualDescription instance = null;

	/**
	 * Singleton-Pattern
	 * 
	 * @return an instance of the class
	 */
	public static TextualDescription getInstance() {
		if (instance == null) {
			instance = new TextualDescription();
		}
		return instance;
	}

	/**
	 * Creates a new tab for textual description.
	 */
	TextualDescription() {
		lastname.setTitle(DE_STRING_01);
		lastname.setLength(50);
		lastname.setRequired(true);
		lastname.setKeyPressFilter("[a-z && A-Z && ä-ü && Ä-Ü && - && ´ ` ^]");

		forename.setTitle(DE_STRING_02);
		forename.setLength(50);
		forename.setRequired(true);
		forename.setKeyPressFilter("[a-z && A-Z && ä-ü && Ä-Ü && - &&´ ` ^]");

		sex.setValueMap(DE_STRING_03, DE_STRING_04, DE_STRING_05);
		sex.setTitle(DE_STRING_06);
		sex.setVertical(false);
		sex.setRequired(true);
		sex.setAlign(Alignment.LEFT);
		sex.setWidth(170);

		dateOfBirth.setTitle("<b>" + DE_STRING_07 + "</b>");
		dateOfBirth.setUseTextField(true);
		dateOfBirth.setWidth(90);
		dateOfBirth.setRequired(true);
		dateOfBirth.setHint(DE_STRING_08);

		// profileForm0
		profileForm0.setFields(new FormItem[] { lastname, forename, sex,
				dateOfBirth });
		profileForm0.setWidth100();
		profileForm0.setIsGroup(true);
		profileForm0.setGroupTitle(DE_STRING_00);
		profileForm0.setPadding(10);
		profileForm0.setBackgroundColor("efefef");

		// profileForm1
		clinicalQuestion.setShowTitle(false);
		clinicalQuestion.setRequired(true);
		clinicalQuestion.setWidth(730);
		clinicalQuestion.setHeight(70);
		profileForm1.setWidth100();
		profileForm1.setFields(new FormItem[] { clinicalQuestion });
		profileForm1.setIsGroup(true);
		profileForm1.setGroupTitle("<b>" + DE_STRING_09 + "</b>");
		profileForm1.setPadding(10);
		profileForm1.setBackgroundColor("efefef");

		// profileForm2
		clinicalFacts.setShowTitle(false);
		clinicalFacts.setWidth(730);
		clinicalFacts.setHeight(170);
		clinicalFacts.setRequired(true);
		profileForm2.setWidth100();
		profileForm2.setFields(new FormItem[] { clinicalFacts });
		profileForm2.setIsGroup(true);
		profileForm2.setGroupTitle("<b>" + DE_STRING_10 + "</b>");
		profileForm2.setPadding(10);
		profileForm2.setBackgroundColor("efefef");

		// profileForm3
		notes.setShowTitle(false);
		notes.setRequired(true);
		notes.setWidth(730);
		notes.setHeight(70);
		profileForm3.setWidth100();
		profileForm3.setHeight100();
		profileForm3.setFields(new FormItem[] { notes });
		profileForm3.setIsGroup(true);
		profileForm3.setGroupTitle("<b>" + DE_STRING_11 + "</b>");
		profileForm3.setPadding(10);
		profileForm3.setBackgroundColor("efefef");

		layout0.setPadding(10);
		layout0.addMember(profileForm0);
		layout0.addMember(profileForm1);
		layout0.addMember(profileForm2);
		layout0.addMember(profileForm3);

		this.setPane(layout0);

		lastname.setValue("Mustermann");
		forename.setValue("Max");
		clinicalFacts.setValue("Klinische Angaben");
		clinicalQuestion.setValue("Klinische Fragestellung");
		dateOfBirth.setValue(new Date());
		sex.setValue(DE_STRING_05);

	}

	/**
	 * @see org.openehealth.tewepo.twp.dmp.dmc.client.interfaces.ITab#setStrings()
	 */
	public void setStrings() {
		if (S.getCurrentLanguage().equals(S.LANGUAGE_ENG)) {
			lastname.setTitle(ENG_STRING_01);
			forename.setTitle(ENG_STRING_02);
			sex.setValueMap(ENG_STRING_03, ENG_STRING_04, ENG_STRING_05);
			sex.setTitle(ENG_STRING_06);
			dateOfBirth.setTitle("<b>" + ENG_STRING_07 + "</b>");
			dateOfBirth.setHint(ENG_STRING_08);
			profileForm0.setGroupTitle(ENG_STRING_00);
			profileForm1.setGroupTitle("<b>" + ENG_STRING_09 + "</b>");
			profileForm2.setGroupTitle("<b>" + ENG_STRING_10 + "</b>");
			profileForm3.setGroupTitle("<b>" + ENG_STRING_11 + "</b>");

		}
		// more languages
		// //else if(language.equals(...){...}

		// //default = german
		else {
			lastname.setTitle(DE_STRING_01);
			forename.setTitle(DE_STRING_02);
			sex.setValueMap(DE_STRING_03, DE_STRING_04, DE_STRING_05);
			sex.setTitle(DE_STRING_06);
			dateOfBirth.setTitle("<b>" + DE_STRING_07 + "</b>");
			dateOfBirth.setHint(DE_STRING_08);
			profileForm0.setGroupTitle(DE_STRING_00);
			profileForm1.setGroupTitle("<b>" + DE_STRING_09 + "</b>");
			profileForm2.setGroupTitle("<b>" + DE_STRING_10 + "</b>");
			profileForm3.setGroupTitle("<b>" + DE_STRING_11 + "</b>");
		}
	}

	/**
	 * @see org.openehealth.tewepo.twp.dmp.dmc.client.interfaces.ITextualTab#isValid()
	 */
	public boolean isValid() {
		return profileForm0.validate() && profileForm1.validate()
				&& profileForm2.validate();
	}

	/**
	 * @see org.openehealth.tewepo.twp.dmp.dmc.client.interfaces.ITextualTab#showTextualDescription()
	 */
	public void showTextualDescription() {
		String mp_lastname = (String) lastname.getValue();
		String mp_forename = (String) forename.getValue();
		String mp_sex = (String) sex.getValue();
		Object mp_dateOfBirth = dateOfBirth.getValue();
		String mp_clinicalQuestion = (String) clinicalQuestion.getValue();
		String mp_clinicalFacts = (String) clinicalFacts.getValue();
		String mp_notes = (String) notes.getValue();
		if (mp_notes == null) {
			mp_notes = "---";
		}
		// depends on language
		// //english
		if (S.getCurrentLanguage().equals(S.LANGUAGE_ENG)) {
			Sending.getInstance().textual_description.setValue(ENG_STRING_01
					+ ": " + mp_lastname + "\n" + ENG_STRING_02 + ": "
					+ mp_forename + "\n" + ENG_STRING_06 + ": " + mp_sex + "\n"
					+ ENG_STRING_07 + ": " + mp_dateOfBirth + "\n" + "\n"
					+ ENG_STRING_09 + "\n" + mp_clinicalQuestion + "\n" + "\n"
					+ ENG_STRING_10 + "\n" + mp_clinicalFacts + "\n" + "\n"
					+ ENG_STRING_11 + "\n" + mp_notes);
		}
		// more languages
		// //else if(language.equals(...){...}

		// //default = german
		else {
			Sending.getInstance().textual_description.setValue(DE_STRING_01
					+ ": " + mp_lastname + "\n" + DE_STRING_02 + ": "
					+ mp_forename + "\n" + DE_STRING_06 + ": " + mp_sex + "\n"
					+ DE_STRING_07 + ": " + mp_dateOfBirth + "\n" + "\n"
					+ DE_STRING_09 + "\n" + mp_clinicalQuestion + "\n" + "\n"
					+ DE_STRING_10 + "\n" + mp_clinicalFacts + "\n" + "\n"
					+ DE_STRING_11 + "\n" + mp_notes);
		}
	}
}
