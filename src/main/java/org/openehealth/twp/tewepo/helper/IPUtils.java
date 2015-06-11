package org.openehealth.twp.tewepo.helper;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.text.StrTokenizer;
import org.apache.log4j.Logger;

/**
 * Look for “x-forwarded-for” header.
 * If header exists, get the first IP.
 * Check that:
 * IP is valid.
 * IP is not a private IP.
 * If IP passes these 2 tests. Return this IP. If not move to the next IP and do the same test and so on.
 * If header doesn’t exist. Return the IP from calling “request.getRemoteAddr”.
 * @author Bashan
 *
 */
public class IPUtils {
	
	private static Logger logger = Logger.getLogger("webportal");
	
	public static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	public static final Pattern pattern = Pattern.compile("^(?:" + _255 + "\\.){3}" + _255 + "$");
	
	/**
	 * 
	 * @param longIP
	 * @return
	 */
	public static String longToIpV4(long longIP){
		int octet3 = (int) ((longIP >> 24) % 256);
	    int octet2 = (int) ((longIP >> 16) % 256);
	    int octet1 = (int) ((longIP >> 8) % 256);
	    int octet0 = (int) ((longIP) % 256);
	    //Aufbau der IP
	    return octet3 + "." + octet2 + "." + octet1 + "." + octet0;
		
	}
	
	/**
	 * 
	 * @param ip
	 * @return
	 */
	public static long ipV4ToLong(String ip){
		 String[] octets = ip.split("\\.");
		    return (Long.parseLong(octets[0]) << 24) + (Integer.parseInt(octets[1]) << 16) +
		        (Integer.parseInt(octets[2]) << 8) + Integer.parseInt(octets[3]);
	}
	
	/**
	 * 
	 * @param ip
	 * @return
	 */
	 public static boolean isIPv4Private(String ip) {
		    long longIp = ipV4ToLong(ip);
		    return (longIp >= ipV4ToLong("10.0.0.0") && longIp <= ipV4ToLong("10.255.255.255")) ||
		        (longIp >= ipV4ToLong("172.16.0.0") && longIp <= ipV4ToLong("172.31.255.255")) ||
		        longIp >= ipV4ToLong("192.168.0.0") && longIp <= ipV4ToLong("192.168.255.255");
		  }
	 /**
	  * 
	  * @param ip
	  * @return
	  */
		  public static boolean isIPv4Valid(String ip) {
		    return pattern.matcher(ip).matches();
		  }
		  /**
		   * 
		   * @param request
		   * @return
		   */
		  public static String getIpFromRequest(HttpServletRequest request) {
		    String ip;
		    boolean found = false;
		    if ((ip = request.getHeader("x-forwarded-for")) != null) {
		      StrTokenizer tokenizer = new StrTokenizer(ip, ",");
		      while (tokenizer.hasNext()) {
		        ip = tokenizer.nextToken().trim();
		        if (isIPv4Valid(ip) && !isIPv4Private(ip)) {
		          found = true;
		          break;
		        }
		      }
		    }
		    if (!found) {
		      ip = request.getRemoteAddr();
		    }
		   
		    return ip;
		  }
	
}
