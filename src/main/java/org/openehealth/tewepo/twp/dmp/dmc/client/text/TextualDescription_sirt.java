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
 * Textual Descrpitions for SIRT (selective internal radiotherapy) 
 * 
 * @author devmis
 *
 */
public class TextualDescription_sirt extends Tab implements ITextualTab {

	private HLayout layout0 = new HLayout();
	private VLayout layout1 = new VLayout();
	private HeaderItem header = new HeaderItem();
	public TextItem lastname = new TextItem();
	protected TextItem forename = new TextItem();
	protected RadioGroupItem sex = new RadioGroupItem();
	protected DateItem orderDate = new DateItem();
	protected TextItem weight = new TextItem();
	protected TextItem height = new TextItem();
	protected TextItem other_tumor = new TextItem();
	protected CheckboxItem tumor1 = new CheckboxItem();
	protected CheckboxItem tumor2 = new CheckboxItem();
	protected CheckboxItem tumor3 = new CheckboxItem();
	protected CheckboxItem tumor4 = new CheckboxItem();
	protected CheckboxItem tumor5 = new CheckboxItem();
	protected CheckboxItem tumor6 = new CheckboxItem();
	protected CheckboxItem metastase1 = new CheckboxItem();
	protected CheckboxItem metastase2 = new CheckboxItem();
	protected CheckboxItem metastase3 = new CheckboxItem();
	protected CheckboxItem metastase4 = new CheckboxItem();
	protected CheckboxItem metastase5 = new CheckboxItem();
	protected TextAreaItem therapy = new TextAreaItem();
	protected DynamicForm profileForm0 = new DynamicForm();
	protected DynamicForm profileForm1 = new DynamicForm();
	protected DynamicForm profileForm2 = new DynamicForm();
	protected DynamicForm profileForm3 = new DynamicForm();
	protected DynamicForm profileForm4 = new DynamicForm();
	protected Label info = new Label("");

	private static TextualDescription_sirt instance = null;

	/**
	 * Singleton-Pattern
	 * 
	 * @return an instance of the class
	 */
	public static TextualDescription_sirt getInstance() {

		if (instance == null) {

			instance = new TextualDescription_sirt();
		}
		return instance;
	}

	/**
	 * Constructor
	 * 
	 */
	private TextualDescription_sirt() {

		header.setDefaultValue(S.TAB2_sirt_a);
		info.setContents(S.TAB2_sirt_b);

		// profileForm4
		profileForm4.setFields(new FormItem[] { header });

		lastname.setTitle(S.TAB0_b);
		lastname.setLength(50);
		lastname.setRequired(true);
		lastname.setKeyPressFilter("[a-z && A-Z && ä-ü && Ä-Ü && - && ´`^]");

		forename.setTitle(S.TAB0_c);
		forename.setLength(50);
		forename.setRequired(true);
		forename.setKeyPressFilter("[a-z && A-Z && ä-ü && Ä-Ü && - && ´`^]");

		sex.setValueMap(S.TAB0_d, S.TAB0_e);
		sex.setVertical(false);
		sex.setWidth(150);
		sex.setTitle(S.TAB0_f);

		// orderDate.setTitle(S.TAB0_g);
		// orderDate.setStartDate(new Date(0, 1, 1));
		// orderDate.setEndDate(new Date());
		// orderDate.setRequired(true);

		orderDate.setTitle(S.TAB0_g);
		orderDate.setUseTextField(true);
		orderDate.setRequired(true);
		orderDate.setHint(S.TAB2_a);

		weight.setTitle(S.TAB2_a);
		weight.setLength(3);
		weight.setKeyPressFilter("[0-9]");
		weight.setHint("kg");

		height.setTitle(S.TAB2_b);
		height.setLength(3);
		height.setKeyPressFilter("[0-9]");
		height.setHint("cm");

		// profileForm0
		profileForm0.setFields(new FormItem[] { lastname, forename, sex,
				orderDate, weight, height });
		profileForm0.setIsGroup(true);
		profileForm0.setGroupTitle(S.TAB0_a);
		profileForm0.setPadding(30);

		tumor1.setTitle(S.TAB2_sirt_d);
		tumor1.setName(S.TAB2_sirt_d);
		tumor2.setTitle(S.TAB2_sirt_e);
		tumor3.setTitle(S.TAB2_sirt_f);
		tumor4.setTitle(S.TAB2_sirt_g);
		tumor5.setTitle(S.TAB2_sirt_h);
		tumor6.setTitle(S.TAB2_sirt_i);
		tumor6.setName("tumor6");
		tumor6.setRedrawOnChange(true);
		tumor6.setValue(false);

		other_tumor.setName("other_tumor");
		other_tumor.setTitle(S.TAB2_sirt_r);
		other_tumor.setWidth(165);
		other_tumor.setKeyPressFilter("[a-z && A-Z && ä-ü && Ä-Ü && - && ´`]");
		other_tumor.setLength(150);
		other_tumor.setRequired(true);
		other_tumor.setVisible(false);

		other_tumor.setShowIfCondition(new FormItemIfFunction() {
			public boolean execute(FormItem item, Object value,
					DynamicForm profileForm1) {
				return (Boolean) profileForm1.getValue("tumor6");
			}
		});

		// profileForm1
		profileForm1.setFields(new FormItem[] { tumor1, tumor2, tumor3, tumor4,
				tumor5, tumor6, other_tumor });
		profileForm1.setGroupTitle(S.TAB2_sirt_c);
		profileForm1.setPadding(35);

		metastase1.setTitle(S.TAB2_sirt_k);
		metastase2.setTitle(S.TAB2_sirt_l);
		metastase3.setTitle(S.TAB2_sirt_m);
		metastase4.setTitle(S.TAB2_sirt_n);
		metastase5.setTitle(S.TAB2_sirt_o);

		// profileForm2
		profileForm2.setFields(new FormItem[] { metastase1, metastase2,
				metastase3, metastase4, metastase5 });
		profileForm2.setGroupTitle(S.TAB2_sirt_j);
		profileForm2.setPadding(35);

		therapy.setTitle(S.TAB2_sirt_q);
		therapy.setWidth(170);
		therapy.setLength(500);

		// profileForm3
		profileForm3.setFields(new FormItem[] { therapy });
		profileForm3.setGroupTitle(S.TAB2_sirt_p);
		profileForm3.setPadding(35);

		LayoutSpacer ls1 = new LayoutSpacer();
		LayoutSpacer ls2 = new LayoutSpacer();
		LayoutSpacer ls3 = new LayoutSpacer();

		ls1.setWidth(20);
		ls2.setWidth(70);
		ls3.setWidth(150);

		layout0.addMember(ls1);
		layout0.addMember(profileForm1);
		layout0.addMember(ls2);
		layout0.addMember(profileForm2);
		layout0.addMember(ls3);
		layout0.addMember(profileForm3);
		layout0.setBackgroundColor("#FBFCC3");
		layout0.setGroupTitle(S.TAB2_c);
		layout0.setIsGroup(true);
		layout0.setPadding(20);
		layout0.setLayoutTopMargin(45);

		layout1.addMember(profileForm4);
		layout1.addMember(info);
		layout1.addMember(profileForm0);
		layout1.addMember(layout0);
		layout1.setBackgroundColor("#FBFCC3");
		layout1.setPadding(20);

		this.setPane(layout0);
		this.setPane(layout1);
	}

	/**
	 * Sets strings
	 * 
	 */
	public void setStrings() {
		header.setDefaultValue(S.TAB2_sirt_a);
		info.setContents(S.TAB2_sirt_b);
		lastname.setTitle(S.TAB0_b);
		forename.setTitle(S.TAB0_c);
		sex.setValueMap(S.TAB0_d, S.TAB0_e);
		sex.setTitle(S.TAB0_f);
		orderDate.setTitle(S.TAB0_g);
		weight.setTitle(S.TAB2_a);
		height.setTitle(S.TAB2_b);
		profileForm0.setGroupTitle(S.TAB0_a);
		tumor1.setTitle(S.TAB2_sirt_d);
		tumor2.setTitle(S.TAB2_sirt_e);
		tumor3.setTitle(S.TAB2_sirt_f);
		tumor4.setTitle(S.TAB2_sirt_g);
		tumor5.setTitle(S.TAB2_sirt_h);
		tumor6.setTitle(S.TAB2_sirt_i);
		other_tumor.setTitle(S.TAB2_sirt_r);
		profileForm1.setGroupTitle(S.TAB2_sirt_c);
		profileForm1.redraw();
		metastase1.setTitle(S.TAB2_sirt_k);
		metastase2.setTitle(S.TAB2_sirt_l);
		metastase3.setTitle(S.TAB2_sirt_m);
		metastase4.setTitle(S.TAB2_sirt_n);
		metastase5.setTitle(S.TAB2_sirt_o);
		profileForm2.setGroupTitle(S.TAB2_sirt_j);
		profileForm2.redraw();
		therapy.setTitle(S.TAB2_sirt_q);
		profileForm3.setGroupTitle(S.TAB2_sirt_p);
		profileForm3.redraw();
		layout0.setGroupTitle(S.TAB2_c);

	}

	/**
	 * Checks if Profile Forms are valid
	 * @return true; if Profile Forms 0,1,6,2,3 and 4 are valid
	 * 			false; if Profile Forms aren't valid
	 * 
	 */
	public boolean isValid() {
		return profileForm0.validate() && profileForm1.validate();
	}

	/**
	 *  Shows the Textual Description
	 * 
	 */
	public void showTextualDescription() {

		String mp_lastname = (String) lastname.getValue();
		String mp_forename = (String) forename.getValue();
		String mp_sex = (String) sex.getValue();
		if (mp_sex == null) {
			mp_sex = "---";
		}
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
		Boolean mp_tumor1 = tumor1.getValueAsBoolean();
		Boolean mp_tumor2 = tumor2.getValueAsBoolean();
		Boolean mp_tumor3 = tumor3.getValueAsBoolean();
		Boolean mp_tumor4 = tumor4.getValueAsBoolean();
		Boolean mp_tumor5 = tumor5.getValueAsBoolean();

		// Boolean mp_tumor6 = tumor6.getValueAsBoolean();
		String mp_other_tumor = (String) other_tumor.getValue();
		Boolean mp_metastase1 = metastase1.getValueAsBoolean();
		Boolean mp_metastase2 = metastase2.getValueAsBoolean();
		Boolean mp_metastase3 = metastase3.getValueAsBoolean();
		Boolean mp_metastase4 = metastase4.getValueAsBoolean();
		Boolean mp_metastase5 = metastase5.getValueAsBoolean();
		String mp_therapy = (String) therapy.getValue();

		if (mp_therapy == null) {
			mp_therapy = "---";
		}

		Sending.getInstance().textual_description.setValue(S.TAB0_b + ": "
				+ mp_lastname + "\n" + S.TAB0_c + ": " + mp_forename + "\n"
				+ S.TAB0_f + ": " + mp_sex + "\n" + S.TAB0_g + ": "
				+ mp_orderDate + "\n" + S.TAB2_a + ": " + mp_weight + "\n"
				+ S.TAB2_b + ": " + mp_height + "\n" + "\n" + "\n" + "1.) "
				+ S.TAB3_sirt_c + "\n" + "  - " + S.TAB2_sirt_d + ": "
				+ mp_tumor1 + "\n" + "  - " + S.TAB2_sirt_e + ": " + mp_tumor2
				+ "\n" + "  - " + S.TAB2_sirt_f + ": " + mp_tumor3 + "\n"
				+ "  - " + S.TAB2_sirt_g + ": " + mp_tumor4 + "\n" + "  - "
				+ S.TAB2_sirt_h + ": " + mp_tumor5 + "\n" + "  - "
				+ S.TAB2_sirt_i + ": " + mp_other_tumor + "\n" + "\n" + "2.) "
				+ S.TAB3_sirt_j + "\n" + "  - " + S.TAB2_sirt_k + ": "
				+ mp_metastase1 + "\n" + "  - " + S.TAB2_sirt_l + ": "
				+ mp_metastase2 + "\n" + "  - " + S.TAB2_sirt_m + ": "
				+ mp_metastase3 + "\n" + "  - " + S.TAB2_sirt_n + ": "
				+ mp_metastase4 + "\n" + "  - " + S.TAB2_sirt_o + ": "
				+ mp_metastase5 + "\n" + "\n" + "3.) " + S.TAB3_sirt_p + "\n"
				+ "  " + mp_therapy);
	}
}