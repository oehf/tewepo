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

import org.openehealth.tewepo.twp.dmp.dmc.client.data.SelectedData;
import org.openehealth.tewepo.twp.dmp.dmc.client.interfaces.ITab;

import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ItemHoverEvent;
import com.smartgwt.client.widgets.form.fields.events.ItemHoverHandler;
import com.smartgwt.client.widgets.grid.ColumnTree;
import com.smartgwt.client.widgets.grid.events.NodeSelectedEvent;
import com.smartgwt.client.widgets.grid.events.NodeSelectedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tree.TreeNode;


/**
 * This class is for choosing recipient, the e-mail must be sent to.
 * 
 * @author devmis
 * 
 */
public class ChoosingRecipient extends Tab implements ITab {

	// private fields
	private static ChoosingRecipient instance = null;

	private ColumnTree columnTree = new ColumnTree();
	private ColumnTree favourits = new ColumnTree();
	private VLayout layout0 = new VLayout();
	private VLayout layout2 = new VLayout();
	private HLayout layout1 = new HLayout();
	private TextItem name = new TextItem(S.TAB1_e);
	private TextItem city = new TextItem(S.TAB1_f);
	private TextItem category = new TextItem(S.TAB1_g);
	private CheckboxItem cb1 = new CheckboxItem();
	private CheckboxItem cb2 = new CheckboxItem();
	private CheckboxItem cb3 = new CheckboxItem();
	// details container
	private DynamicForm profileForm = new DynamicForm();
	// send to container
	private DynamicForm profileForm2 = new DynamicForm();
	private HStack hstack = new HStack();
	private Label send = new Label(S.TAB1_m);
	private Label dontsend = new Label(S.TAB1_n);
	private String mail1 = "";
	private String mail2 = "";
	private String mail3 = "";

	/**
	 * Singleton-Pattern
	 * 
	 * @return an instance of the class
	 */
	public static ChoosingRecipient getInstance() {
		if (instance == null) {
			instance = new ChoosingRecipient();
		}
		return instance;
	}

	/**
	 * Creates a new tab to choose recipient.
	 */
	private ChoosingRecipient() {

		columnTree.setGroupTitle(S.TAB1_a);
		columnTree.setIsGroup(true);
		columnTree.setWidth100();
		columnTree.setHeight(285);
		// columnTree.setData(new ColumnTreeDummyData());
		columnTree.setAutoFetchData(true);
		columnTree.setNodeIcon("health.png");
		columnTree.setFolderIcon("stadt.png");
		columnTree.setShowOpenIcons(false);
		columnTree.setShowDropIcons(false);
		columnTree.setClosedIconSuffix("");
		columnTree.setShowHeaders(true);
		columnTree.setShowShadow(true);
		columnTree.setShowNodeCount(true);
		columnTree.addNodeSelectedHandler(new NodeSelectedHandler() {

			public void onNodeSelected(NodeSelectedEvent event) {

				mail1 = event.getTreeNode().getAttribute("email1"); 
				//here stored the selected in mail1												
																	
				mail2 = event.getTreeNode().getAttribute("email2");
				mail3 = event.getTreeNode().getAttribute("email3");

				if (event.getTreeNode().getAttribute("children") == null) {
					MainPanel.getInstance().enableWeiterButton(true);
					name.setValue(event.getTreeNode().getAttribute("name"));
					city.setValue(event.getTreeNode().getAttribute("ort"));
					category.setValue(event.getTreeNode().getAttribute(
							"kategorie"));
					if (mail1 != null && mail1 != "") { // when selected mail1 
								//if selected mail1, then put checkmarks					
						cb1.setValue(true);
						cb1.setTitle("<b>" + S.TAB1_h + ":  ( " + mail1
								+ " )</b>");
						cb1.setVisible(true);
					}
					if (mail2 != null && mail2 != "") {
						cb2.setTitle(S.TAB1_i + ":  ( " + mail2 + " )");
						cb2.setVisible(true);
						cb2.setValue(false);
					} else {
						cb2.setVisible(false);
					}
					if (mail3 != null && mail3 != "") {
						cb3.setTitle(S.TAB1_j + ":  ( " + mail3 + " )");
						cb3.setVisible(true);
						cb3.setValue(false);
					} else {
						cb3.setVisible(false);
					}
				} else {
					MainPanel.getInstance().enableWeiterButton(false);
					name.setValue("");
					city.setValue("");
					category.setValue("");

					cb1.setVisible(false);
					cb2.setVisible(false);
					cb3.setVisible(false);
				}
				profileForm2.redraw();

				// set addresses
				SelectedData.getInstance().setAddresses(getAddresses());
			}
		});
		columnTree.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				favourits.deselectAllRecords();
			}
		});

		favourits.setGroupTitle(S.TAB1_a);
		favourits.setIsGroup(true);
		favourits.setWidth100();
		favourits.setHeight(155);
		// favourits.setData(new ColumnTreeDummyData(1));
		favourits.setAutoFetchData(true);
		favourits.setNodeIcon("health.png");
		favourits.setFolderIcon("stadt.png");
		favourits.setShowOpenIcons(false);
		favourits.setShowDropIcons(false);
		favourits.setClosedIconSuffix("");
		favourits.setShowHeaders(true);
		favourits.setShowShadow(true);
		favourits.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				columnTree.deselectAllRecords();
				columnTree.deselectAllRecords(1);
				columnTree.deselectAllRecords(2);
			}
		});
		favourits.addNodeSelectedHandler(new NodeSelectedHandler() {

			public void onNodeSelected(NodeSelectedEvent event) {
				name.setValue(event.getTreeNode().getAttribute("name"));
				city.setValue(event.getTreeNode().getAttribute("ort"));
				category
						.setValue(event.getTreeNode().getAttribute("kategorie"));
				mail1 = event.getTreeNode().getAttribute("email1");
				mail2 = event.getTreeNode().getAttribute("email2");
				mail3 = event.getTreeNode().getAttribute("email3");

				cb1.setVisible(false);
				cb2.setVisible(false);
				cb3.setVisible(false);
				cb2.setValue(false);
				cb3.setValue(false);

				if (mail1 != null && mail1 != "") {
					cb1.setValue(true);
					cb1.setTitle("<b>" + S.TAB1_h + ":  ( " + mail1 + " )</b>");
					cb1.setVisible(true);
				}
				if (mail2 != null && mail2 != "") {
					cb2.setTitle(S.TAB1_i + ":  ( " + mail2 + " )");
					cb2.setVisible(true);

					// set addresses
					SelectedData.getInstance().setAddresses(getAddresses());
				}
				if (mail3 != null && mail3 != "") {
					cb3.setTitle(S.TAB1_j + ":  ( " + mail3 + " )");
					cb3.setVisible(true);
				}
				profileForm2.redraw();
				// at least 1 entry must be chosen
				if (!(Boolean) cb1.getValue() && !(Boolean) cb2.getValue()
						&& !(Boolean) cb3.getValue()) {
					MainPanel.getInstance().enableWeiterButton(false);
				} else {
					MainPanel.getInstance().enableWeiterButton(true);
				}
				// set addresses
				SelectedData.getInstance().setAddresses(getAddresses());

			}
		});

		// upper View
		layout0.setPadding(10);
		layout0.setGroupTitle(S.TAB1_c);
		layout0.setIsGroup(true);
		layout0.setWidth100();
		layout0.setBackgroundColor("F5F5F5");
		layout0.addMember(columnTree);
		layout0.addMember(favourits);

		// lower View
		name.setWidth(290);
		city.setWidth(200);
		category.setWidth(200);

		// choose the first entry of all recipients and show his details
		if (favourits.getData() != null) {
			favourits.selectRecord(0);
			name.setValue(favourits.getRecord(0).getAttribute("name"));
			city.setValue(favourits.getRecord(0).getAttribute("ort"));
			category.setValue(favourits.getRecord(0).getAttribute("kategorie"));
			mail1 = favourits.getRecord(0).getAttribute("email1");
			mail2 = favourits.getRecord(0).getAttribute("email2");
			mail3 = favourits.getRecord(0).getAttribute("email3");

			cb1.setVisible(false);
			cb2.setVisible(false);
			cb3.setVisible(false);
			cb1.setValue(false);
			cb2.setValue(false);
			cb3.setValue(false);
			// choosen addresses in bold
			if (mail1 != null && mail1 != "") {
				cb1.setValue(true);
				cb1.setTitle("<b>" + S.TAB1_h + ":  ( " + mail1 + " )</b>");
				cb1.setVisible(true);
			}
			if (mail2 != null && mail2 != "") {
				cb2.setTitle(S.TAB1_i + ":  ( " + mail2 + " )");
				cb2.setVisible(true);
			}
			if (mail3 != null && mail3 != "") {
				cb3.setTitle(S.TAB1_j + ":  ( " + mail3 + " )");
				cb3.setVisible(true);
			}
			profileForm2.redraw();
		}

		cb1.setValue(true);
		cb1.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				profileForm2.redraw();
				if ((Boolean) event.getValue()) {
					cb1.setTitle("<b>" + cb1.getTitle() + "</b>");
				} else {
					cb1.setTitle(cb1.getTitle().substring(3,
							cb1.getTitle().length() - 4));
				}
				// at least 1 recipient must be choosen
				if (!(Boolean) cb1.getValue() && !(Boolean) cb2.getValue()
						&& !(Boolean) cb3.getValue()) {
					MainPanel.getInstance().enableWeiterButton(false);
				} else {
					MainPanel.getInstance().enableWeiterButton(true);
				}
				// set addresses
				SelectedData.getInstance().setAddresses(getAddresses());
			}
		});
		cb2.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				System.out.println(event.getValue());
				profileForm2.redraw();
				if ((Boolean) event.getValue()) {
					cb2.setTitle("<b>" + cb2.getTitle() + "</b>");
				} else {
					cb2.setTitle(cb2.getTitle().substring(3,
							cb2.getTitle().length() - 4));
				}
				// at least 1 recipient must be choosen
				if (!(Boolean) cb1.getValue() && !(Boolean) cb2.getValue()
						&& !(Boolean) cb3.getValue()) {
					MainPanel.getInstance().enableWeiterButton(false);
				} else {
					MainPanel.getInstance().enableWeiterButton(true);
				}

				// set addresses
				SelectedData.getInstance().setAddresses(getAddresses());
			}

		});

		cb3.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {

				profileForm2.redraw();
				if ((Boolean) event.getValue()) {
					cb3.setTitle("<b>" + cb3.getTitle() + "</b>");
				} else {
					cb3.setTitle(cb3.getTitle().substring(3,
							cb3.getTitle().length() - 4));
				}
				// at least 1 recipient must be choosen
				if (!(Boolean) cb1.getValue() && !(Boolean) cb2.getValue()
						&& !(Boolean) cb3.getValue()) {
					MainPanel.getInstance().enableWeiterButton(false);
				} else {
					MainPanel.getInstance().enableWeiterButton(true);
				}
				// set addresses
				SelectedData.getInstance().setAddresses(getAddresses());
			}

		});
		// set Icons 
		cb1.setCheckedImage("sendmail.png");
		cb2.setCheckedImage("sendmail.png");
		cb3.setCheckedImage("sendmail.png");
		cb1.setUncheckedImage("dontsendmail.png");
		cb2.setUncheckedImage("dontsendmail.png");
		cb3.setUncheckedImage("dontsendmail.png");
		cb1.addItemHoverHandler(new ItemHoverHandler() {

			public void onItemHover(ItemHoverEvent event) {

				if (cb1.getValueAsBoolean()) {
					cb1.setTooltip("an E-Mail-Adresse 1 verschicken");
				} else {
					cb1
							.setTooltip("an E-Mail-Adresse 1 <b>nicht</b> verschicken");
				}
			}
		});
		cb2.addItemHoverHandler(new ItemHoverHandler() {

			public void onItemHover(ItemHoverEvent event) {

				if (cb2.getValueAsBoolean()) {
					cb2.setTooltip("an E-Mail-Adresse 2 verschicken");
				} else {
					cb2
							.setTooltip("an E-Mail-Adresse 2 <b>nicht</b> verschicken");
				}
			}
		});
		cb3.addItemHoverHandler(new ItemHoverHandler() {

			public void onItemHover(ItemHoverEvent event) {

				if (cb3.getValueAsBoolean()) {
					cb3.setTooltip("an E-Mail-Adresse 3 verschicken");
				} else {
					cb3
							.setTooltip("an E-Mail-Adresse 3 <b>nicht</b> verschicken");
				}
			}
		});

		profileForm.setFields(new FormItem[] { name, city, category });
		profileForm.setBackgroundColor("efefef");
		profileForm.setHeight100();
		profileForm.setIsGroup(true);
		profileForm.disable();

		profileForm2.setFields(new FormItem[] { cb1, cb2, cb3 });
		profileForm2.setBackgroundColor("efefef");
		profileForm2.setHeight100();
		profileForm2.setIsGroup(true);
		profileForm2.setWidth(394);

		LayoutSpacer ls = new LayoutSpacer();
		ls.setWidth(5);

		layout1.addMember(profileForm);
		layout1.addMember(ls);
		layout1.addMember(profileForm2);
		layout1.setHeight(100);

		layout2.addMember(layout0);
		layout2.addMember(layout1);
		layout2.setPadding(10);

		SelectedData.getInstance().setAddresses(getAddresses());

		this.setPane(layout2);

	}

	/**
	 * An object of this class represents customized TreeNodes.
	 * 
	 * @author Benjamin Schneider
	 * 
	 */
	public static class PartsTreeNode extends TreeNode {
		
		/**
		 * Constructor
		 * 
		 * @param name
		 * @param icon
		 */
		public PartsTreeNode(String name, String icon) {
			this(name, icon, new PartsTreeNode[] {});
		}

		/**
		 * Creates a new node in the tree with all given attributes.
		 * 
		 * @param name
		 * @param children
		 */
		public PartsTreeNode(String name, PartsTreeNode... children) {
			this(name, null, children);
		}

		/**
		 * Creates a new node in the tree with all given attributes.
		 * 
		 * @param name
		 * @param end
		 * @param mail
		 */
		public PartsTreeNode(String name, boolean end, String mail) {
			setTitle(name);
			setAttribute("mail", mail);
		}

		/**
		 * Creates a new node in the tree with all given attributes.
		 * 
		 * @param name
		 * @param icon
		 *            icon must not be null.
		 * @param children
		 */
		public PartsTreeNode(String name, String icon,
				PartsTreeNode... children) {
			setAttribute("Name", name);
			setTitle(name);
			setAttribute("children", children);
			if (icon != null)
				setAttribute("icon", icon);
		}
	}

	/**
	 * @see org.openehealth.tewepo.twp.dmp.dmc.client.interfaces.ITab#setStrings()
	 */
	public void setStrings() {
		columnTree.setGroupTitle(S.TAB1_a);
		favourits.setGroupTitle(S.TAB1_b);
		layout0.setGroupTitle(S.TAB1_c);
		profileForm.setGroupTitle(S.TAB1_d);
		profileForm2.setGroupTitle(S.TAB1_k);
		send.setContents(S.TAB1_m);
		dontsend.setContents(S.TAB1_n);
		hstack.setGroupTitle(S.TAB1_l);
		name.setTitle(S.TAB1_e);
		city.setTitle(S.TAB1_f);
		category.setTitle(S.TAB1_g);
		profileForm.redraw();
	}

	/**
	 * Gets all three e-mail addresses of the chosen recipient.
	 * 
	 * @return adresses
	 */
	public Vector<String> getAddresses() {
		Vector<String> addresses = new Vector<String>();
		if (cb1.getVisible() && cb1.getValueAsBoolean()) {
			addresses.add(mail1);
		}
		if (cb2.getVisible() && cb2.getValueAsBoolean()) {
			addresses.add(mail2);
		}
		if (cb3.getVisible() && cb3.getValueAsBoolean()) {
			addresses.add(mail3);
		}
		return addresses;
	}
}
