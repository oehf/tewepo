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

import java.util.LinkedHashMap;

import org.openehealth.tewepo.twp.dmp.dmc.client.rpc.SendDicomMailsService;
import org.openehealth.tewepo.twp.dmp.dmc.client.rpc.SendDicomMailsServiceAsync;
import org.openehealth.tewepo.twp.dmp.dmc.client.text.TextualDescription;
import org.openehealth.tewepo.twp.dmp.dmc.client.utils.StringInternationalizer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TabBarControls;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.TabSet;


/**
 * An object of this class represents the main panel. The main panel contains
 * tabpanel tab for choosing recipient tab for textual description tab for
 * sending files back and next button progressbar
 * 
 * @author devmis
 * 
 */
public class MainPanel extends VStack {

	// private fields
	private StringInternationalizer S = StringInternationalizer.getInstance();
	private TabSet tabpanel = new TabSet();
	private ChoosingRecipient tab0 = ChoosingRecipient.getInstance();
	private TextualDescription tab1 = TextualDescription.getInstance();
	// // private TextualDescription_heart tab1 =
	// // TextualDescription_heart.getInstance();
	// private TextualDescription_heart tab1 =
	// TextualDescription_heart.getInstance();
	// private ChoosingFile tab2 = ChoosingFile.getInstance();
	private Sending tab3 = Sending.getInstance();
	private Button weiter = new Button(S.NEXT_BUTTON_a);
	private Button zurueck = new Button(S.BACK_BUTTON);
	private Progressbar pb = new Progressbar();
	private SelectItem sprachauswahl = new SelectItem();
	private DynamicForm form = new DynamicForm();
	private HLayout navigation = new HLayout(3);
	private LayoutSpacer ls = new LayoutSpacer();

	private static MainPanel instance = null;

	// different options to show a message in the statusbar
	public final int STATUS_NONE = 0;
	public final int STATUS_PERMANENT_NOTE = 1;
	public final int STATUS_PERMANENT_WARNING = 2;
	public final int STATUS_PERMANENT_SUCCESS = 3;
	public final int STATUS_5SEC_NOTE = 4;
	public final int STATUS_5SEC_WARNING = 5;
	public final int STATUS_5SEC_SUCCESS = 6;

	// Remote call
	private final SendDicomMailsServiceAsync sendDicomMailService = (SendDicomMailsServiceAsync) GWT
			.create(SendDicomMailsService.class);

	/**
	 * Singleton-Pattern
	 * 
	 * @return an instance of the class
	 */
	public static MainPanel getInstance() {
		if (instance == null) {
			instance = new MainPanel();
		}
		return instance;
	}

	/**
	 * Creates a new main panel.
	 */
	private MainPanel() {

		// configuration of tab-panel
		tabpanel.setTabBarPosition(Side.TOP);
		tabpanel.setWidth(780);
		tabpanel.setHeight(625);
		tabpanel.setTabBarThickness(20);

		// deactivating internationalization for RC1
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("DE", "Deutsch");
		valueMap.put("GB", "English");
		LinkedHashMap<String, String> valueIcons = new LinkedHashMap<String, String>();
		valueIcons.put("DE", "DE.png");
		valueIcons.put("GB", "GB.png");

		sprachauswahl.setVisible(false);
		sprachauswahl.setTitle("");
		sprachauswahl.setValueMap(valueMap);
		sprachauswahl.setValueIcons(valueIcons);
		sprachauswahl.setDefaultToFirstOption(true);
		sprachauswahl.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (sprachauswahl.getDisplayValue().contains("Deutsch")) {
					setStrings(S.LANGUAGE_ENG);
					sprachauswahl.redraw();
				} else if (sprachauswahl.getDisplayValue().contains("English")) {
					setStrings(S.LANGUAGE_DE);
					sprachauswahl.redraw();

				}
				// ...other languages
			}
		});
		form.setFields(sprachauswahl);
		tabpanel.setTabBarControls(TabBarControls.TAB_SCROLLER,
				TabBarControls.TAB_PICKER, form);

		// adding tabs
		tabpanel.addTab(tab0);
		tabpanel.addTab(tab1);
		// tabpanel.addTab(tab2);
		tabpanel.addTab(tab3);

		// set first tab
		changeTab(0);

		// back&next-Buttons
		weiter.setSize("200", "30");
		weiter.addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent event) {
						if (tabpanel.getSelectedTabNumber() == 2) {
							// send Panel
						
							startSending();
						} else if (!(tabpanel.getSelectedTabNumber() == 1 && !tab1
								.isValid())) {
							changeTab(tabpanel.getSelectedTabNumber() + 1);
						} else {
							setStatus(S.STATUS_ERROR_1, STATUS_5SEC_WARNING);
						}
					}
				});

		zurueck.setSize("200", "30");
		zurueck.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				changeTab(tabpanel.getSelectedTabNumber() - 1);
			}
		});

		pb.setHeight100();
		pb.setShowTitle(true);

		navigation.setAnimateMembers(true);
		navigation.setWidth(780);
		navigation.setHeight(30);
		navigation.addMember(zurueck);
		navigation.addMember(pb);
		navigation.addMember(weiter);

		ls.setHeight(5);

		// add to layout
		addMember(tabpanel);
		addMember(ls);
		addMember(navigation);
	}

	/**
	 * Changes the strings, if a new language is selected.
	 * 
	 * @param language
	 *            selected language
	 */
	public void setStrings(String language) {
		S.setDefaultLanguage(language);

		tabpanel.setTabTitle(0, S.TAB1_HEADER);
		tabpanel.setTabTitle(1, S.TAB2_HEADER);
		// tabpanel.setTabTitle(2, S.TAB3_HEADER);
		tabpanel.setTabTitle(2, S.TAB4_HEADER);

		tab0.setStrings();
		tab1.setStrings();
		// tab2.setStrings();
		tab3.setStrings();

		weiter.setTitle(S.NEXT_BUTTON_a);
		if (tabpanel.getSelectedTabNumber() == 3) {
			weiter.setTitle(S.NEXT_BUTTON_b);
			tab1.showTextualDescription();
		}
	}

	/**
	 * Enables the next button, if allowed.
	 * 
	 * @param check
	 *            enable, if true disable, else
	 */
	public void enableWeiterButton(boolean check) {
		if (check) {
			weiter.enable();
		} else {
			weiter.disable();
		}
	}

	/**
	 * Changes tabs, shows correct progressbar status and buttons.
	 * 
	 * @param i
	 */
	public void changeTab(int i) {

		// no bugs
		if (i < 0) {
			i = 0;
		} else if (i > 3) {
			i = 3;
		}

		// first disable all tabs
		tabpanel.disableTab(0);
		tabpanel.disableTab(1);
		tabpanel.disableTab(2);
		// tabpanel.disableTab(3);

		// standard for all tabs
		weiter.setTitle(S.NEXT_BUTTON_a);
		zurueck.enable();
		tabpanel.selectTab(i);
		tabpanel.enableTab(i);

		// customizing
		switch (i) {
		case 0:
			zurueck.disable();
			pb.setPercentDone(30);
			setStatus(S.STATUS_1, STATUS_PERMANENT_NOTE);
			break;
		case 1:
			pb.setPercentDone(60);
			setStatus(S.STATUS_2, STATUS_PERMANENT_NOTE);
			break;
		// case 2:
		// pb.setPercentDone(60);
		// setStatus(S.STATUS_3, STATUS_PERMANENT_NOTE);
		// break;
		case 2:
			// show choosen recipients from tab0
			Sending.getInstance().setAddresses(
					ChoosingRecipient.getInstance().getAddresses());
			// show textual description
			tab1.showTextualDescription();
			weiter.setTitle(S.NEXT_BUTTON_b);
			pb.setPercentDone(100);
			setStatus(S.STATUS_3, STATUS_PERMANENT_NOTE);
			break;
		}
	}

	/**
	 * Shows progress text in statusbar in defined time intervals.
	 * 
	 * @param newText
	 * @param option
	 */
	public void setStatus(String newText, int option) {
		final String oldText = pb.getTitle();
		Timer timer = new Timer() {
			@Override
			public void run() {
				pb.setTitle(oldText);
			}
		};
		if (newText != null) {
			switch (option) {
			case STATUS_NONE:
				pb.setTitle("");
				break;
			case STATUS_PERMANENT_NOTE:
				pb.setTitle("<i>" + newText + "</i>");
				break;
			case STATUS_PERMANENT_WARNING:
				pb.setTitle("<font color=\"red\"> <b>" + newText
						+ "</b></font>");
				break;
			case STATUS_PERMANENT_SUCCESS:
				pb.setTitle("<font color=\"#38610B\"> <b>" + newText
						+ "</b></font>");
				break;
			case STATUS_5SEC_NOTE:
				pb.setTitle("<b>" + newText + "</b></font>");
				timer.schedule(5000);
				break;
			case STATUS_5SEC_WARNING:
				pb.setTitle("<font color=\"red\"> <b>" + newText
						+ "</b></font>");
				timer.schedule(5000);
				break;
			case STATUS_5SEC_SUCCESS:
				pb.setTitle("<font color=\"#38610B\"> <b>" + newText
						+ "</b></font>");
				timer.schedule(5000);
				break;
			}
		}
	}

	/**
	 * Start sending the DICOM E-Mail.
	 */
	@SuppressWarnings("unchecked")
	public void startSending() {
		ServiceDefTarget endpoint = (ServiceDefTarget) sendDicomMailService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "sendData.sdm");
		// Call send method
		sendDicomMailService.sendDicomMails(null, new AsyncCallback() {

			public void onFailure(Throwable e) {
				// resultLabel.setText("Server call failed");
				System.out.println("Server call failed");
				pb
						.setTitle("<font color=\"red\"> <b> Server call failed </b></font>");
			}

			public void onSuccess(Object obj) {
				if (obj != null) {
					pb.setTitle("<font color=\"red\"> <b>" + obj.toString()
							+ "</b></font>");
					// label.setText(obj.toString());
					System.out.println(obj.toString());
				} else {
					pb
							.setTitle("<font color=\"red\"> <b> Server call returned nothing </b></font>");
					// label.setText("Server call returned nothing");
					System.out.println("Server call returned nothing");
				}
			}
		});
	}
}