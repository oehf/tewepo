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
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;



/**
 * Class to build signed and encrypted message
 * @author devmis
 *
 */
public class CSignedEncryptedMessageBuilder extends CEncryptedMessageBuilder {

	/**
	 * Build Signed and encrypted message
	 * 
	 * @param sComments
	 * @param sxTag
	 * @param sPublicKey
	 */
    public CSignedEncryptedMessageBuilder(String sComments, String sxTag,
                                                          String sPublicKey) {
		super(sComments, sxTag, sPublicKey);
    }


    /**
     * "PlainPart" here means "unencrypted part" 
     * 
     * @param mbpPlainPart
     * @param sFileName
     * @throws MessagingException, IOException, wrapperException
     */
    protected void ApplyGPG(MimeBodyPart mbpPlainPart, String sFileName) 
    throws MessagingException,IOException,wrapperException {
      ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      mbpPlainPart.writeTo(outStream);
      String[] sPublicKeys = {mPublicKey};
      GpgWrapper.signEncrypt(outStream, sPublicKeys, sFileName, mSecretKey, mPass);
    }
}
