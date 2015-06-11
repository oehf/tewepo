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
package org.openehealth.twp.tewepo.helper;

import java.io.File;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openehealth.twp.tewepo.businesslogic.InvalidValueException;
import org.openehealth.twp.tewepo.configuration.Configuration;


/**
 * Hier werden Methoden zusammen gefasst, die keiner anderen Klasse sinnvoll
 * zugeordnet werden koennen.
 * 
 */
public class Utility {

	/**
	 * Generates random strings with allowed characters.
	 * 
	 * @return generated random string
	 */
	public static String generateRandomString() {
		String allowedChars = "0123456789abcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		return generateRandomString(allowedChars, random);
	}

	/**
	 * Generates random strings with the given allowed characters.
	 * 
	 * @return generated random string
	 */
	private static String generateRandomString(String allowedChars,
			Random random) {
		int max = allowedChars.length();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int value = random.nextInt(max);
			buffer.append(allowedChars.charAt(value));
		}
		return buffer.toString();
	}

	/**
	 * This method checks if the email address is entered correctly
	 * (xyz@abc.com)
	 * 
	 * @param emailaddress
	 *            the email address to be checked
	 * @return true, if the email address exists; otherwise false.
	 */
	public static boolean isRichtigeEmailadresse(String emailaddress) {

		if (emailaddress != null && !emailaddress.equals("")) {
			Pattern p = Pattern
					.compile("(([a-zA-Z0-9][a-zA-Z0-9._%-]*[a-zA-Z0-9])|([a-zA-Z0-9]))@(([a-zA-Z0-9][a-zA-Z0-9._%-]*[a-zA-Z0-9])|[a-zA-Z0-9])+\\.[a-zA-Z]{2,4}");
			Matcher m = p.matcher(emailaddress);
			return m.matches();
		}
		return false;

	}
	
//	/**
//	 * This method checks if the name is entered correctly
//	 * 
//	 * @param name
//	 *            the name to be checked
//	 * @return true, if the name exists; otherwise false.
//	 */
//	public static boolean isRightTitle(String title) {
//
//		if (title == null || title.trim().equals("")) {
//			return false;
//		}
//
//		Pattern p = Pattern.compile("([a-zA-ZÄÖÜäöüß \\-().]*)");
//		Matcher m = p.matcher(title);
//
//		boolean test = m.matches();
//		if (test)
//			return true;
//		else
//			return false;
//	}

	/**
	 * This method checks if the name is entered correctly
	 * 
	 * @param name
	 *            the name to be checked
	 * @return true, if the name exists; otherwise false.
	 */
	public static boolean isRightName(String name) {

		if (name == null || name.trim().equals("")) {
			return false;
		}

		Pattern p = Pattern.compile("([a-zA-ZÄÖÜäöüß \\-]*)");
		Matcher m = p.matcher(name);

		boolean test = m.matches();
		if (test)
			return true;
		else
			return false;
	}

	/**
	 * This method checks if the text consists character that are allowed
	 * 
	 * @param text
	 *            the name to be checked
	 * @return true, if the name exists; otherwise false.
	 */
	public static boolean isRightString(String text) {

		if (text == null || text.trim().equals("")) {
			return false;
		}

		Pattern p = Pattern
				.compile("^([a-zA-Z0-9&öäüÖÄÜß]*\\-?)(\\p{javaWhitespace}([a-zA-Z0-9&öäüÖÄÜß]*)\\-?)*");
		Matcher m = p.matcher(text);

		boolean test = m.matches();
		if (test)
			return true;
		else
			return false;
	}

	/**
	 * This method checks if the loginname consists character that are allowed
	 * 
	 * @param text
	 *            the loginname to be checked
	 * @return true, if the loginname exists; otherwise false.
	 */
	public static boolean isRightLoginName(String text) {

		if (text == null || text.trim().equals("")) {
			return false;
		}

		Pattern p = Pattern.compile("[0-9a-zA-Z]{6,20}");
		Matcher m = p.matcher(text);

		boolean test = m.matches();
		if (test)
			return true;
		else
			return false;
	}

	/**
	 * This method checks if the String consists only of letters and numbers and
	 * has min. 6 und max. 20 character .
	 * 
	 * @param text
	 *            the name to be checked
	 * @return true, if the name exists; otherwise false.
	 */
	public static boolean isRightStandardString(String text) {

		if (text == null || text.trim().equals("")) {
			return false;
		}

		Pattern p = Pattern.compile("[0-9a-zA-ZÄÖÜäöüß]{6,20}");
		Matcher m = p.matcher(text);

		boolean test = m.matches();
		if (test)
			return true;
		else
			return false;
	}

	/**
	 * This method checks if the String consists only of letters and numbers and
	 * has min. 3 character
	 * 
	 * @param text
	 *            the name to be checked
	 * @return true, if the name exists; otherwise false.
	 */
	public static boolean isRightAddressString(String text) {

		if (text == null || text.trim().equals("")) {
			return false;
		}

		Pattern p = Pattern.compile("([a-zA-ZÄÖÜäöüß0-9\\- .]*){2,20}");
		Matcher m = p.matcher(text);

		boolean test = m.matches();
		if (test)
			return true;
		else
			return false;
	}

	/**
	 * This method checks if the zipcode consists character that are allowed
	 * 
	 * @param zipcode
	 *            the name to be checked
	 * @return true, if the name exists; otherwise false.
	 */
	public static boolean isRightZipcode(String zipcode) {

		if (zipcode == null || zipcode.trim().equals("")) {
			return false;
		}

		Pattern p = Pattern.compile("^\\d{5}$");
		Matcher m = p.matcher(zipcode);

		boolean test = m.matches();
		if (test)
			return true;
		else
			return false;
	}

	/**
	 * This method checks if the password is entered correctly
	 * 
	 * @param password
	 *            the password to be checked
	 * @return true, if the password exists; otherwise false.
	 */
	public static boolean isRightPassword(String password) {

		if (password == null || password.trim().equals("")
				|| password.trim().length() < 6) {
			return false;
		}
		Pattern p = Pattern.compile("*");// ^((?=.*[^a-zA-Z])(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{6,20})$");
		Matcher m = p.matcher(password);
		return m.matches();
	}

	/**
	 * This method checks if the token exists for a university course and if it
	 * is correctly (it must not be empty and have not more than 10 characters )
	 * 
	 * @param token
	 *            the testet token
	 * @return true, if the token conforms the requirements; otherwise false
	 */
	public static boolean isRightToken(String token) {
		return !(token == null || token.trim().equals("") || token.trim()
				.length() > 10);
	}

	/**
	 * Gets all allowed file endings from property file
	 * 
	 * @return Vector with all allowed file endings from the property file
	 */
	public static Vector<String> getAllowedFileExtension() {
		Vector<String> allowedFileExtension = new Vector<String>();

		
		String allowedEnding = Configuration.getMainConfig("").getProperty(
				"erlaubteEndungen");

		StringTokenizer tokEndungen = new StringTokenizer(allowedEnding, ".");
		// read endings in vector
		while (tokEndungen.hasMoreTokens()) {
			allowedFileExtension.add(tokEndungen.nextToken());
		}
		// for later testing:
		// if and only allowed if at least one allowable file extension in
		//Vector with the real over matches.
		return allowedFileExtension;
	}

	/**
	 * Gets file ending from the forwarded filename
	 * 
	 * @param filename
	 *            forwarded filename
	 * @return file ending from forwarded filename
	 */
	public static String getFileExtension(String filename) {
		StringTokenizer tokFilename = new StringTokenizer(filename, ".");

		String fileEnding = "";
		while (tokFilename.hasMoreElements()) {
			fileEnding = tokFilename.nextToken();
		}
		/*
		 * now fileEnding ist really the last file extension(z.B. neu.txt.exe ->
		 * exe)
		 */

		return fileEnding;
	}

	/**
	 * Ensures that the filename exists a valid extension
	 * 
	 * @param filename
	 * 
	 * @return "", if evrything ok or an error description, if the filename has
	 *         no valid extension
	 * 
	 */
	public static String hasAllowedExtension(String filename) {

		boolean allowed = true; // no file is allowed
		String message = "";
		if (filename != null) {
			Vector<String> allowedEnding = Utility.getAllowedFileExtension();
			allowed = false;
			String fileEnding = Utility.getFileExtension(filename);

			for (int i = 0; !allowed && i < allowedEnding.size(); i++) {
				allowed = fileEnding.equalsIgnoreCase(allowedEnding
						.elementAt(i));
			}
		}

		if (!allowed) {
			Vector<String> allowedEnding = Utility.getAllowedFileExtension();

			message += "Die Dateiendung ist nicht erlaubt!<br>"
					+ "Zul&auml;ssige Dateiendungen:<br>.";

			for (int i = 0; i < allowedEnding.size(); i++) {
				message += allowedEnding.elementAt(i);
				if (i < allowedEnding.size() - 1) {
					message += ", .";
				}
			}
			return message;
		}
		return "";
	}

	/**
	 * Checked, if the forwarded file is smaller than the given maximum size ,
	 * has no special characters and contains an allowed file extension
	 * 
	 * @param file
	 * @param maxSize
	 *            in Byte
	 * @throws InvalidValueException
	 *             if the file is too high or has a invalid file extension
	 */
	public static void checkFile(File file, int maxSize)
			throws InvalidValueException {
		String filename = "", message = "";

		if (file != null) {

			filename = file.getName();
			if (file.length() > maxSize) {
				message += "Die Gr&ouml;sse der Datei darf nicht gr&ouml;&szlig;er als "
						+ maxSize / 1024 + " KB sein!<br>";
			}

			message += hasAllowedExtension(filename);
			filename = Utility.replaceUmlauts(filename);
			if (!filename.equals(EscapeChars.escape(filename))) {
				//when characters are included in the file name that were escaped
				message += "Der Dateiname darf keine Sonderzeichen enthalten!<br>";
			}
		}

		if (!message.equals("")) {
			throw new InvalidValueException(message);
		}
	}

	/**
	 * Replace the umlauts  Ã¤,Ã¶,Ã¼,Ã„,Ã–,Ãœ,ÃŸ in the forwarded String and
	 * gives a String  back, in this is changed
	 */
	public static String replaceUmlauts(String string) {
		String neu = string;
		neu = neu.replace("Ã¤", "ae");
		neu = neu.replace("Ã¶", "oe");
		neu = neu.replace("Ã¼", "ue");
		neu = neu.replace("Ã„", "Ae");
		neu = neu.replace("Ã–", "Oe");
		neu = neu.replace("Ãœ", "Ue");
		neu = neu.replace("ÃŸ", "ss");
		return neu;
	}

	/**
	 * Checks an semester to the following format WS2004/2005 or SS2007
	 * 
	 * @param string
	 *           with toUpperCase, ws is not complaining
	 * @return true if correct
	 */
	public static boolean isCorrectSemester(String sem) {

		if (sem.length() == 6 || sem.length() == 11) {
			String beginning = sem.substring(0, 2);
			if ((beginning.equals("WS") && sem.length() == 11)
					|| (beginning.equals("SS") && sem.length() == 6)) {
				if (beginning.equals("WS")) {
					// WS
					String end = sem.substring(2);
					String firstPart = end.substring(0, 4);
					String separator = end.substring(4, 5);
					String secondPart = end.substring(5);

					int firstNumber = 0, secondNumber = 0;
					try {
						firstNumber = Integer.parseInt(firstPart);
						secondNumber = Integer.parseInt(secondPart);
					} catch (NumberFormatException e) {
						return false;
					}
					if (((2100 > firstNumber) && (firstNumber > 1990))
							&& ((2100 > secondNumber) && (secondNumber > 1990))
							&& (firstNumber == secondNumber - 1)
							&& (separator.equals("/"))) {
						return true;
					} else {
						return false;
					}

				} else {
					// SS
					String end = sem.substring(2);
					String firstPart = end.substring(0, 4);
					int firstNumber = 0;
					try {
						firstNumber = Integer.parseInt(firstPart);

					} catch (NumberFormatException e) {
						return false;
					}
					if (((2100 > firstNumber) && (firstNumber > 1990))) {
						return true;
					} else {
						return false;
					}
				}

			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}