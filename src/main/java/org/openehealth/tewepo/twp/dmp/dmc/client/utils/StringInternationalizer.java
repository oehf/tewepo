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
package org.openehealth.tewepo.twp.dmp.dmc.client.utils;

/**
 * This class is for internationalizing the strings.
 * 
 * @author devmis
 * 
 */
public class StringInternationalizer {
	private static StringInternationalizer instance = null;

	// Language-Optionen
	public final String LANGUAGE_DE = "de";
	public final String LANGUAGE_ENG = "eng";
	private String LANGUAGE_CURRENT = LANGUAGE_DE;

	// DEUTSCHE STING-KONSTANTEN
	private final String DE_STATUS_1 = "Schritt 1/3";
	private final String DE_STATUS_2 = "Schritt 2/3";
	private final String DE_STATUS_3 = "Schritt 3/3";
	// private final String DE_STATUS_4 ="Schritt 4/5";

	private final String DE_STATUS_ERROR_1 = "Bitte markierte Felder korrigieren !";

	private final String DE_TAB0_HEADER = "Patient wählen";
	private final String DE_TAB0_a = "<b>Patient auswählen*</b>";
	private final String DE_TAB0_b = "Name*";
	private final String DE_TAB0_c = "Vorname*";
	private final String DE_TAB0_d = "männlich";
	private final String DE_TAB0_e = "weiblich";
	private final String DE_TAB0_f = "unbekannt";
	private final String DE_TAB0_g = "Geschlecht*";
	private final String DE_TAB0_h = "Geburtsdatum*";
	private final String DE_TAB0_i = "Klinische Fragestellung*";
	private final String DE_TAB0_j = "Klinische Angaben*";
	private final String DE_TAB0_k = "Anmerkungen";
	private final String DE_TAB1_a = "<b>Neu wählen:</b>";
	private final String DE_TAB1_b = "<b>Zuletzt gewählt:</b>";
	private final String DE_TAB1_c = "<b>Bitte Empfänger auswählen:</b>";
	private final String DE_TAB1_d = "<b>Details:</b>";
	private final String DE_TAB1_e = "<b>Name</b>";
	private final String DE_TAB1_f = "Ort";
	private final String DE_TAB1_g = "Kategorie";
	private final String DE_TAB1_h = "E-Mail 1";
	private final String DE_TAB1_i = "E-Mail 2";
	private final String DE_TAB1_j = "E-Mail 3";
	private final String DE_TAB1_k = "<b>Senden an:</b>";
	private final String DE_TAB1_l = "<b>Legende:</b>";
	private final String DE_TAB1_m = "senden";
	private final String DE_TAB1_n = "nicht senden";
	private final String DE_TAB3_a = "Datei(en) auswählen";
	private final String DE_TAB3_b = "Durchsuchen";
	private final String DE_TAB3_c = "Hochladen";
	private final String DE_TAB4_a = "Empfänger";
	private final String DE_TAB4_b = "Ändern";
	private final String DE_TAB4_c = "Angaben zum Patienten";
	private final String DE_TAB4_d = "Dateiauswahl";

	private final String DE_TAB1_HEADER = "Empfänger wählen";
	private final String DE_TAB2_HEADER = "Angaben zum Patienten";
	private final String DE_TAB3_HEADER = "Dateiauswahl";
	private final String DE_TAB4_HEADER = "Absenden";
	private final String DE_BACK_BUTTON = "Zurück";
	private final String DE_NEXT_BUTTON_a = "Weiter";
	private final String DE_NEXT_BUTTON_b = "DICOM-Mail absenden";

	private final String DE_TAB2_a = "Gewicht";
	private final String DE_TAB2_b = "Größe";
	private final String DE_TAB2_c = "<b>Bitte füllen Sie das folgende Formular aus</b>";
	private final String DE_TAB2_d = "MM/TT/JJJJ";

	// SIRT
	private final String DE_TAB2_sirt_a = "<h2>SIRT - Selektive Interne Radiotherapie</h2>";
	private final String DE_TAB2_sirt_b = "<h3>Sehr geehrte Patientin, sehr geehrter Patient, <br>vor der Durchführung "
			+ "einer SIRT-Therapie möchten wir Sie um einige Informationen bitten, die es uns "
			+ "erleichtern, die Untersuchung für Sie <br>zu optimieren. Wir möchten Sie bitten, den "
			+ "folgenden Fragebogen ausgefüllt an uns zu senden. Die Datenübertragung erfolgt verschlüsselt <br> "
			+ "und ist von Dritten nicht einsehbar! Ihre Daten werden vertraulich behandelt. Vielen Dank!</p>"
			+ "Bitte füllen Sie das nachfolgende Formular aus. <br> Beachten Sie bitte: Felder mit einem Sternchen (*) "
			+ "sind Pflicht und müssen ausgefüllt werden!</h3><br><br>";
	private final String DE_TAB2_sirt_c = "<b>Welche Tumorerkrankungen haben Sie?</b>";
	private final String DE_TAB3_sirt_c = "Welche Tumorerkrankungen haben Sie?";
	private final String DE_TAB2_sirt_d = "Kolorektalkarzinom";
	private final String DE_TAB2_sirt_e = "Mammakarzinom";
	private final String DE_TAB2_sirt_f = "Hepatozelluläres Karzinom";
	private final String DE_TAB2_sirt_g = "Neuroendokriner Tumor";
	private final String DE_TAB2_sirt_h = "Cholangiozelluläres Karzinom";
	private final String DE_TAB2_sirt_i = "Sonstige Tumorerkrankungen";
	private final String DE_TAB2_sirt_j = "<b>Welche Metastasen sind bekannt?</b>";
	private final String DE_TAB3_sirt_j = "Welche Metastasen sind bekannt?";
	private final String DE_TAB2_sirt_k = "Lunge";
	private final String DE_TAB2_sirt_l = "Leber";
	private final String DE_TAB2_sirt_m = "Knochen";
	private final String DE_TAB2_sirt_n = "Darm";
	private final String DE_TAB2_sirt_o = "Kopf";
	private final String DE_TAB2_sirt_p = "<b>Bisherige Therapien:</b>";
	private final String DE_TAB3_sirt_p = "Bisherige Therapien:";
	private final String DE_TAB2_sirt_q = "Therapien";
	private final String DE_TAB2_sirt_r = "Tumorer-<br>krankung";

	// Prostata
	private final String DE_TAB2_prostate_a = "<h2>Prostata - MRT</h2>";
	private final String DE_TAB2_prostate_b = "<h3>Sehr geehrter Patient, <br>damit wir Ihre Untersuchung bestmöglich auswerten können, benötigen "
			+ "wir umfassende Informationen über Ihre Erkrankung.<br>Wir möchten Sie bitten, den folgenden Fragebogen ausgefüllt an uns zu senden. "
			+ "Die komplette Datenübertragung erfolgt verschlüsselt <br> und ist von Dritten nicht einsehbar! Ihre Daten werden vertraulich behandelt. "
			+ "Vielen Dank! </p> Bitte füllen Sie das nachfolgende Formular aus. <br>Beachten Sie bitte: "
			+ "Felder mit einem Sternchen (*) sind Pflicht und müssen ausgefüllt werden!<h3/><br>";
	private final String DE_TAB2_prostate_c = "<b>Bisherige Diagnose*</b>";
	private final String DE_TAB3_prostate_c = "Bisherige Diagnose"; // tab3
	private final String DE_TAB2_prostate_d = "Diagnose";
	private final String DE_TAB2_prostate_e = "Bisher keine Diagnose";
	private final String DE_TAB2_prostate_f = "Prostatitis";
	private final String DE_TAB2_prostate_g = "Prostata Karzinom";
	private final String DE_TAB2_prostate_h = "Aktueller PSA-Wert";
	private final String DE_TAB2_prostate_i = "<b>Sind bereits Biopsien (Probeentnahmen) erfolgt?*</b>";
	private final String DE_TAB3_prostate_i = "Sind bereits Biopsien (Probeentnahmen) erfolgt?"; // tab3
	private final String DE_TAB2_prostate_j = "Biopsien";
	private final String DE_TAB2_prostate_k = "Bisher keine Biopsie";
	private final String DE_TAB2_prostate_l = "Negative Biopsie";
	private final String DE_TAB2_prostate_m = "Positive Biopsie";
	private final String DE_TAB2_prostate_n = "Anzahl negativer Biopsien";
	private final String DE_TAB2_prostate_o = "mehr als 4";
	private final String DE_TAB2_prostate_p = "Gleason Score";
	private final String DE_TAB2_prostate_q = "Letzte Biopsie am";
	private final String DE_TAB2_prostate_r = "<b>Bisherige Therapien</b>";
	private final String DE_TAB3_prostate_r = "Bisherige Therapien"; // tab3
	private final String DE_TAB2_prostate_s = "Prostatektomie (Operation)";
	private final String DE_TAB2_prostate_t = "Bestrahlung";
	private final String DE_TAB2_prostate_u = "Hormontherapie";
	private final String DE_TAB2_prostate_v = "Datum";
	private final String DE_TAB2_prostate_w = "Zeitraum";
	private final String DE_TAB2_prostate_x = "von";
	private final String DE_TAB2_prostate_y = "bis";

	// ENGLISCHE STRING-KONSTANTEN
	private final String ENG_STATUS_1 = "Step 1/5";
	private final String ENG_STATUS_2 = "Step 2/5";
	private final String ENG_STATUS_3 = "Step 3/5";
	private final String ENG_STATUS_4 = "Step 4/5";

	private final String ENG_STATUS_ERROR_1 = "Please correct marked fields !";

	private final String ENG_TAB0_HEADER = "Choosing Patient";
	private final String ENG_TAB0_a = "<b>Please choose a patient:</b>";
	private final String ENG_TAB0_b = "Last Name*";
	private final String ENG_TAB0_c = "First Name*";
	private final String ENG_TAB0_d = "male";
	private final String ENG_TAB0_e = "female";
	private final String ENG_TAB0_f = "unknown";
	private final String ENG_TAB0_g = "Sex*";
	private final String ENG_TAB0_h = "Date of Birth*";
	private final String ENG_TAB0_i = "Clinical Question";
	private final String ENG_TAB0_j = "Clinical Facts";
	private final String ENG_TAB0_k = "Notes";
	private final String ENG_TAB1_a = "<b>Choose a new one:</b>";
	private final String ENG_TAB1_b = "<b>Last chosen:</b>";
	private final String ENG_TAB1_c = "<b>Please choose an addressee:</b>";
	private final String ENG_TAB1_d = "<b>Details:</b>";
	private final String ENG_TAB1_e = "Name";
	private final String ENG_TAB1_f = "City";
	private final String ENG_TAB1_g = "Category";
	private final String ENG_TAB1_h = "e-mail 1";
	private final String ENG_TAB1_i = "e-mail 2";
	private final String ENG_TAB1_j = "e-mail 3";
	private final String ENG_TAB1_k = "<b>Send to:</b>";
	private final String ENG_TAB1_l = "<b>Legend:</b>";
	private final String ENG_TAB1_m = "send";
	private final String ENG_TAB1_n = "not send";
	private final String ENG_TAB1_HEADER = "Choosing addressee";
	private final String ENG_TAB2_HEADER = "Textual Description";
	private final String ENG_TAB3_HEADER = "Choosing File";
	private final String ENG_TAB4_HEADER = "Send";
	private final String ENG_BACK_BUTTON = "Back";
	private final String ENG_NEXT_BUTTON_a = "Next";
	private final String ENG_NEXT_BUTTON_b = "Send DICOM-Mail";
	private final String ENG_TAB3_a = "Choosing file(s)";
	private final String ENG_TAB3_b = "Search";
	private final String ENG_TAB3_c = "Upload";
	private final String ENG_TAB4_a = "Addressee";
	private final String ENG_TAB4_b = "edit";
	private final String ENG_TAB4_c = "Textual Description";
	private final String ENG_TAB4_d = "Choosen Files";

	private final String ENG_TAB2_a = "weight";
	private final String ENG_TAB2_b = "height";
	private final String ENG_TAB2_c = "<b>Please fill in the form</b>";
	private final String ENG_TAB2_d = "MM/DD/YYYY";

	// SIRT
	private final String ENG_TAB2_sirt_a = "<h2>SIRT - Selective Intern Radiotherapy</h2>";
	private final String ENG_TAB2_sirt_b = "<h3>Dear patient,<br> before carrying out a SIRT - Therapy, "
			+ "we want to ask for some information, that will make it us easier to optimize the medical "
			+ "examination for you. Therefore please send us the filled in form. The data is "
			+ "transferred in encrypted form and cannot be seen by third parties. <br>Your data will be "
			+ "kept strictly confidential. Thanks!</p> Please fill in the following form.<br> Note: Fields marked "
			+ "with an asterisk (*) are mandatory!</h3><br><br>";
	private final String ENG_TAB2_sirt_c = "<b>Which tumor diseases do you have?</b>";
	private final String ENG_TAB3_sirt_c = "Which tumor diseases do you have?";
	private final String ENG_TAB2_sirt_d = "Colorectal cancer";
	private final String ENG_TAB2_sirt_e = "Breast cancer";
	private final String ENG_TAB2_sirt_f = "Hepatocellular carcinoma";
	private final String ENG_TAB2_sirt_g = "Neuroendocrine tumor";
	private final String ENG_TAB2_sirt_h = "Cholangiocarcinoma";
	private final String ENG_TAB2_sirt_i = "Other tumor diseases";
	private final String ENG_TAB2_sirt_j = "<b>Which metastases are known?</b>";
	private final String ENG_TAB3_sirt_j = "Which metastases are known?";
	private final String ENG_TAB2_sirt_k = "Lung";
	private final String ENG_TAB2_sirt_l = "Liver";
	private final String ENG_TAB2_sirt_m = "Bones";
	private final String ENG_TAB2_sirt_n = "Intestine";
	private final String ENG_TAB2_sirt_o = "Head";
	private final String ENG_TAB2_sirt_p = "<b>Therapies</b>";
	private final String ENG_TAB3_sirt_p = "Therapies";
	private final String ENG_TAB2_sirt_q = "Therapies";
	private final String ENG_TAB2_sirt_r = "Other tumors";

	// Prostata-MRT
	private final String ENG_TAB2_prostate_a = "<h2>Prostate - MRT</h2>";
	private final String ENG_TAB2_prostate_b = "<h3>Dear patient,<br> to analyse your medical examination in the best way, we need "
			+ "comprehensive information about your disease. Therefore we ask you to <br> fill in and send us the following form. "
			+ "The data is transferred in encrypted form and cannot be seen by third parties.<br> Your data will be kept strictly "
			+ "confidential. Thanks!</p> Please fill in the following form.<br> Note: Fields marked with an asterisk (*) are mandatory!</h3><br><br>";

	private final String ENG_TAB2_prostate_c = "<b>Diagnosis*</b>";
	private final String ENG_TAB3_prostate_c = "Diagnosis"; // tab3
	private final String ENG_TAB2_prostate_d = "Diagnosis";
	private final String ENG_TAB2_prostate_e = "No diagnosis";
	private final String ENG_TAB2_prostate_f = "Prostatitis";
	private final String ENG_TAB2_prostate_g = "Prostatic carcinoma";
	private final String ENG_TAB2_prostate_h = "Current PSA value";
	private final String ENG_TAB2_prostate_i = "<b>Are biopsies (specimens) already taken?*</b>";
	private final String ENG_TAB3_prostate_i = "Are biopsies (specimens) already taken?"; // tab3
	private final String ENG_TAB2_prostate_j = "Biopsies";
	private final String ENG_TAB2_prostate_k = "No biopsies taken";
	private final String ENG_TAB2_prostate_l = "Negative Biopsy";
	private final String ENG_TAB2_prostate_m = "Positive Biopsy";
	private final String ENG_TAB2_prostate_n = "Number of negative biopsies";
	private final String ENG_TAB2_prostate_o = "more than 4";
	private final String ENG_TAB2_prostate_p = "Gleason score";
	private final String ENG_TAB2_prostate_q = "Last biopsy";
	private final String ENG_TAB2_prostate_r = "<b>Therapies</b>";
	private final String ENG_TAB3_prostate_r = "Therapies"; // tab3
	private final String ENG_TAB2_prostate_s = "Prostatectomy (operation)";
	private final String ENG_TAB2_prostate_t = "Radiotherapy";
	private final String ENG_TAB2_prostate_u = "Hormone therapy";
	private final String ENG_TAB2_prostate_v = "Date";
	private final String ENG_TAB2_prostate_w = "Period of time";
	private final String ENG_TAB2_prostate_x = "from";
	private final String ENG_TAB2_prostate_y = "to";

	// ---------------

	// ausgewählte Sprache (default=DE)
	public String STATUS_1 = DE_STATUS_1;
	public String STATUS_2 = DE_STATUS_2;
	public String STATUS_3 = DE_STATUS_3;
	// public String STATUS_4=DE_STATUS_4;

	public String STATUS_ERROR_1 = DE_STATUS_ERROR_1;

	public String TAB0_HEADER = DE_TAB0_HEADER;
	public String TAB0_a = DE_TAB0_a;
	public String TAB0_b = DE_TAB0_b;
	public String TAB0_c = DE_TAB0_c;
	public String TAB0_d = DE_TAB0_d;
	public String TAB0_e = DE_TAB0_e;
	public String TAB0_f = DE_TAB0_f;
	public String TAB0_g = DE_TAB0_g;
	public String TAB0_h = DE_TAB0_h;
	public String TAB0_i = DE_TAB0_i;
	public String TAB0_j = DE_TAB0_j;
	public String TAB0_k = DE_TAB0_k;
	public String TAB1_a = DE_TAB1_a;
	public String TAB1_b = DE_TAB1_b;
	public String TAB1_c = DE_TAB1_c;
	public String TAB1_d = DE_TAB1_d;
	public String TAB1_e = DE_TAB1_e;
	public String TAB1_f = DE_TAB1_f;
	public String TAB1_g = DE_TAB1_g;
	public String TAB1_h = DE_TAB1_h;
	public String TAB1_i = DE_TAB1_i;
	public String TAB1_j = DE_TAB1_j;
	public String TAB1_k = DE_TAB1_k;
	public String TAB1_l = DE_TAB1_l;
	public String TAB1_m = DE_TAB1_m;
	public String TAB1_n = DE_TAB1_n;

	public String TAB4_a = DE_TAB4_a;
	public String TAB4_b = DE_TAB4_b;
	public String TAB4_c = DE_TAB4_c;
	public String TAB4_d = DE_TAB4_d;

	public String TAB3_a = DE_TAB3_a;
	public String TAB3_b = DE_TAB3_b;
	public String TAB3_c = DE_TAB3_c;

	public String TAB2_a = DE_TAB2_a;
	public String TAB2_b = DE_TAB2_b;
	public String TAB2_c = DE_TAB2_c;
	public String TAB2_d = DE_TAB2_d;

	public String TAB1_HEADER = DE_TAB1_HEADER;
	public String TAB2_HEADER = DE_TAB2_HEADER;
	public String TAB3_HEADER = DE_TAB3_HEADER;
	public String TAB4_HEADER = DE_TAB4_HEADER;
	public String BACK_BUTTON = DE_BACK_BUTTON;
	public String NEXT_BUTTON_a = DE_NEXT_BUTTON_a;
	public String NEXT_BUTTON_b = DE_NEXT_BUTTON_b;

	// SIRT
	public String TAB2_sirt_a = DE_TAB2_sirt_a;
	public String TAB2_sirt_b = DE_TAB2_sirt_b;
	public String TAB2_sirt_c = DE_TAB2_sirt_c;
	public String TAB3_sirt_c = DE_TAB3_sirt_c;
	public String TAB2_sirt_d = DE_TAB2_sirt_d;
	public String TAB2_sirt_e = DE_TAB2_sirt_e;
	public String TAB2_sirt_f = DE_TAB2_sirt_f;
	public String TAB2_sirt_g = DE_TAB2_sirt_g;
	public String TAB2_sirt_h = DE_TAB2_sirt_h;
	public String TAB2_sirt_i = DE_TAB2_sirt_i;
	public String TAB2_sirt_j = DE_TAB2_sirt_j;
	public String TAB3_sirt_j = DE_TAB3_sirt_j;
	public String TAB2_sirt_k = DE_TAB2_sirt_k;
	public String TAB2_sirt_l = DE_TAB2_sirt_l;
	public String TAB2_sirt_m = DE_TAB2_sirt_m;
	public String TAB2_sirt_n = DE_TAB2_sirt_n;
	public String TAB2_sirt_o = DE_TAB2_sirt_o;
	public String TAB2_sirt_p = DE_TAB2_sirt_p;
	public String TAB3_sirt_p = DE_TAB3_sirt_p;
	public String TAB2_sirt_q = DE_TAB2_sirt_q;
	public String TAB2_sirt_r = DE_TAB2_sirt_r;

	// Prostata-MRT
	public String TAB2_prostate_a = DE_TAB2_prostate_a;
	public String TAB2_prostate_b = DE_TAB2_prostate_b;
	public String TAB2_prostate_c = DE_TAB2_prostate_c;
	public String TAB3_prostate_c = DE_TAB3_prostate_c; // tab3
	public String TAB2_prostate_d = DE_TAB2_prostate_d;
	public String TAB2_prostate_e = DE_TAB2_prostate_e;
	public String TAB2_prostate_f = DE_TAB2_prostate_f;
	public String TAB2_prostate_g = DE_TAB2_prostate_g;
	public String TAB2_prostate_h = DE_TAB2_prostate_h;
	public String TAB2_prostate_i = DE_TAB2_prostate_i;
	public String TAB3_prostate_i = DE_TAB3_prostate_i; // tab3
	public String TAB2_prostate_j = DE_TAB2_prostate_j;
	public String TAB2_prostate_k = DE_TAB2_prostate_k;
	public String TAB2_prostate_l = DE_TAB2_prostate_l;
	public String TAB2_prostate_m = DE_TAB2_prostate_m;
	public String TAB2_prostate_n = DE_TAB2_prostate_n;
	public String TAB2_prostate_o = DE_TAB2_prostate_o;
	public String TAB2_prostate_p = DE_TAB2_prostate_p;
	public String TAB2_prostate_q = DE_TAB2_prostate_q;
	public String TAB2_prostate_r = DE_TAB2_prostate_r;
	public String TAB3_prostate_r = DE_TAB3_prostate_r; // tab3
	public String TAB2_prostate_s = DE_TAB2_prostate_s;
	public String TAB2_prostate_t = DE_TAB2_prostate_t;
	public String TAB2_prostate_u = DE_TAB2_prostate_u;
	public String TAB2_prostate_v = DE_TAB2_prostate_v;
	public String TAB2_prostate_w = DE_TAB2_prostate_w;
	public String TAB2_prostate_x = DE_TAB2_prostate_x;
	public String TAB2_prostate_y = DE_TAB2_prostate_y;

	public static StringInternationalizer getInstance() {
		if (instance == null) {
			instance = new StringInternationalizer();
		}
		return instance;
	}

	/**
	 * Sets the default language.
	 * 
	 * @param choosenLanguage
	 */
	public void setDefaultLanguage(String choosenLanguage) {
		if (choosenLanguage == LANGUAGE_DE) {

			LANGUAGE_CURRENT = LANGUAGE_DE;

			STATUS_1 = DE_STATUS_1;
			STATUS_2 = DE_STATUS_2;
			STATUS_3 = DE_STATUS_3;
			// STATUS_4=DE_STATUS_4;
			STATUS_ERROR_1 = DE_STATUS_ERROR_1;

			TAB0_HEADER = DE_TAB0_HEADER;
			TAB0_a = DE_TAB0_a;
			TAB0_b = DE_TAB0_b;
			TAB0_c = DE_TAB0_c;
			TAB0_d = DE_TAB0_d;
			TAB0_e = DE_TAB0_e;
			TAB0_f = DE_TAB0_f;
			TAB0_g = DE_TAB0_g;
			TAB0_h = DE_TAB0_h;
			TAB0_i = DE_TAB0_i;
			TAB0_j = DE_TAB0_j;
			TAB0_k = DE_TAB0_k;
			TAB1_a = DE_TAB1_a;
			TAB1_b = DE_TAB1_b;
			TAB1_c = DE_TAB1_c;
			TAB1_d = DE_TAB1_d;
			TAB1_e = DE_TAB1_e;
			TAB1_f = DE_TAB1_f;
			TAB1_g = DE_TAB1_g;
			TAB1_i = DE_TAB1_i;
			TAB1_h = DE_TAB1_h;
			TAB1_j = DE_TAB1_j;
			TAB1_k = DE_TAB1_k;
			TAB1_l = DE_TAB1_l;
			TAB1_m = DE_TAB1_m;
			TAB1_n = DE_TAB1_n;

			TAB3_a = DE_TAB3_a;
			TAB3_b = DE_TAB3_b;
			TAB3_c = DE_TAB3_c;

			TAB4_a = DE_TAB4_a;
			TAB4_b = DE_TAB4_b;
			TAB4_c = DE_TAB4_c;
			TAB4_d = DE_TAB4_d;

			TAB2_a = DE_TAB2_a;
			TAB2_b = DE_TAB2_b;
			TAB2_c = DE_TAB2_c;
			TAB2_d = DE_TAB2_d;

			TAB1_HEADER = DE_TAB1_HEADER;
			TAB2_HEADER = DE_TAB2_HEADER;
			TAB3_HEADER = DE_TAB3_HEADER;
			TAB4_HEADER = DE_TAB4_HEADER;
			BACK_BUTTON = DE_BACK_BUTTON;
			NEXT_BUTTON_a = DE_NEXT_BUTTON_a;
			NEXT_BUTTON_b = DE_NEXT_BUTTON_b;

			// SIRT
			TAB2_sirt_a = DE_TAB2_sirt_a;
			TAB2_sirt_b = DE_TAB2_sirt_b;
			TAB2_sirt_c = DE_TAB2_sirt_c;
			TAB3_sirt_c = DE_TAB3_sirt_c;
			TAB2_sirt_d = DE_TAB2_sirt_d;
			TAB2_sirt_e = DE_TAB2_sirt_e;
			TAB2_sirt_f = DE_TAB2_sirt_f;
			TAB2_sirt_g = DE_TAB2_sirt_g;
			TAB2_sirt_h = DE_TAB2_sirt_h;
			TAB2_sirt_i = DE_TAB2_sirt_i;
			TAB2_sirt_j = DE_TAB2_sirt_j;
			TAB3_sirt_j = DE_TAB3_sirt_j;
			TAB2_sirt_k = DE_TAB2_sirt_k;
			TAB2_sirt_l = DE_TAB2_sirt_l;
			TAB2_sirt_m = DE_TAB2_sirt_m;
			TAB2_sirt_n = DE_TAB2_sirt_n;
			TAB2_sirt_o = DE_TAB2_sirt_o;
			TAB2_sirt_p = DE_TAB2_sirt_p;
			TAB3_sirt_p = DE_TAB3_sirt_p;
			TAB2_sirt_q = DE_TAB2_sirt_q;
			TAB2_sirt_r = DE_TAB2_sirt_r;

			// Prostata-MRT
			TAB2_prostate_a = DE_TAB2_prostate_a;
			TAB2_prostate_b = DE_TAB2_prostate_b;
			TAB2_prostate_c = DE_TAB2_prostate_c;
			TAB3_prostate_c = DE_TAB3_prostate_c; // tab3
			TAB2_prostate_d = DE_TAB2_prostate_d;
			TAB2_prostate_e = DE_TAB2_prostate_e;
			TAB2_prostate_f = DE_TAB2_prostate_f;
			TAB2_prostate_g = DE_TAB2_prostate_g;
			TAB2_prostate_h = DE_TAB2_prostate_h;
			TAB2_prostate_i = DE_TAB2_prostate_i;
			TAB3_prostate_i = DE_TAB3_prostate_i;
			TAB2_prostate_j = DE_TAB2_prostate_j;
			TAB2_prostate_k = DE_TAB2_prostate_k;
			TAB2_prostate_l = DE_TAB2_prostate_l;
			TAB2_prostate_m = DE_TAB2_prostate_m;
			TAB2_prostate_n = DE_TAB2_prostate_n;
			TAB2_prostate_o = DE_TAB2_prostate_o;
			TAB2_prostate_p = DE_TAB2_prostate_p;
			TAB2_prostate_q = DE_TAB2_prostate_q;
			TAB2_prostate_r = DE_TAB2_prostate_r;
			TAB3_prostate_r = DE_TAB3_prostate_r;
			TAB2_prostate_s = DE_TAB2_prostate_s;
			TAB2_prostate_t = DE_TAB2_prostate_t;
			TAB2_prostate_u = DE_TAB2_prostate_u;
			TAB2_prostate_v = DE_TAB2_prostate_v;
			TAB2_prostate_w = DE_TAB2_prostate_w;
			TAB2_prostate_x = DE_TAB2_prostate_x;
			TAB2_prostate_y = DE_TAB2_prostate_y;
		}

		else if (choosenLanguage == LANGUAGE_ENG) {
			LANGUAGE_CURRENT = LANGUAGE_ENG;

			STATUS_1 = ENG_STATUS_1;
			STATUS_2 = ENG_STATUS_2;
			STATUS_3 = ENG_STATUS_3;
			// STATUS_4=ENG_STATUS_4;
			STATUS_ERROR_1 = ENG_STATUS_ERROR_1;

			TAB0_HEADER = ENG_TAB0_HEADER;
			TAB0_a = ENG_TAB0_a;
			TAB0_b = ENG_TAB0_b;
			TAB0_c = ENG_TAB0_c;
			TAB0_d = ENG_TAB0_d;
			TAB0_e = ENG_TAB0_e;
			TAB0_f = ENG_TAB0_f;
			TAB0_g = ENG_TAB0_g;
			TAB0_h = ENG_TAB0_h;
			TAB0_i = ENG_TAB0_i;
			TAB0_j = ENG_TAB0_j;
			TAB0_k = ENG_TAB0_k;
			TAB1_a = ENG_TAB1_a;
			TAB1_b = ENG_TAB1_b;
			TAB1_c = ENG_TAB1_c;
			TAB1_d = ENG_TAB1_d;
			TAB1_e = ENG_TAB1_e;
			TAB1_f = ENG_TAB1_f;
			TAB1_g = ENG_TAB1_g;
			TAB1_h = ENG_TAB1_h;
			TAB1_i = ENG_TAB1_i;
			TAB1_j = ENG_TAB1_j;
			TAB1_k = ENG_TAB1_k;
			TAB1_l = ENG_TAB1_l;
			TAB1_m = ENG_TAB1_m;
			TAB1_n = ENG_TAB1_n;

			TAB3_a = ENG_TAB3_a;
			TAB3_b = ENG_TAB3_b;
			TAB3_c = ENG_TAB3_c;

			TAB4_a = ENG_TAB4_a;
			TAB4_b = ENG_TAB4_b;
			TAB4_c = ENG_TAB4_c;
			TAB4_d = ENG_TAB4_d;

			TAB2_a = ENG_TAB2_a;
			TAB2_b = ENG_TAB2_b;
			TAB2_c = ENG_TAB2_c;
			TAB2_d = ENG_TAB2_d;

			TAB1_HEADER = ENG_TAB1_HEADER;
			TAB2_HEADER = ENG_TAB2_HEADER;
			TAB3_HEADER = ENG_TAB3_HEADER;
			TAB4_HEADER = ENG_TAB4_HEADER;
			BACK_BUTTON = ENG_BACK_BUTTON;
			NEXT_BUTTON_a = ENG_NEXT_BUTTON_a;
			NEXT_BUTTON_b = ENG_NEXT_BUTTON_b;

			// SIRT eng
			TAB2_sirt_a = ENG_TAB2_sirt_a;
			TAB2_sirt_b = ENG_TAB2_sirt_b;
			TAB2_sirt_c = ENG_TAB2_sirt_c;
			TAB3_sirt_c = ENG_TAB3_sirt_c;
			TAB2_sirt_d = ENG_TAB2_sirt_d;
			TAB2_sirt_e = ENG_TAB2_sirt_e;
			TAB2_sirt_f = ENG_TAB2_sirt_f;
			TAB2_sirt_g = ENG_TAB2_sirt_g;
			TAB2_sirt_h = ENG_TAB2_sirt_h;
			TAB2_sirt_i = ENG_TAB2_sirt_i;
			TAB2_sirt_j = ENG_TAB2_sirt_j;
			TAB3_sirt_j = ENG_TAB3_sirt_j;
			TAB2_sirt_k = ENG_TAB2_sirt_k;
			TAB2_sirt_l = ENG_TAB2_sirt_l;
			TAB2_sirt_m = ENG_TAB2_sirt_m;
			TAB2_sirt_n = ENG_TAB2_sirt_n;
			TAB2_sirt_o = ENG_TAB2_sirt_o;
			TAB2_sirt_p = ENG_TAB2_sirt_p;
			TAB3_sirt_p = ENG_TAB3_sirt_p;
			TAB2_sirt_q = ENG_TAB2_sirt_q;
			TAB2_sirt_r = ENG_TAB2_sirt_r;

			// Prostata-MRT eng
			TAB2_prostate_a = ENG_TAB2_prostate_a;
			TAB2_prostate_b = ENG_TAB2_prostate_b;
			TAB2_prostate_c = ENG_TAB2_prostate_c;
			TAB3_prostate_c = ENG_TAB3_prostate_c;
			TAB2_prostate_d = ENG_TAB2_prostate_d;
			TAB2_prostate_e = ENG_TAB2_prostate_e;
			TAB2_prostate_f = ENG_TAB2_prostate_f;
			TAB2_prostate_g = ENG_TAB2_prostate_g;
			TAB2_prostate_h = ENG_TAB2_prostate_h;
			TAB2_prostate_i = ENG_TAB2_prostate_i;
			TAB3_prostate_i = ENG_TAB3_prostate_i;
			TAB2_prostate_j = ENG_TAB2_prostate_j;
			TAB2_prostate_k = ENG_TAB2_prostate_k;
			TAB2_prostate_l = ENG_TAB2_prostate_l;
			TAB2_prostate_m = ENG_TAB2_prostate_m;
			TAB2_prostate_n = ENG_TAB2_prostate_n;
			TAB2_prostate_o = ENG_TAB2_prostate_o;
			TAB2_prostate_p = ENG_TAB2_prostate_p;
			TAB2_prostate_q = ENG_TAB2_prostate_q;
			TAB2_prostate_r = ENG_TAB2_prostate_r;
			TAB3_prostate_r = ENG_TAB3_prostate_r;
			TAB2_prostate_s = ENG_TAB2_prostate_s;
			TAB2_prostate_t = ENG_TAB2_prostate_t;
			TAB2_prostate_u = ENG_TAB2_prostate_u;
			TAB2_prostate_v = ENG_TAB2_prostate_v;
			TAB2_prostate_w = ENG_TAB2_prostate_w;
			TAB2_prostate_x = ENG_TAB2_prostate_x;
			TAB2_prostate_y = ENG_TAB2_prostate_y;

		}
	}

	/**
	 * Gets the current set language.
	 * 
	 * @return current language
	 */
	public String getCurrentLanguage() {
		return LANGUAGE_CURRENT;
	}
}
