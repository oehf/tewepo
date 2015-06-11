package org.openehealth.tewepo.twp.dmp.dmc.server.email.dicommail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;



/**
 * The code of this class is taken from the source code DBridge
 */
public class dbridge_MailHelper {

}

/**
 * Sender that send message aplitting it into smaller parts as described in
 * RFC2046
 * 
 * @author mcdi
 * 
 */
class SplittingSender {

	private Logger logger = Logger.getLogger(SplittingSender.class);

	private int m_split_size;

	/**
	 * Splitting sender
	 * 
	 * @param split_size
	 */
	public SplittingSender(int split_size) {
		m_split_size = split_size;
	}

	/**
	 * Create unique partial message ID
	 * 
	 * @param host
	 * @return
	 */
	private String createUnquePartialMessageId(String host) {
		return String
				.valueOf(GregorianCalendar.getInstance().getTimeInMillis());
	}

	/**
	 * Send message
	 * 
	 * @param in_msg
	 * @param mSession
	 * @throws MessagingException
	 * @throws IOException
	 */
	protected void sendMessage(Message in_msg, Session mSession)
			throws MessagingException, IOException {
		String message_partial_id = createUnquePartialMessageId("localhost");
		//
		// Split message into the smaller parts
		//
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		in_msg.writeTo(os);
		if (os.size() <= m_split_size) {
			//
			// Send message in usual way
			//
			
			
			Transport.send(in_msg, in_msg.getAllRecipients());
			
		} else {
			logger.info("dbridge_MailHelper - SplitSize: " + m_split_size);
			logger.info("dbridge_MailHelper - ByteOutputStream.size: "
					+ os.size());
			//
			// Send message in smaller parts
			//
			Collection parts_coll = MessageBufferParser.parse(m_split_size, os
					.toByteArray());
			logger.info("dbridge_MailHelper -  Collection parts_coll.size: "
					+ parts_coll.size());
			Iterator it = parts_coll.iterator();
			logger.info("dbridge_MailHelper -  Iterator hasNext: "
					+ it.hasNext());
			int i = 0;
			while (it.hasNext()) {

				logger.info("dbridge_MailHelper -  session: "
						+ mSession.toString());

				MimeMessage msg = new MimeMessage(mSession);
				byte[] part_bytes = (byte[]) it.next();

				DataSource4PartialMsgContentType dataSource4PartialMsgContentType = new DataSource4PartialMsgContentType(
						part_bytes, message_partial_id, ++i, parts_coll.size());
				DataHandler dataHandler = new DataHandler(
						dataSource4PartialMsgContentType);
				msg.setDataHandler(dataHandler);

				msg.setFrom(in_msg.getFrom()[0]);
				msg.setRecipients(Message.RecipientType.TO, in_msg
						.getAllRecipients());
				msg.setSubject(in_msg.getSubject() + " (" + i + "/"
						+ parts_coll.size() + ")");
				msg.setSentDate(new Date());
				//
				// send the message
				//
				msg.saveChanges();
			}
		}
	}
}


/**
 * Class to process special Content-Type=Message/Partial
 * 
 * @author mcdi
 * 
 */
class DataSource4PartialMsgContentType implements DataSource {
	/**
	 * 
	 * @param part_bytes
	 * @param id
	 * @param i
	 * @param n
	 */
	public DataSource4PartialMsgContentType(byte[] part_bytes, String id,
			int i, int n) {
		m_part_bytes = part_bytes;
		mContentType = Rfc2046Names.TYPE_MESSAGE_PARTIAL + ";"
				+ Rfc2046Names.ATTR_ID + "=\"" + id + "\";"
				+ Rfc2046Names.ATTR_NUMBER + "=" + i + ";"
				+ Rfc2046Names.ATTR_TOTAL + "=" + n;
	}

	/**
	 * 
	 */
	public String getContentType() {
		return mContentType;
	}
	
	/**
	 * 
	 */
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(m_part_bytes);
	}

	/**
	 * 
	 */
	public String getName() {
		return "part";
	}

	/**
	 * 
	 */
	public OutputStream getOutputStream() throws IOException {
		return new ByteArrayOutputStream();
	}

	byte[] m_part_bytes;
	String mContentType;
}

/**
 * Class to split original message to the parts accordingly to RFC2046
 * 
 * @author mcdi
 * 
 */
class MessageBufferParser {
	
	/**
	 * 
	 * @param max_part_size
	 * @param message
	 * @return
	 */
	public static Collection parse(int max_part_size, byte[] message) {
		LinkedList out = new LinkedList();
		int curr_pos = 0;
		int messageLength = message.length;
		while (message.length - curr_pos > 0) {

			// 1. Fragmentation agents must split messages at line
			// boundaries only. This restriction is imposed because
			// splits at points other than the ends of lines in turn
			// depends on message transports being able to preserve
			// the semantics of messages that don't end with a CRLF
			// sequence. Many transports are incapable of preserving
			// such semantics.

			int lastCLRF = searchEosBack(curr_pos + max_part_size, message);
			byte[] message_part = Arrays.copyOfRange(message, curr_pos,
					lastCLRF);

			out.add(message_part);
			curr_pos = lastCLRF + 1;
		}
		return out;
	}

	/**
	 * 
	 * @param from
	 * @param arr
	 * @return
	 */
	static private int findNextNotCRLF(int from, byte[] arr) {
		for (int i = from; i < arr.length; ++i) {
			if (arr[i] != '\r' && arr[i] != '\n')
				return i;
		}
		return arr.length;
	}

	/**
	 * 
	 * @param from
	 * @param arr
	 * @return
	 */
	static private int searchEosForward(int from, byte[] arr) {
		for (int i = from; i < arr.length; ++i) {
			if (arr[i] == '\n')
				return i;
		}
		return arr.length;
	}

	/**
	 * 
	 * @param from
	 * @param arr
	 * @return
	 */
	static private int searchEosBack(int from, byte[] arr) {
		int n = from < arr.length ? from : arr.length - 1;
		for (int i = n; i >= 0; --i) {
			if (arr[i] == '\n')
				return i;
		}
		return 0;
	}
}

/**
 * Constants for the Names from RFC2046
 * 
 * @author mcdi
 * 
 */
class Rfc2046Names {
	public final static String TYPE_MESSAGE_PARTIAL = "message/partial";
	public final static String ATTR_ID = "id";
	public final static String ATTR_NUMBER = "number";
	public final static String ATTR_TOTAL = "total";
}
