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

import java.util.Vector;

import org.openehealth.tewepo.twp.dmp.dmc.client.interfaces.ITab;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;


/**
 * An object of this class represents a tab for sending DICOM E-Mails.
 * 
 * @author devmis
 * 
 */
public class Sending extends Tab implements ITab {

	private static Sending instance = null;

	/**
	 * Singleton-Pattern
	 * 
	 * @return an instance of the class
	 */
	public static Sending getInstance() {
		if (instance == null) {
			instance = new Sending();
		}
		return instance;
	}

	// layout
	private VStack left_layout = new VStack();
	private VStack right_layout = new VStack();
	private HStack layoutMain = new HStack();

	// change addresses
	private HStack addressee_layout = new HStack();
	private VStack vstack = new VStack();
	private DynamicForm addressee_form = new DynamicForm();
	private Label mail1 = new Label("");
	private Label mail2 = new Label("");
	private Label mail3 = new Label("");
	private ButtonItem addressee_button = new ButtonItem(S.TAB4_b);

	// textual description
	private HStack textual_layout = new HStack();
	private DynamicForm textual_form = new DynamicForm();
	public TextAreaItem textual_description = new TextAreaItem(); // in public

	private ButtonItem textual_button = new ButtonItem(S.TAB4_b);
	private Label tumor1 = new Label("");
	private Label tumor2 = new Label("");
	private Label tumor3 = new Label("");

	private Label resultLabel = new Label("");

	// change file(s)
	private HStack file_layout = new HStack();
	private TextAreaItem file_description = new TextAreaItem();
	private DynamicForm file_form = new DynamicForm();
	private ButtonItem file_button = new ButtonItem(S.TAB4_b);

	/**
	 * Creates a new tab for sending the DICOM E-Mail.
	 */
	private Sending() {
		// change addresses
		mail1.setIcon("sendmail_Down.png");
		mail2.setIcon("sendmail_Down.png");
		mail3.setIcon("sendmail_Down.png");
		mail1.setHeight(20);
		mail2.setHeight(20);
		mail3.setHeight(20);
		mail1.setWidth(200);
		mail2.setWidth(200);
		mail3.setWidth(200);
		vstack.addMember(mail1);
		vstack.addMember(mail2);
		vstack.addMember(mail3);
		addressee_button.setIcon("edit.png");
		addressee_button.setHeight(30);
		addressee_button.setWidth(200);
		addressee_button.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				MainPanel.getInstance().changeTab(0);
			}
		});
		addressee_form.setFields(new FormItem[] { addressee_button });
		addressee_layout.setIsGroup(true);
		addressee_layout.setGroupTitle(S.TAB4_a);
		addressee_layout.addMember(vstack);
		addressee_layout.addMember(addressee_form);
		addressee_layout.setPadding(20);
		addressee_layout.setWidth(300);
		addressee_layout.setBackgroundColor("efefef");

		// textual description
		textual_button.setIcon("edit.png");
		textual_button.setHeight(30);
		textual_button.setWidth(200);
		textual_button.setAlign(Alignment.RIGHT);
		textual_button.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				MainPanel.getInstance().changeTab(1);
			}
		});

		textual_description.setWidth(405);
		textual_description.setHeight(393);
		textual_description.setShowTitle(false);
		textual_form.setFields(new FormItem[] { textual_description,
				textual_button });

		textual_layout.setIsGroup(true);
		textual_layout.setGroupTitle(S.TAB4_c);
		textual_layout.addMember(textual_form);
		textual_layout.setPadding(20);
		textual_layout.setWidth(300);
		textual_layout.setBackgroundColor("efefef");

		// changing file(s)
		file_button.setIcon("edit.png");
		file_button.setHeight(30);
		file_button.setWidth(200);
		file_button.setAlign(Alignment.RIGHT);
		file_button.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				

			}
		});

		file_layout.setIsGroup(true);
		file_layout.setGroupTitle(S.TAB4_d);
		file_layout.setPadding(20);
		file_layout.setHeight(573);
		file_layout.setWidth(220);
		file_description.setShowTitle(false);
		file_description.setHeight(493);
		file_description.setWidth(258);
		file_form.setFields(new FormItem[] { file_description, file_button });
		file_layout.addMember(file_form);
		file_layout.setBackgroundColor("efefef");

		left_layout.addMember(addressee_layout);
		left_layout.addMember(textual_layout);
		right_layout.addMember(file_layout);

		LayoutSpacer ls = new LayoutSpacer();
		ls.setWidth(5);

		layoutMain.addMember(left_layout);
		layoutMain.addMember(ls);
		layoutMain.addMember(right_layout);
		layoutMain.setPadding(5);
		setPane(layoutMain);
	}

	/**
	 * @see org.openehealth.tewepo.twp.dmp.dmc.client.interfaces.ITab#setStrings()
	 */
	public void setStrings() {
		addressee_layout.setGroupTitle(S.TAB4_a);
		addressee_button.setTitle(S.TAB4_b);
		textual_layout.setGroupTitle(S.TAB4_c);
		textual_button.setTitle(S.TAB4_b);
		file_layout.setGroupTitle(S.TAB4_d);
		file_button.setTitle(S.TAB4_b);
	}

	/**
	 * Sets addresses to those, chosen in "Choosing Addresses".
	 * 
	 * @param add
	 */
	public void setAddresses(Vector<String> add) {
		mail1.setVisible(false);
		mail2.setVisible(false);
		mail3.setVisible(false);
		if (add.size() == 1) {
			mail1.setVisible(true);
			mail1.setContents("<b>" + add.elementAt(0) + "</b>");
		} else if (add.size() == 2) {
			mail1.setVisible(true);
			mail2.setVisible(true);
			mail1.setContents("<b>" + add.elementAt(0) + "</b>");
			mail2.setContents("<b>" + add.elementAt(1) + "</b>");

		} else if (add.size() == 3) {
			mail1.setVisible(true);
			mail2.setVisible(true);
			mail3.setVisible(true);
			mail1.setContents("<b>" + add.elementAt(0) + "</b>");
			mail2.setContents("<b>" + add.elementAt(1) + "</b>");
			mail3.setContents("<b>" + add.elementAt(2) + "</b>");
		}
	}

	/**
	 * Sets tumors.
	 * 
	 * @param add2
	 */
	public void setTumors(Vector<String> add2) {
		tumor1.setVisible(false);
		tumor2.setVisible(false);
		tumor3.setVisible(false);
		if (add2.size() == 1) {
			tumor1.setVisible(true);
			tumor1.setContents("<b>" + add2.elementAt(0) + "</b>");
		} else if (add2.size() == 2) {
			tumor1.setVisible(true);
			tumor2.setVisible(true);
			tumor1.setContents("<b>" + add2.elementAt(0) + "</b>");
			tumor2.setContents("<b>" + add2.elementAt(1) + "</b>");

		} else if (add2.size() == 3) {
			tumor1.setVisible(true);
			tumor2.setVisible(true);
			tumor3.setVisible(true);
			tumor1.setContents("<b>" + add2.elementAt(0) + "</b>");
			tumor2.setContents("<b>" + add2.elementAt(1) + "</b>");
			tumor3.setContents("<b>" + add2.elementAt(2) + "</b>");
		}
	}

}
