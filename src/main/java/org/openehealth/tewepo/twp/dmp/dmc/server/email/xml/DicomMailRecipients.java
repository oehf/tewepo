/*
 * Copyright 2012 Benjamin Schneider
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
package org.openehealth.tewepo.twp.dmp.dmc.server.email.xml;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openehealth.twp.tewepo.configuration.Configuration;


/**
 * This class is for the DICOM e-mail recipients.
 * 
 * @author Benjamin Schneider
 * 
 */
public class DicomMailRecipients {

	/** the configuration */
	private final static Configuration conf = Configuration.getMainConfig("");
	private List<DicomMailRecipient> recipients = new ArrayList<DicomMailRecipient>();
	private XMLEncoder enc = null;
	private Logger logger = Logger.getLogger(DicomMailRecipients.class);
	private File recipientsFile = null;

	/**
	 * Creates a new DICOM mail recipients' object from the given XML-file.
	 * 
	 */
	public DicomMailRecipients(String folderPath) {
		/*
		 * Check if a XML File exists; if exists, load it.
		 */
		recipientsFile = new File(folderPath + "recipients/"
				+ conf.getProperty("filenameXMLFileRecipients"));
		logger.info("DicomMailRecipients - recipientsFile Path: "
				+ recipientsFile.getAbsolutePath());
		if (recipientsFile.exists())
			deserialize(recipientsFile.getAbsolutePath());
		else {
			generateDefaultDataXml();
			serialize();
		}
	}

	/**
	 * Gets a list of all recipients.
	 * 
	 * @return recipients
	 */
	public List<DicomMailRecipient> getRecipients() {
		return recipients;
	}

	/**
	 * Sets a list with all recipients.
	 * 
	 * @param recipients
	 */
	public void setRecipients(List<DicomMailRecipient> recipients) {
		this.recipients = recipients;
	}

	/**
	 * Adds a new recipient to the existing recipients' list.
	 * 
	 * @param recipient
	 */
	public void addRecipient(DicomMailRecipient recipient) {
		this.recipients.add(recipient);
	}

	/**
	 * Serialize. Writes recipients' data to an XML-file.
	 */
	public void serialize() {

		try {
			enc = new XMLEncoder(new FileOutputStream(recipientsFile
					.getAbsolutePath()));
			for (DicomMailRecipient recipient : this.recipients) {
				enc.writeObject(recipient);
			}
		} catch (IOException e) {
			logger.error("DicomMailRecipients - serialize - " + e.getMessage());
		} finally {
			if (enc != null)
				enc.close();
		}
	}

	/**
	 * Deserialize. Reads all recipients' data from the XML-file.
	 * 
	 * @param recipientsFilePath
	 *            location of the XML-file
	 */
	public void deserialize(String recipientsFilePath) {
		this.recipients = new ArrayList<DicomMailRecipient>();
		XMLDecoder dec = null;
		try {
			dec = new XMLDecoder(new FileInputStream(recipientsFilePath));
			while (true) {
				DicomMailRecipient recipient = (DicomMailRecipient) dec
						.readObject();
				this.addRecipient(recipient);
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			logger.info("DicomMailRecipients - deserialize - Alle Empfängerdaten von der XML Datei eingelesen!");
		} catch (IOException e) {
			logger.error("DicomMailRecipients - deserialize - " + e.getMessage());
		} finally {
			if (enc != null)
				dec.close();
		}
	}

	/**
	 * Adds a dummy recipient to the list for testing purposes.
	 */
	private void generateDefaultDataXml() {

		recipients = new ArrayList<DicomMailRecipient>();

		DicomMailRecipient systemdefault1 = new DicomMailRecipient();
		systemdefault1.setInstitution("Test");
		systemdefault1.setLocation("Teststadt");
		systemdefault1.setRecipient("Abteilung Test");
		systemdefault1.setPublicKeyId("ADC25E");
		systemdefault1
				.setMailAddress1("max.mustermannr@test.de");

		recipients.add(systemdefault1);

	}
}
