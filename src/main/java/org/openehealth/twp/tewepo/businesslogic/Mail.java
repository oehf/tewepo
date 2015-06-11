/*
 * Copyright 2009 Manuel Carrasco Moñino. (manuel_carrasco at users.sourceforge.net) 
 * http://code.google.com/p/gwtupload
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.openehealth.twp.tewepo.businesslogic;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.openehealth.twp.tewepo.configuration.Configuration;


/**
 * @author zeja
 */
public class Mail {

	/** configuration */
	private final static Configuration conf = Configuration.getMainConfig("");

	private Logger logger = Logger.getLogger("webportal");

	/**
	 * This method send mail to the default recipient
	 * 
	 * @param subject
	 * @param text
	 * @throws Exception
	 */
	public void sendMail(String subject, String text) throws Exception {

		sendMail(conf.getProperty("defaultMailRecipient"), subject, text);
	}

	/**
	 * 
	 * @param recipientsAddress
	 * @param subject
	 * @param text
	 * @throws Exception
	 */
	public void sendMail(String recipientsAddress, String subject, String text)
			throws Exception {
		MailAuthenticator auth = new MailAuthenticator(conf
				.getProperty("mailUsername"), conf.getProperty("mailPassword"));

		Properties properties = new Properties();

		// set serveraddress to the properties
		properties.put("mail.smtp.host", conf.getProperty("mailSmtpHost"));

		// !!Important!! if the SMTP-Server requires authentication
		// must be at this point, the property is set to "true" 
		properties.put("mail.smtp.auth", "true");

		properties.put("mail.smtp.starttls.enable", conf
				.getProperty("mailSmtpStarttlsEnable"));
		

		logger.info("Mail - sendMail - SMTP Starttls: "+conf
				.getProperty("mailSmtpStarttlsEnable"));
		
		
		properties.put("mail.smtp.port", conf.getProperty("mailSmtpPort"));

		// with the Properties and implements Contructor
		// created
		// MailAuthenticator crestes a Session 
		Session session = Session.getInstance(properties, auth);

		// create new message
		Message msg = new MimeMessage(session);

		// set sender and recipient address 
		msg.setFrom(new InternetAddress(conf.getProperty("mailSenderAddress")));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
				recipientsAddress, false));

		// subject and Body from message are set
		msg.setSubject(subject);
		msg.setText(text);

		// set HEADER-Informationen 
		msg.setHeader("Test", "Test");
		msg.setSentDate(new Date());

		// send mail
		Transport.send(msg);

		logger.info("Mail - sendMail - Die e-Mail an " + recipientsAddress
				+ " wurde erfolgreich versendet.");
	}

	/**
	 * 
	 * @author devmis
	 *
	 */
	class MailAuthenticator extends Authenticator {

		
		private final String user;


		private final String password;

		/**
		 * constructor creates a MailAuthenticator Object<br>
		 * from the two parameters user and password.
		 * 
		 * @param user
		 *            String,  Username for the Mailaccount.
		 * @param password
		 *            String, password for the  Mailaccount.
		 */
		public MailAuthenticator(String user, String password) {
			this.user = user;
			this.password = password;
		}

		/**
		 * This method Methode returns a new  PasswortAuthentication Object
		 * 
		 * {@inheritDoc}
		 */
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(this.user, this.password);
		}
	}

}