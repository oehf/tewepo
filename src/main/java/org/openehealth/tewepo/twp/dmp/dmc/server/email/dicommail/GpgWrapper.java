/* Created: 12.31.03 18:11:25
 * Modifies 16.02.06 getKeyID
 **
Set of functions for using GnuPG from java programms.
Supported some key menegment functons and encrypt/decrypt.
In case of errors wrapperException will be thrown
 **
 */

package org.openehealth.tewepo.twp.dmp.dmc.server.email.dicommail;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Vector;

import org.openehealth.tewepo.twp.dmp.dmc.server.config.Configuration;



/**
 * Wrapper class for GPG
 * @author DBridge Tool
 *
 */
public class GpgWrapper {

	/**
	 * Sets the GPG Path 
	 * @param gpgPath
	 */
	public static void setGpgPath(String gpgPath) {
		Init(gpgPath);
	}

	/**
	 * Gets the GPG Path
	 * @return path as String
	 */
	public static String getGpgPath() {
		return gpgPath;
	}

	/**
	 * Initialize the GPG Path
	 * @param inGpgPath
	 */
	static void Init(String inGpgPath) {
		if (inGpgPath.length() != 0) {
			if (inGpgPath.charAt(inGpgPath.length() - 1) != File.separatorChar){
				gpgPath = inGpgPath + File.separator;
			}else{
				gpgPath = inGpgPath;
			}
		} else {
			gpgPath = new String();
		}
	}

	/**
	 * Export key Input: String keyID: id of key (it is good idea to use key
	 * fingerprint as id) String outFileName: file name where to store exported
	 * key boolean isSecret: which key should be exported (true - for secret
	 * key) boolean needArmor: if true key will be exported in ASCII else in
	 * binary format
	 * 
	 * @param keyID
	 * @param outFileName
	 * @param isSecret
	 * @param needArmor
	 * @throws wrapperException
	 */
	public static void exportKey(String keyID, String outFileName,
			boolean isSecret, boolean needArmor) throws wrapperException {
		boolean errorFlag = false;
		// --------------New command generation--------------------
		Vector cmdVec = new Vector(8, 1);
		cmdVec.add(gpgPath);
		cmdVec.add("--batch");
		cmdVec.add("--no-tty");
		cmdVec.add("--yes");
		if (needArmor) {
			// create ACII Armored file
			cmdVec.add("--armor");
		}
		cmdVec.add("--output");
		cmdVec.add(outFileName);
		if (!isSecret) {
			cmdVec.add("--export");
		} else {
			cmdVec.add("--export-secret-keys");
		}
		cmdVec.add(keyID);
		String[] command = getCommand(cmdVec);
		// ----------------------------------------------------------

		Runtime currentRT = Runtime.getRuntime();
		try {
			Process gpgProc = currentRT.exec(command);
			if (gpgProc.waitFor() != 0)
				errorFlag = true;
		} catch (Exception e) {
			throw new wrapperException("ExportKey Exeception: "
					+ e.getMessage());
		}
		if (errorFlag) {
			throw new wrapperException("Error when running GnuPG.\n Command "
					+ getCommandString(command));
		}
	} // end exportKey()



	
	/**
	 * Encryption. Multiple recipient Encrypt file for several (1 and more)
	 * recipients. Input: String inFileName: file for encryption. String[]
	 * recipients: array with recipients id's (it is good idea to use key
	 * fingerprint as id). String outFileName: file name for encrypted
	 * information
	 * 
	 * @param osDataStream
	 * @param recipients
	 * @param outFileName
	 * @throws wrapperException
	 */
	public static void encrypt(ByteArrayOutputStream osDataStream,
			String[] recipients, String outFileName) throws wrapperException {
		boolean errorFlag = false;
		// --------------New command generation--------------------
		Vector cmdVec = new Vector(13);
		cmdVec.add(gpgPath);
		cmdVec.add("--batch");
		cmdVec.add("--no-tty");
		cmdVec.add("--yes");
		cmdVec.add("--status-fd");
		cmdVec.add("1");
		cmdVec.add("--armor");
		cmdVec.add("--trust-model");
		cmdVec.add("always");
		cmdVec.add("--output");
		cmdVec.add(outFileName);
		cmdVec.add("--encrypt");
		for (int recCount = 0; recCount < recipients.length; recCount++) {
			cmdVec.add("--recipient");
			cmdVec.add(recipients[recCount]);
		}

		// cmdVec.add(inFileName);
		String[] command = getCommand(cmdVec);
		// --------------------------------------------------------
		Runtime currentRT = Runtime.getRuntime();
		try {
			Process gpgProc = currentRT.exec(command);
			// -----------------------------------------------------------
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(gpgProc
					.getOutputStream()), true);
			osDataStream.writeTo(gpgProc.getOutputStream());
			writer.close();
			// -----------------------------------------------------------
			// ::::::::::::::Output streams::::::::::::::::
			BufferedReader in = new BufferedReader(new InputStreamReader(
					gpgProc.getInputStream()));
			CStreamReader inputReader = new CStreamReader("EncryptOutput", in);
			new Thread(inputReader).start();
			// ::::::::::::::::::::::::::::::::::::::::
			if (gpgProc.waitFor() != 0)
				errorFlag = true;
		} catch (Exception e) {
			throw new wrapperException("Encrypt Exeception: " + e.getMessage());
		}
		if (errorFlag) {
			throw new wrapperException("Error when running GnuPG.\nCommand "
					+ getCommandString(command));
		}
	} // end encrypt()



	/**
	 * Sign without changing input information (--clearsign or --detach-sign)
	 * Parameters: ByteArrayOutputStream osDataStream: DataStream with data for
	 * signing; String outFileName: file to store results: information from
	 * input file + signature in case of --clearsign or signature only in case
	 * of --detach-sign. String digestAlgo: name of HASH function for signature
	 * generation (currenly not in use) String signer: keyid for signature
	 * generation. String passPhrase: secret key protection passphrase. boolean
	 * isDetached: create detached signature if TRUE.
	 * 
	 * @param osDataStream
	 * @param outFileName
	 * @paramsigner
	 * @param passPhrase
	 * @param isDetached
	 * @throws wrapperException
	 */
	public static void noChangeSign(ByteArrayOutputStream osDataStream,
			String outFileName,/* String digestAlgo, */String signer, String passPhrase,
			boolean isDetached) throws wrapperException {
		boolean errorFlag = false;

		Vector cmdVec = new Vector(18);
		cmdVec.add(gpgPath);
		cmdVec.add("--batch");
		cmdVec.add("--no-tty");
		cmdVec.add("--yes");
		cmdVec.add("--status-fd");
		cmdVec.add("1");
		cmdVec.add("--passphrase-fd");
		cmdVec.add("0");
		cmdVec.add("--armor");
		// cmdVec.add("--digest-algo");
		// cmdVec.add(digestAlgo);
		cmdVec.add("--default-key");
		cmdVec.add(signer);
		cmdVec.add("--output");
		cmdVec.add(outFileName);
		if (isDetached) {
			cmdVec.add("--detach-sign");
		} else {
			cmdVec.add("--clearsign");
		}
		// cmdVec.add(inFileName);
		String[] command = getCommand(cmdVec);
		Runtime currentRT = Runtime.getRuntime();
		try {
			Process gpgProc = currentRT.exec(command);
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(gpgProc
					.getOutputStream()), true);
			writer.println(passPhrase);
			// -----------------------------------------------------------
			osDataStream.writeTo(gpgProc.getOutputStream());
			// writer.println();
			// -----------------------------------------------------------
			writer.close();
			// ::::::::::::::Output streams::::::::::::::::
			BufferedReader in = new BufferedReader(new InputStreamReader(
					gpgProc.getInputStream()));
			CStreamReader inputReader = new CStreamReader("SignOutput", in);
			new Thread(inputReader).start();
			// ::::::::::::::::::::::::::::::::::::::::
			if (gpgProc.waitFor() != 0)
				errorFlag = true;
		} catch (Exception ex) {
			throw new wrapperException("Sign Exception: " + ex.getMessage());
		}
		if (errorFlag) {
			throw new wrapperException("Error when running GnuPG.\nCommand "
					+ getCommandString(command));
		}
	}// end of noChangeSign

	/**
	 * Sign and encrypt Parameters: ByteArrayOutputStream osDataStream: Data to
	 * be signed. String[] recipients: list of recipeients (public keys for file
	 * encryption) String outFileName: file to store results; String digestAlgo:
	 * name of HASH function for signature generation (currenly not in use)
	 * String signer: keyid for signature generation. String passPhrase: secret
	 * key protection passphrase.
	 * 
	 * @param osDataStream
	 * @param recipients
	 * @param outFileName
	 * @param signer
	 * @param passPhrase
	 * @throws wrapperException
	 */
	public static void signEncrypt(ByteArrayOutputStream osDataStream,
			String[] recipients, String outFileName,
			/* String digestAlgo, */String signer, String passPhrase)
			throws wrapperException {
		boolean errorFlag = false;

		Vector cmdVec = new Vector(18);
		cmdVec.add("sudo");
		cmdVec.add(gpgPath);
		cmdVec.add("--batch");
		cmdVec.add("--no-tty");
		cmdVec.add("--yes");
		cmdVec.add("--status-fd");
		cmdVec.add("1");
		cmdVec.add("--passphrase-fd");
		cmdVec.add("0");
		cmdVec.add("--armor");
		cmdVec.add("--trust-model");
		cmdVec.add("always");
		// cmdVec.add("--digest-algo");
		// cmdVec.add(digestAlgo);
		cmdVec.add("--default-key");
		cmdVec.add(signer);
		cmdVec.add("--output");
		cmdVec.add(outFileName);
		for (int recCount = 0; recCount < recipients.length; recCount++) {
			cmdVec.add("--recipient");
			cmdVec.add(recipients[recCount]);
		}
		cmdVec.add("--sign");
		cmdVec.add("--encrypt");
		// cmdVec.add(inFileName);
		String[] command = getCommand(cmdVec);

		Runtime currentRT = Runtime.getRuntime();
		try {
			Process gpgProc = currentRT.exec(command);
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(gpgProc
					.getOutputStream()), true);
			writer.println(passPhrase);
			osDataStream.writeTo(gpgProc.getOutputStream());
			writer.close();
			// ::::::::::::::Output streams::::::::::::::::
			BufferedReader in = new BufferedReader(new InputStreamReader(
					gpgProc.getInputStream()));
			CStreamReader inputReader = new CStreamReader("SignEncryptOutput",
					in);
			new Thread(inputReader).start();
			// ::::::::::::::::::::::::::::::::::::::::
			if (gpgProc.waitFor() != 0) {
				errorFlag = true;
			}
		} catch (Exception ex) {
			throw new wrapperException("Sign and Encrypt Exception: "
					+ ex.getMessage());
		}
		if (errorFlag) {
			throw new wrapperException("Error when running GnuPG.\nCommand "
					+ getCommandString(command));
		}
	}// end of signEncrypt


	/** Generate key helpers functions */

	/**
	 * Helper functoin for key pair generation. Defines the expiration date for
	 * the key (and the subkey). It may be entered as number of days, weeks,
	 * month or years.
	 * 
	 * @param expTime
	 * @param timeSlice
	 * @throws wrapperException
	 * 
	 * @return result
	 */
	private static String getExpDate(int expTime, char timeSlice)
			throws wrapperException {
		String result = "Expire-Date: ";
		if ((timeSlice != 'd') && (timeSlice != 'w') && (timeSlice != 'm')
				&& (timeSlice != 'y'))
			throw new wrapperException(
					"Invalid time slice for expiration date.");
		if (expTime == 0) {
			result += 0;
		} else {
			result += expTime;
			result += timeSlice;
		}
		return result;
	}

	/**
	 * Helper functoin for key pair generation. Defines type and size of
	 * generated kei pair. type: 0: Generate DSA key for signing and ElGamal
	 * subkey for encryption keySize - length of ElGamal subKey in bits (768 is
	 * smallest value allowed). DSA keypair will have 1024 bits.
	 * 
	 * 1: Generate DSA key pair for sign only. keysize - length of the key in
	 * bits (can be in between 768 and 1024)
	 * 
	 * 2: Generate RSA key pair for sign only. keysize - length of the key in
	 * bits (1024 is smallest value allowed).
	 * 
	 * @param type
	 * @param keySize
	 * @throws wrapperException
	 * 
	 * @return result
	 * 
	 */
	private static String getKeyTypeAndLength(int type, int keySize)
			throws wrapperException {

		String keyType = "Key-Type: DSA";
		String subType = new String();
		String keyLength = "Key-Length: ";
		String subLength = new String();
		String result = new String();

		switch (type) {
		case 0:
			subType = "Subkey-Type: ELG-E";
			if (keySize < 768) {
				throw new wrapperException("768 is smallest value allowed.");
			}
			
			keyLength += 1024;
			subLength = "Subkey-Length: " + keySize;
			result = keyType + "\n" + keyLength + "\n" + subType + "\n"
					+ subLength;
			break;
		case 1:
			if ((keySize < 768) || (keySize > 1024)) {
				throw new wrapperException(
						"DSA only allows keysizes from 768 to 1024");
			}
			keyLength += keySize;
			result = keyType + "\n" + keyLength;
			break;
		case 2:
			keyType = "Key-Type: RSA";
			if (keySize < 1024) {
				throw new wrapperException(
						"1024 is smallest value allowed for RSA.");
			}
			keyLength += keySize;
			result = keyType + "\n" + keyLength;
			break;
		default:
			throw new wrapperException("Invalid type of key");
		}
		return result;
	}


	/**
	 * Gets the command
	 * 
	 * @param cmdVec
	 * @return command
	 * 
	 */
	private static String[] getCommand(Vector cmdVec) {
		String[] command = new String[cmdVec.size()];
		for (int argCount = 0; argCount < command.length; argCount++) {
			command[argCount] = (String) cmdVec.get(argCount);
		}
		return command;
	}

	
	/**
	 * Gets the command String
	 * 
	 * @param cmdStrings
	 * 
	 * @return
	 */
	private static String getCommandString(String[] cmdStrings) {
		String command = "";// new String;
		for (int argCount = 0; argCount < cmdStrings.length; argCount++) {
			command += cmdStrings[argCount];
			command += " ";
		}
		return command;
	}


	private static String gpgPath = Configuration.getMainConfig().getProperty(
			"gnupgApplicationPath");
}

class wrapperException extends Exception {
	/**
	 * Constructor
	 * 
	 * @param errStr
	 */
	public wrapperException(String errStr) {
		super(errStr);
	}
}

class badSignatureException extends Exception {
	/**
	 * Constructor
	 * @param errStr
	 */
	public badSignatureException(String errStr) {
		super(errStr);
	}
}


/**
 * @author Andrei
 */
class CStreamReader implements Runnable {
	
	private BufferedReader input;
	private String threadName;

	/**
	 * 
	 * @param threadName
	 * @param input
	 */
	public CStreamReader(String threadName, BufferedReader input) {
		this.input = input;
		this.threadName = threadName;
	}

	/**
	 * 
	 */
	public void run() {
		String currentLine;

		try {
			while ((currentLine = input.readLine()) != null) {

			}
		} catch (Exception e) {

		}

	}
}
