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
package org.openehealth.tewepo.twp.dmp.dmc.client.jsni;

import org.openehealth.tewepo.twp.dmp.dmc.client.GuiSingleton;

/**
 * Interface for native JavaScript methods.
 * 
 * @author devmis
 * 
 */
public class JSNInterface {

	/**
	 * Puts up a JavaScript alert dialog.
	 * 
	 * @param msg
	 *            alert message
	 */
	public static native void alert(String msg) /*-{
		$wnd.alert(msg);
	}-*/;

	/**
	 * Views the upload panel.
	 */
	public static native void viewUploadApplet() /*-{
		$doc.getElementById("uploadApplet").style.display = 'block';

	}-*/;

	/**
	 * Hides the upload panel.
	 */
	public static native void hideUploadApplet() /*-{
		$doc.getElementById("uploadApplet").style.display = 'none';
	}-*/;

	/**
	 * Gets the user's ID.
	 * 
	 * @return userId
	 */
	public static native String getUserId() /*-{
		return $doc.getElementById("hidden_userId").title;
	}-*/;

	/**
	 * Refreshes the uploaded file list.
	 */
	public static void refreshFileList() {
		GuiSingleton.getInstance().showFilesToSend(true);

		GuiSingleton.getInstance().showFileSize();
		
		JSNInterface.hideUploadApplet();
	}

	/**
	 * Exportstatic of the refresh file list.
	 * 
	 */
	public static native void exportStaticRefreshFileListMethod()/*-{
		$wnd.refreshFileListJSNI = @org.openehealth.tewepo.twp.dmp.dmc.client.jsni.JSNInterface::refreshFileList();
	}-*/;

	/**
	 * Opens the DICOM Email page.
	 * 
	 * @param msg
	 */
	public static native void openDicomEmailPageUrl(String msg)/*-{
		
	}-*/;
}
