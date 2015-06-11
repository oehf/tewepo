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
package org.openehealth.tewepo.twp.dmp.dmc.server.email.dicommail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

/**
 *  Class to build encrypted message and to build a MIME PGP Multipart
 * 
 * @author devmis
 *
 */
public class CEncryptedMessageBuilder extends CBaseMessageBuilder {

	private Logger logger = Logger.getLogger(CEncryptedMessageBuilder.class);

	
	/**
	 * Class Constructor
	 * @param sComments
	 * @param sxTag
	 * @param sPublicKey
	 */
	public CEncryptedMessageBuilder(String sComments, String sxTag,
			String sPublicKey) {
		super(sComments, sxTag, sPublicKey);
	}

	/**
	 * "PlainPart" here means "unencrypted part"
	 * 
	 * @param mbpPlainPart
	 * 
	 * @param sFileName
	 * 
	 * @return
	 */
	protected void ApplyGPG(MimeBodyPart mbpPlainPart, String sFileName)
			throws MessagingException, IOException, wrapperException {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		mbpPlainPart.writeTo(outStream);
		String[] sPublicKeys = { mPublicKey };
		GpgWrapper.encrypt(outStream, sPublicKeys, sFileName);
	}
	
	/**
	 * Build a MIME PGP Multipart Body
	 * 
	 * @param mbpPart
	 * 
	 * @param sFileNameGpg
	 * 
	 * @return
	 */
	protected MimeMultipart BuildMimePgpMultipart(MimeBodyPart mbpPart,
			String sFileNameGpg) throws MessagingException, IOException,
			wrapperException {
		// PGP:
		logger.info("CEncryptedMessageBuiler - BuildMimePgpMultipart - sFilenameGpg: " + sFileNameGpg);
		ApplyGPG(mbpPart, sFileNameGpg);

		MimeBodyPart mbpPartWithoutFile = new MimeBodyPart();
		mbpPartWithoutFile.setText("Version: 1\n");
		mbpPartWithoutFile.setHeader("Content-Type",
				"application/pgp-encrypted");
	
		MimeBodyPart mbpPartWithFile = new MimeBodyPart();
		File dataFile = new File(sFileNameGpg);
		
		FileDataSource fdsData = new FileDataSource(dataFile);
		
		mbpPartWithFile.setDataHandler(new DataHandler(fdsData));
	
		MimeMultipart mpRoot = new MimeMultipart(
				"encrypted;\n\tprotocol=\"application/pgp-encrypted\"");
		mpRoot.addBodyPart(mbpPartWithoutFile);
		mpRoot.addBodyPart(mbpPartWithFile);

		return mpRoot;
	}
}
