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

import org.openehealth.tewepo.twp.dmp.dmc.client.interfaces.ITab;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.SubmitValuesEvent;
import com.smartgwt.client.widgets.form.events.SubmitValuesHandler;
import com.smartgwt.client.widgets.form.fields.SubmitItem;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;

/**
 * This class is for choosing files, which have to be uploaded.
 * 
 * @author devmis
 * 
 */
public class ChoosingFile extends Tab implements ITab {

	// private fields
	private static ChoosingFile instance = null;
	private UploadItem uploadItem = new UploadItem();
	private DynamicForm uploadForm = new DynamicForm();
	private SubmitItem submitItem = new SubmitItem(S.TAB3_c, S.TAB3_c);

	/**
	 * Singleton-Pattern
	 * 
	 * @return an instance of the class
	 */
	public static ChoosingFile getInstance() {
		if (instance == null) {
			instance = new ChoosingFile();
		}
		return instance;
	}

	/**
	 * Creates a tab to upload files.
	 */
	private ChoosingFile() {

		// create a hidden frame
		Canvas iframe = new Canvas();
		iframe.setID("fileUploadFrame");
		iframe
				.setContents("<IFRAME NAME=\"fileUploadFrame\" style=\"width:0;height:0;border:0\"></IFRAME>");
		iframe.setVisibility(Visibility.VISIBLE); // Could not get the IFRAME in
													// the page with Visibility
													// HIDDEN
		// so set the style sizes to 0,0,0 instead to not show the IFRAME

		// create upload form with an upload item and a submit item
		uploadForm.setNumCols(4);
		uploadForm.setIsGroup(true);
		uploadForm.setBackgroundColor("efefef");
		uploadForm.setGroupTitle(S.TAB3_a);
		uploadForm.setTarget("fileUploadFrame"); // set target of FORM to the
													// IFRAME
		uploadForm.setEncoding(Encoding.MULTIPART);
		uploadForm.setAction(GWT.getModuleBaseURL()); // call to a FileUpload
														// servlet on the
														// webserver

		// creates a HTML formitem with a textfield and a browse button
		uploadItem.setWidth(300);
		uploadItem.setShowTitle(false);
		uploadItem.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				// The item shows the whole (long) path, so set caret at end
				// so user can verify his chosen filename
				String filename = (String) uploadItem.getValue();
				if (filename != null)
					uploadItem.setSelectionRange(filename.length(), filename
							.length());

				// Unfortunately UploadItem does not throw a ChangedEvent :(
			}
		});

		submitItem.setStartRow(false);
		submitItem.setEndRow(false);
		uploadForm.setItems(uploadItem, submitItem);
		uploadForm.addSubmitValuesHandler(new SubmitValuesHandler() {

			public void onSubmitValues(SubmitValuesEvent event) {
				System.out.println((String) uploadItem.getValue());
				uploadForm.submitForm();
			}
		});
		VStack layout = new VStack();
		layout.addMember(uploadForm);
		layout.addMember(iframe);

		setPane(layout);
	}

	/**
	 * @see org.openehealth.tewepo.twp.dmp.dmc.client.interfaces.ITab#setStrings()
	 */
	public void setStrings() {
		uploadForm.setGroupTitle(S.TAB3_a);
		submitItem.setTitle(S.TAB3_c);
	}
}
