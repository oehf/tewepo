package main;

import org.openehealth.twp.tewepo.helper.*;
public class app {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BCrypt crypt = new BCrypt();
		
		System.out.println(crypt.hashpw("1234567", crypt.gensalt()));

		
		
	}

}
