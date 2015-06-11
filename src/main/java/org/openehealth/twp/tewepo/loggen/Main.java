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
package org.openehealth.twp.tewepo.loggen;

import org.apache.log4j.Logger;

/**
 * This class defines log messages.
 * 
 * @author Benjamin Schneider
 * 
 */
public class Main {
	private static Logger logger = Logger.getLogger(Main.class);

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// SimpleLayout layout = new SimpleLayout();
			// ConsoleAppender consoleAppender = new ConsoleAppender( layout );
			// logger.addAppender( consoleAppender );
			// FileAppender fileAppender = new FileAppender( layout,
			// "MeineLogDatei.log", false );
			// logger.addAppender( fileAppender );
			// // ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF:
			// logger.setLevel( Level.WARN );
		} catch (Exception ex) {
			System.out.println(ex);
		}
		logger.debug("Meine Debug-Meldung");
		logger.info("Meine Info-Meldung");
		logger.warn("Meine Warn-Meldung");
		logger.error("Meine Error-Meldung");
		logger.fatal("Meine Fatal-Meldung");
		logger.error("error: startseite wurde aufgerufen");
		logger.fatal("fatal: startseite wurde aufgerufen");
	}

}
