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
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

/**
 * 
 * Textual Descrpitions for prostata
 * 
 * @author devmis
 *
 */
public class TextualDescription_prostate extends Tab implements ITextualTab {

	private VLayout layout0 = new VLayout();
	private HLayout layout1 = new HLayout();
	private VLayout layout2 = new VLayout();
	private HeaderItem header = new HeaderItem();
	public TextItem lastname = new TextItem();
	protected TextItem forename = new TextItem();
	protected DateItem orderDate = new DateItem();
	protected TextItem weight = new TextItem();
	protected TextItem height = new TextItem();
	protected ComboBoxItem diagnose = new ComboBoxItem();
	protected TextItem psa = new TextItem();
	protected ComboBoxItem biopsy = new ComboBoxItem();;
	protected ComboBoxItem neg_biopsy = new ComboBoxItem();;
	protected TextItem gleason = new TextItem();
	protected DateItem date_biopsy = new DateItem();
	protected CheckboxItem prosta = new CheckboxItem();
	protected CheckboxItem radiotherapy = new CheckboxItem();
	protected CheckboxItem hormon = new CheckboxItem();
	protected DateItem date_prosta = new DateItem();
	protected DateItem date_radio1 = new DateItem();
	protected DateItem date_radio2 = new DateItem();
	protected DateItem date_hormon1 = new DateItem();
	protected DateItem date_hormon2 = new DateItem();
	protected DynamicForm profileForm0 = new DynamicForm();
	protected DynamicForm profileForm1 = new DynamicForm();
	protected DynamicForm profileForm2 = new DynamicForm();
	protected DynamicForm profileForm3 = new DynamicForm();
	protected DynamicForm profileForm4 = new DynamicForm();
	protected DynamicForm profileForm5 = new DynamicForm();
	protected DynamicForm profileForm6 = new DynamicForm();
	private Label info = new Label("");
	private Label question_1 = new Label("");
	private static TextualDescription_prostate instance = null;


	/**
	 * Singleton-Pattern
	 * 
	 * @return an instance of the class
	 */
	public static TextualDescription_prostate getInstance() {

		if (instance == null) {

			instance = new TextualDescription_prostate();
		}
		return instance;
	}

	/**
	 * Constructor of the class
	 * 
	 */
	private TextualDescription_prostate() {

		header.setDefaultValue(S.TAB2_prostate_a);
		info.setContents(S.TAB2_prostate_b);

		// profileForm5
		profileForm5.setFields(new FormItem[] { header });

		lastname.setTitle(S.TAB0_b);
		lastname.setLength(50);
		lastname.setRequired(true);
		lastname.setKeyPressFilter("[a-z && A-Z && ä-ü && Ä-Ü && - && ´ ` ^]");

		forename.setTitle(S.TAB0_c);
		forename.setLength(50);
		forename.setRequired(true);
		forename.setKeyPressFilter("[a-z && A-Z && ä-ü && Ä-Ü && - &&´ ` ^]");

		// orderDate.setTitle(S.TAB0_g);
		// orderDate.setStartDate(new Date(0, 1, 1));
		// orderDate.setEndDate(new Date());
		// orderDate.setRequired(true);

		orderDate.setTitle(S.TAB0_g);
		orderDate.setUseTextField(true);
		orderDate.setRequired(true);
		orderDate.setHint(S.TAB2_d);

		weight.setTitle(S.TAB2_a);
		weight.setLength(3);
		weight.setHint("kg");
		weight.setKeyPressFilter("[0-9]");
		height.setTitle(S.TAB2_b);
		height.setLength(3);
		height.setHint("cm");
		height.setKeyPressFilter("[0-9]");

		// profileForm0
		profileForm0.setFields(new FormItem[] { lastname, forename, orderDate,
				weight, height });
		profileForm0.setIsGroup(true);
		profileForm0.setGroupTitle(S.TAB0_a);
		profileForm0.setPadding(30);

		question_1.setContents(S.TAB2_prostate_i);
		question_1.getContents();

		diagnose.setTitle(S.TAB2_prostate_d);
		diagnose.setType("comboBox");
		diagnose.setValueMap(S.TAB2_prostate_e, S.TAB2_prostate_f,
				S.TAB2_prostate_g);
		diagnose.setRequired(true);

		psa.setTitle(S.TAB2_prostate_h);
		psa.setLength(4);
		psa.setHint("µg/l");
		psa.setRequired(true);
		psa.setKeyPressFilter("[0-9.,]");

		profileForm6.setFields(new FormItem[] { diagnose, psa });
		profileForm6.setIsGroup(true);
		profileForm6.setGroupTitle(S.TAB2_prostate_c);
		profileForm6.setPadding(30);

		biopsy.setTitle(S.TAB2_prostate_j);
		biopsy.setType("comboBox");
		biopsy.setValueMap(S.TAB2_prostate_k, S.TAB2_prostate_l,
				S.TAB2_prostate_m);
		biopsy.setRequired(true);

		neg_biopsy.setTitle(S.TAB2_prostate_n);
		neg_biopsy.setType("comboBox");
		neg_biopsy.setValueMap("---", "1", "2", "3", S.TAB2_prostate_o);
		neg_biopsy.setRequired(true);

		gleason.setTitle(S.TAB2_prostate_p);
		gleason.setLength(2);
		gleason.setRequired(true);
		gleason.setKeyPressFilter("[0-9]");

		// date_biopsy.setTitle(S.TAB2_prostate_q);
		// date_biopsy.setRequired(true);

		date_biopsy.setTitle(S.TAB2_prostate_q);
		date_biopsy.setUseTextField(true);
		date_biopsy.setRequired(true);
		date_biopsy.setHint(S.TAB2_d);

		// profileForm1
		profileForm1.setFields(new FormItem[] { biopsy, neg_biopsy, gleason,
				date_biopsy });
		profileForm1.setIsGroup(true);
		profileForm1.setGroupTitle(S.TAB2_prostate_i);
		profileForm1.setPadding(30);

		prosta.setTitle(S.TAB2_prostate_s);
		prosta.redraw();
		prosta.setName("prosta");
		prosta.setRedrawOnChange(true);
		prosta.setValue(false);

		date_prosta.setTitle(S.TAB2_prostate_v);
		date_prosta.setVisible(false);
		date_prosta.setName("date_prosta");
		date_prosta.setRequired(true);
		date_prosta.setVisible(false);
		date_prosta.setUseTextField(true);
		// date_prosta.setHint(S.TAB2_d);

		date_prosta.setShowIfCondition(new FormItemIfFunction() {
			public boolean execute(FormItem item, Object value,
					DynamicForm profileForm2) {
				return (Boolean) profileForm2.getValue("prosta");
			}
		});

		// profileForm2
		profileForm2.setFields(new FormItem[] { prosta, date_prosta });
		profileForm2.setPadding(40);
		profileForm2.draw();
		radiotherapy.setName("radiotherapy");
		radiotherapy.setTitle(S.TAB2_prostate_t);
		radiotherapy.setRedrawOnChange(true);
		radiotherapy.setValue(false);

		date_radio1.setName("date_radio1");
		date_radio1.setTitle(S.TAB2_prostate_x);
		date_radio1.setRequired(true);
		date_radio1.setVisible(false);
		date_radio1.setUseTextField(true);
		// date_radio1.setHint(S.TAB2_d);

		date_radio2.setName("date_radio2");
		date_radio2.setTitle(S.TAB2_prostate_y);
		date_radio2.setRequired(true);
		date_radio2.setVisible(false);
		date_radio2.setUseTextField(true);
		// date_radio2.setHint(S.TAB2_d);

		date_radio1.setShowIfCondition(new FormItemIfFunction() {
			public boolean execute(FormItem item, Object value,
					DynamicForm profileForm3) {
				return (Boolean) profileForm3.getValue("radiotherapy");
			}
		});
		date_radio2.setShowIfCondition(new FormItemIfFunction() {
			public boolean execute(FormItem item, Object value,
					DynamicForm profileForm3) {
				return (Boolean) profileForm3.getValue("radiotherapy");
			}
		});

		// profileForm3
		profileForm3.setFields(new FormItem[] { radiotherapy, date_radio1,
				date_radio2 });
		profileForm3.setPadding(40);

		hormon.setName("hormon");
		hormon.setTitle(S.TAB2_prostate_u);
		hormon.setRedrawOnChange(true);
		hormon.setValue(false);

		date_hormon1.setName("date_hormon1");
		date_hormon1.setTitle(S.TAB2_prostate_x);

		date_hormon1.setVisible(false);
		date_hormon1.setUseTextField(true);
		// date_hormon1.setHint(S.TAB2_d);
		date_hormon1.setRequired(true);

		date_hormon2.setName("date_hormon2");
		date_hormon2.setTitle(S.TAB2_prostate_y);
		date_hormon2.setRequired(true);
		date_hormon2.setVisible(false);
		date_hormon2.setUseTextField(true);
		// date_hormon1.setHint(S.TAB2_d);

		date_hormon1.setShowIfCondition(new FormItemIfFunction() {
			public boolean execute(FormItem item, Object value,
					DynamicForm profileForm4) {
				return (Boolean) profileForm4.getValue("hormon");
			}
		});

		date_hormon2.setShowIfCondition(new FormItemIfFunction() {
			public boolean execute(FormItem item, Object value,
					DynamicForm profileForm4) {
				return (Boolean) profileForm4.getValue("hormon");
			}
		});

		// profileForm4
		profileForm4.setFields(new FormItem[] { hormon, date_hormon1,
				date_hormon2 });
		profileForm4.setPadding(40);

		LayoutSpacer ls = new LayoutSpacer();
		ls.setWidth(40);

		layout0.addMember(profileForm5);
		layout0.addMember(info);
		layout0.addMember(profileForm0);
		layout0.addMember(profileForm6);
		layout0.addMember(profileForm1);
		layout0.setBackgroundColor("F5F5F5");

		layout1.addMember(profileForm2);
		layout1.addMember(ls);
		layout1.addMember(profileForm3);
		layout1.addMember(ls);
		layout1.addMember(profileForm4);
		layout1.setIsGroup(true);
		layout1.setGroupTitle(S.TAB2_prostate_r);
		layout1.setBackgroundColor("F5F5F5");

		layout2.setPadding(20);
		layout2.addMember(layout0);
		layout2.addMember(layout1);

		this.setPane(layout0);
		this.setPane(layout1);
		this.setPane(layout2);

	}

	/**
	 * Sets Strings
	 * 
	 */
	public void setStrings() {

		header.setDefaultValue(S.TAB2_prostate_a);
		info.setContents(S.TAB2_prostate_b);
		lastname.setTitle(S.TAB0_b);
		// lastname.redraw();
		forename.setTitle(S.TAB0_c);
		orderDate.setTitle(S.TAB0_g);
		weight.setTitle(S.TAB2_a);
		height.setTitle(S.TAB2_b);
		profileForm0.setGroupTitle(S.TAB0_a);
		profileForm0.redraw();
		question_1.setContents(S.TAB2_prostate_i);
		diagnose.setTitle(S.TAB2_prostate_d);
		diagnose.setValueMap(S.TAB2_prostate_e, S.TAB2_prostate_f,
				S.TAB2_prostate_g);
		psa.setTitle(S.TAB2_prostate_h);
		profileForm6.setGroupTitle(S.TAB2_prostate_c);
		profileForm6.redraw();
		biopsy.setTitle(S.TAB2_prostate_j);
		biopsy.setValueMap(S.TAB2_prostate_k, S.TAB2_prostate_l,
				S.TAB2_prostate_m);
		neg_biopsy.setTitle(S.TAB2_prostate_n);
		neg_biopsy.setValueMap("---", "1", "2", "3", S.TAB2_prostate_o);
		gleason.setTitle(S.TAB2_prostate_p);
		date_biopsy.setTitle(S.TAB2_prostate_q);
		profileForm1.setGroupTitle(S.TAB2_prostate_i);
		profileForm1.redraw();
		prosta.setTitle(S.TAB2_prostate_s);
		date_prosta.setTitle(S.TAB2_prostate_v);
		radiotherapy.setTitle(S.TAB2_prostate_t);
		date_radio1.setTitle(S.TAB2_prostate_x);
		date_radio2.setTitle(S.TAB2_prostate_y);
		hormon.setTitle(S.TAB2_prostate_u);
		date_hormon1.setTitle(S.TAB2_prostate_x);
		date_hormon2.setTitle(S.TAB2_prostate_y);
		layout1.setGroupTitle(S.TAB2_prostate_r);

	}

	/**
	 * Checks if Profile Forms are valid
	 * @return true; if Profile Forms 0,1,6,2,3 and 4 are valid
	 * 			false; if Profile Forms aren't valid
	 * 
	 */
	public boolean isValid() {
		return profileForm0.validate() && profileForm1.validate()
				&& profileForm6.validate() && profileForm2.validate()
				&& profileForm3.validate() && profileForm4.validate();
	}

	/**
	 * 
	 * Shows the Textual Description
	 */
	public void showTextualDescription() {
		String mp_lastname = (String) lastname.getValue();
		String mp_forename = (String) forename.getValue();
		Object mp_orderDate = orderDate.getValue();

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
		String mp_diagnose = (String) diagnose.getValue();

		String mp_psa = (String) psa.getValue();
		String mp_biopsy = (String) biopsy.getValue();
		String mp_neg_biopsy = (String) neg_biopsy.getValue();
		String mp_gleason = (String) gleason.getValue();
		Object mp_date_biopsy = date_biopsy.getValue();
		Object mp_date_prosta = date_prosta.getValue();
		if (mp_date_prosta == null) {
			mp_date_prosta = "---";
		}
		Object mp_date_radio1 = date_radio1.getValue();
		if (mp_date_radio1 == null) {
			mp_date_radio1 = "---";
		}
		Object mp_date_radio2 = date_radio2.getValue();
		if (mp_date_radio2 == null) {
			mp_date_radio2 = "---";
		}
		Object mp_date_hormon1 = date_hormon1.getValue();
		if (mp_date_hormon1 == null) {
			mp_date_hormon1 = "---";
		}
		Object mp_date_hormon2 = date_hormon2.getValue();
		if (mp_date_hormon2 == null) {
			mp_date_hormon2 = "---";
		}

		Sending.getInstance().textual_description.setValue(S.TAB0_b + ": "
				+ mp_lastname + "\n" + S.TAB0_c + ": " + mp_forename + "\n"
				+ S.TAB0_g + ": " + mp_orderDate + "\n" + S.TAB2_a + ": "
				+ mp_weight + "\n" + S.TAB2_b + ": " + mp_height + "\n" + "\n"
				+ "\n" + "1.) " + S.TAB3_prostate_c + "\n" + "   - "
				+ S.TAB2_prostate_d + ": " + mp_diagnose + "\n" + "   - "
				+ S.TAB2_prostate_h + ": " + mp_psa + " µg/l" + "\n" + "\n"
				+ "2.) " + S.TAB3_prostate_i + "\n" + "   - "
				+ S.TAB2_prostate_j + ": " + mp_biopsy + "\n" + "   - "
				+ S.TAB2_prostate_n + ": " + mp_neg_biopsy + "\n" + "   - "
				+ S.TAB2_prostate_p + ": " + mp_gleason + "\n" + "   - "
				+ S.TAB2_prostate_j + ": " + mp_biopsy + "\n" + "   - "
				+ S.TAB2_prostate_q + ": " + mp_date_biopsy + "\n" + "\n"
				+ "3.) " + S.TAB3_prostate_r + "\n" + "   - "
				+ S.TAB2_prostate_s + ":" + "\n" + "     " + mp_date_prosta
				+ "\n" + "\n" + "   - " + S.TAB2_prostate_t + ":" + "\n"
				+ "     " + S.TAB2_prostate_x + ": " + mp_date_radio1 + "\n"
				+ "     " + S.TAB2_prostate_y + ":  " + mp_date_radio2 + "\n"
				+ "\n" + "   - " + S.TAB2_prostate_u + ":" + "\n" + "     "
				+ S.TAB2_prostate_x + ": " + mp_date_hormon1 + "\n" + "     "
				+ S.TAB2_prostate_y + ":  " + mp_date_hormon2 + "\n");
	}
}
