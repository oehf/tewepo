package org.openehealth.twp.tewepo.helper;

/**
 * Taken from: http://www.javapractices.com/Topic96.cjp<br>
 * Convenience methods for altering special characters related to URLs, regular
 * expressions, and HTML tags.
 */
public final class EscapeChars {

	// /**
	// * Synonym for <tt>URLEncoder.encode(String, "UTF-8")</tt>.
	// *
	// * <P>
	// * Used to ensure that HTTP query strings are in proper form, by escaping
	// * special characters such as spaces.
	// *
	// * <P>
	// * An example use case for this method is a login scheme in which, after
	// * successful login, the user is redirected to the "original" target
	// * destination. Such a target might be passed around as a request
	// parameter.
	// * Such a request parameter will have a URL as its value, as in
	// * "LoginTarget=Blah.jsp?this=that&blah=boo", and would need to be
	// * URL-encoded in order to escape its special characters.
	// *
	// * <P>
	// * It is important to note that if a query string appears in an
	// * <tt>HREF</tt> attribute, then there are two issues - ensuring the query
	// * string is valid HTTP (it is URL-encoded), and ensuring it is valid HTML
	// * (ensuring the ampersand is escaped).
	// */
	// public static String forURL(String aURLFragment) {
	// String result = null;
	// try {
	// result = URLEncoder.encode(aURLFragment, "UTF-8");
	// } catch (UnsupportedEncodingException ex) {
	// throw new RuntimeException("UTF-8 not supported", ex);
	// }
	// return result;
	// }
	//
	// /**
	// * Replace characters having special meaning <em>inside</em> HTML tags
	// * with their escaped equivalents, using character entities such as
	// * <tt>'&amp;'</tt>.
	// *
	// * <P>
	// * The escaped characters are :
	// * <ul>
	// * <li> <
	// * <li> >
	// * <li> "
	// * <li> '
	// * <li> \
	// * <li> &
	// * </ul>
	// *
	// * <P>
	// * This method ensures that arbitrary text appearing inside a tag does not
	// * "confuse" the tag. For example, <tt>HREF='Blah.do?Page=1&Sort=ASC'</tt>
	// * does not comply with strict HTML because of the ampersand, and should
	// be
	// * changed to <tt>HREF='Blah.do?Page=1&amp;Sort=ASC'</tt>. This is
	// * commonly seen in building query strings. (In JSTL, the c:url tag
	// performs
	// * this task automatically.)
	// */
	// public static String forHTMLTag(String aTagFragment) {
	// final StringBuffer result = new StringBuffer();
	//
	// final StringCharacterIterator iterator = new StringCharacterIterator(
	// aTagFragment);
	// char character = iterator.current();
	// while (character != StringCharacterIterator.DONE) {
	// if (character == '<') {
	// result.append("&lt;");
	// } else if (character == '>') {
	// result.append("&gt;");
	// } else if (character == '\"') {
	// result.append("&quot;");
	// } else if (character == '\'') {
	// result.append("&#039;");
	// } else if (character == '\\') {
	// result.append("&#092;");
	// } else if (character == '&') {
	// result.append("&amp;");
	// } else {
	// // the char is not a special one
	// // add it to the result as is
	// result.append(character);
	// }
	// character = iterator.next();
	// }
	// return result.toString();
	// }
	//
	// public static String forDB(String sqlString) {
	// final StringBuffer result = new StringBuffer();
	//
	// final StringCharacterIterator iterator = new StringCharacterIterator(
	// sqlString);
	// char character = iterator.current();
	// while (character != StringCharacterIterator.DONE) {
	// if (character == '\'') {
	// result.append("''");
	// } else {
	// // the char is not a special one
	// // add it to the result as is
	// result.append(character);
	// }
	// character = iterator.next();
	// }
	// return result.toString();
	// }
	//
	// public static String forXMLText(String aTagFragment) {
	// final StringBuffer result = new StringBuffer();
	//
	// final StringCharacterIterator iterator = new StringCharacterIterator(
	// aTagFragment);
	// char character = iterator.current();
	// while (character != StringCharacterIterator.DONE) {
	// if (character == '<') {
	// result.append("<");
	// } else if (character == '>') {
	// result.append("&gt;");
	// } else if (character == '\\') {
	// result.append("&#092;");
	// } else if (character == '&') {
	// result.append("&amp;");
	// } else {
	// // the char is not a special one
	// // add it to the result as is
	// result.append(character);
	// }
	// character = iterator.next();
	// }
	// return result.toString();
	// }
	//
	// /**
	// * Return <tt>aText</tt> with all start-of-tag and end-of-tag characters
	// * replaced by their escaped equivalents.
	// *
	// * <P>
	// * If user input may contain tags which must be disabled, then call this
	// * method, not {@link #forHTMLTag}. This method is used for text appearing
	// * <em>outside</em> of a tag, while {@link #forHTMLTag} is used for text
	// * appearing <em>inside</em> an HTML tag.
	// *
	// * <P>
	// * It is not uncommon to see text on a web page presented erroneously,
	// * because <em>all</em> special characters are escaped (as in
	// * {@link #forHTMLTag}). In particular, the ampersand character is often
	// * escaped not once but <em>twice</em> : once when the original input
	// * occurs, and then a second time when the same item is retrieved from the
	// * database. This occurs because the ampersand is the only escaped
	// character
	// * which appears in a character entity.
	// */
	// public static String toDisableTags(String aText) {
	// final StringBuffer result = new StringBuffer();
	// final StringCharacterIterator iterator = new StringCharacterIterator(
	// aText);
	// char character = iterator.current();
	// while (character != StringCharacterIterator.DONE) {
	// if (character == '<') {
	// result.append("&lt;");
	// } else if (character == '>') {
	// result.append("&gt;");
	// } else {
	// // the char is not a special one
	// // add it to the result as is
	// result.append(character);
	// }
	// character = iterator.next();
	// }
	// return result.toString();
	// }
	//
	// /**
	// * Replace characters having special meaning in regular expressions with
	// * their escaped equivalents.
	// *
	// * <P>
	// * The escaped characters include :
	// * <ul>
	// * <li>.
	// * <li>\
	// * <li>?, * , and +
	// * <li>&
	// * <li>:
	// * <li>{ and }
	// * <li>[ and ]
	// * <li>( and )
	// * <li>^ and $
	// * </ul>
	// *
	// */
	// public static String forRegex(String aRegexFragment) {
	// final StringBuffer result = new StringBuffer();
	//
	// final StringCharacterIterator iterator = new StringCharacterIterator(
	// aRegexFragment);
	// char character = iterator.current();
	// while (character != StringCharacterIterator.DONE) {
	// /*
	// * All literals need to have backslashes doubled.
	// */
	// if (character == '.') {
	// result.append("\\.");
	// } else if (character == '\\') {
	// result.append("\\\\");
	// } else if (character == '?') {
	// result.append("\\?");
	// } else if (character == '*') {
	// result.append("\\*");
	// } else if (character == '+') {
	// result.append("\\+");
	// } else if (character == '&') {
	// result.append("\\&");
	// } else if (character == ':') {
	// result.append("\\:");
	// } else if (character == '{') {
	// result.append("\\{");
	// } else if (character == '}') {
	// result.append("\\}");
	// } else if (character == '[') {
	// result.append("\\[");
	// } else if (character == ']') {
	// result.append("\\]");
	// } else if (character == '(') {
	// result.append("\\(");
	// } else if (character == ')') {
	// result.append("\\)");
	// } else if (character == '^') {
	// result.append("\\^");
	// } else if (character == '$') {
	// result.append("\\$");
	// } else {
	// // the char is not a special one
	// // add it to the result as is
	// result.append(character);
	// }
	// character = iterator.next();
	// }
	// return result.toString();
	// }

	/**
	 * Copied and slightly adapted from:
	 * http://www.borg-perg.asn-linz.ac.at/informatik
	 * /javainsel/javainsel_170027.
	 * htm#RxxxJava170027256TextinHTMLkonformenTextumwandeln
	 */
	/**
	 * This method escape Strings
	 * 
	 * @param s
	 *            the email address to be checked
	 * @return
	 */
	public static String escape(String s) {
		int len = s.length();
		StringBuffer sb = new StringBuffer(len * 5 / 4);
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (c == '&') {
				sb.append("&amp;");
			} else if (c == '<') {
				sb.append("&lt;");
			} else if (c == '>') {
				sb.append("&gt;");
			} else if (c == '\"') {
				sb.append("&quot;");
			} else {
				String elem = htmlchars[c & 0xff];
				sb.append(elem == null ? "" + c : elem);
			}
		}
		return sb.toString();
	}

	
	private static String htmlchars[] = new String[256];
	static {
		String entry[] = { "nbsp", "iexcl", "cent", "pound", "curren", "yen",
				"brvbar", "sect", "uml", "copy", "ordf", "laquo", "not", "shy",
				"reg", "macr", "deg", "plusmn", "sup2", "sup3", "acute",
				"micro", "para", "middot", "cedil", "sup1", "ordm", "raquo",
				"frac14", "frac12", "frac34", "iquest", "Agrave", "Aacute",
				"Acirc", "Atilde", "Auml", "Aring", "AElig", "CCedil",
				"Egrave", "Eacute", "Ecirc", "Euml", "Igrave", "Iacute",
				"Icirc", "Iuml", "ETH", "Ntilde", "Ograve", "Oacute", "Ocirc",
				"Otilde", "Ouml", "times", "Oslash", "Ugrave", "Uacute",
				"Ucirc", "Uuml", "Yacute", "THORN", "szlig", "agrave",
				"aacute", "acirc", "atilde", "auml", "aring", "aelig",
				"ccedil", "egrave", "eacute", "ecirc", "euml", "igrave",
				"iacute", "icirc", "iuml", "eth", "ntilde", "ograve", "oacute",
				"ocirc", "otilde", "ouml", "divid", "oslash", "ugrave",
				"uacute", "ucirc", "uuml", "yacute", "thorn", "yuml" };
		htmlchars['&'] = "&";
		htmlchars['<'] = "<";
		htmlchars['>'] = ">";
		for (int c = '\u00A0', i = 0; c <= '\u00FF'; c++, i++)
			htmlchars[c] = "&" + entry[i] + ";";
		for (int c = '\u0083', i = 131; c <= '\u009f'; c++, i++)
			htmlchars[c] = "&#" + i + ";";
		htmlchars['\u0088'] = htmlchars['\u008D'] = htmlchars['\u008E'] = null;
		htmlchars['\u008F'] = htmlchars['\u0090'] = htmlchars['\u0098'] = null;
		htmlchars['\u009D'] = null;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		String s = "Das ist³ <B>!<I>HTML in Tägs</I>&</B>!\"";
		System.out.println("-> " + s);
		System.out.println("<- " + EscapeChars.escape(s));
	}

}