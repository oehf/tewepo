package org.openehealth.tewepo.twp.dmp.dmc.server.email.dicommail;

/**
 * <p>Title: dbridge</p>
 *
 * <p>Description: </p> X-Telemedicine Tag is a string with the following grammatic
 * 1. can be an ASCII string
 * 2. can be a DICOM StudyId
 * 3. <List Of symbols> = <Symbol>|<List Of symbols>
 * 4. <Symbol> = "-" | "_" | "." | "," | 0 | ... 9 | a | ... | z | A | ... Z
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

import javax.swing.JOptionPane;

/* modified 23.04.05 */
/* modified 15.12.05 */

/**
 * 
 */
public class XTag {
	private String m_tag;

	/**
	 * Default constructor. The XTag value is assigned to "My_X-Tag"
	 * 
	 */

	public XTag() {
		m_tag = new String("My_X-Tag");
	};

	/**
	 * Parametrized constructor. The input string is corrected and assigned as
	 * an XTag value
	 * 
	 */

	public XTag(String tag) {
		if (tag.compareTo("") == 0 || tag == null) {
			m_tag = new String("My_X-Tag");
		} else {
			m_tag = new String(correctTag(tag));
		}
	};

	/**
	 * Copy constructor
	 * 
	 * @param tag
	 *            XTag
	 */

	public XTag(XTag tag) {
		m_tag = new String(tag.toString());
	};

	/**
	 * assign the XTag value from the string tag without notification about any
	 * corrections
	 * 
	 * @param tag
	 *            String
	 */
	public void setText(String tag) {
		if (tag.compareTo("") != 0 && tag != null) {
			m_tag = new String(correctTag(tag));
		}
	};

	/**
	 * assign the XTag value from the string tag with a notification via GUI
	 * 
	 * @param tag
	 *            String
	 */
	public void setTextGUI(String tag) {
		this.setText(tag);
		if (tag.length() != m_tag.length()) {
			this.setText(tag);
			Object[] options = { "OK", "CANCEL" };
			JOptionPane
					.showOptionDialog(
							null,
							"X-Tag: "
									+ tag
									+ " >> "
									+ m_tag
									+ "\n"
									+ "<Symbol> = - | _ | . | , | 0 | ... 9 | a | ... | z | A | ... Z",
							"Warning", JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE, null, options,
							options[0]);
		}
	}

	/**
	 * check either the given string follows the XTag rules or not
	 * 
	 * @param tag
	 *            String
	 * @return boolean
	 */
	boolean isCorrect(String tag) {
		int n = tag.trim().length();
		int m = correctTag(tag).length();
		if (n == m)
			return true;
		else
			return false;
	};

	/**
	 * 
	 * @return String
	 */
	public String toString() {
		return m_tag;
	};

	/**
	 * 
	 * 
	 * @param tag
	 *            XTag
	 * @return int
	 */
	public int compareTo(XTag tag) {
		return m_tag.compareTo(tag.toString());
	}

	/**
	 * Correct the input string in according to XTag rules
	 * 
	 * @param tag
	 *            String
	 * @return String
	 */
	private String correctTag(String tag) {
		tag = tag.trim();
		int n = tag.length();
		String res = null;
		StringBuffer buf = new StringBuffer();
		int j = -1;
		for (int i = 0; i < n; i++) {
			char ch = tag.charAt(i);
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
					|| (ch >= '0' && ch <= '9') || ch == '_' || ch == '-'
					|| ch == '.' || ch == ',') {
				j++;
				buf.append(ch);
			}
		}// end for
		if (j != -1)
			res = new String(buf);
		return res;
	};

	// /**
	// * Return a GUID
	// * @param
	// * @return String
	// */
	// public String GUID(){
	// dbGUID guid=new dbGUID();
	// return guid.generateGUID();
	// }

	/**
	 * 
	 */
	public static void main(String[] args) {
		XTag xtag1 = new XTag();
		System.out.println(xtag1.toString());
		XTag xtag2 = new XTag(" 123.a,B_3-4");
		System.out.println(xtag2.toString());
		XTag xtag3 = new XTag(" s4%vb.;_gj?-1");
		System.out.println(xtag3.toString());
		XTag xtag4 = new XTag("AAAAAAAA+~BBBBBBB");
		System.out.println(xtag4.toString());

	}
}
