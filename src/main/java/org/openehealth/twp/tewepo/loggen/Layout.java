package org.openehealth.twp.tewepo.loggen;

import org.apache.log4j.Level;
import org.apache.log4j.NDC;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Pushes to the NDC the IP Address of the thread who called the logging event.
 * !!!!!!!!!!!!!!!! WARNING !!!!!!!!!!!!!!!! Logging automatically the IP can be
 * very slow on the system since every LoggingEvent that uses this
 * ConversionPattern will also log the IP Address which in many cases will be:
 * [127.0.0.1] aka [localhost]
 * 
 * @author Cristian Bejenariu (cri5tib@yahoo.com)
 * @version $Id: Layout.java 3145 2007-05-27 17:25:55Z rsloboze $
 */

public class Layout extends PatternLayout {

	Level logOnlyLevel;

	/** Creates a new instance of Layout */
	public Layout() {
		super();
	}

	/**
	 * 
	 * @param s
	 */
	public Layout(String s) {
		super(s);
	}

	/**
	 * 
	 * @param l
	 */
	public void setLogOnlyLevel(Level l) {
		logOnlyLevel = l;
	}

	/**
	 * 
	 */
	@Override
	public String format(LoggingEvent event) {
		String r = "";
		if (logOnlyLevel != null) {
			if (event.getLevel().equals(logOnlyLevel)) {
				NDC.push(getIp());
				r = super.format(event);
				NDC.pop();
				return r;
			}
		} else {
			NDC.push(getIp());
			r = super.format(event);
			NDC.pop();
		}
		return r;
	}

	/**
	 * 
	 * @return
	 */
	public String getIp() {
		String ip = "";
		try {
			java.net.InetAddress i = java.net.InetAddress.getLocalHost();
			ip = i.getHostAddress();
		} catch (Exception e) {
			return "NO IP";
		}
		return ip;
	}
}
