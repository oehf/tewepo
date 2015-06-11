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
package org.openehealth.twp.tewepo.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openehealth.twp.tewepo.loggen.Layout;


/**
 * Servlet implementation for logging usage and errors.
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class Log4jInit extends HttpServlet {

	private Logger logger = Logger.getLogger(Log4jInit.class);

	/**
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() {
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j-init-file");
		// if the log4j-init-file is not set, then no point in trying
		// if(file != null) {
		// PropertyConfigurator.configure(prefix+file);
		// }else {
		try {
			// Layout for the different Logging levels
			Layout layoutInfo = new Layout(
					"[%d{MMM dd HH:mm:ss}] %-5p - [%x] (%F:%L) - %m%n");
			// Logger
			Logger rootLogger = Logger.getRootLogger();
			// set level 
			rootLogger.setLevel(Level.INFO);

			// FileAppender
			DailyRollingFileAppender dailyFileAppenderError = new DailyRollingFileAppender(
					layoutInfo, prefix + "logs/error.log", "'.'yyyy-MM-dd");
			dailyFileAppenderError.setThreshold(Level.ERROR);
			DailyRollingFileAppender dailyFileAppenderInfo = new DailyRollingFileAppender(
					layoutInfo, prefix + "logs/info.log", "'.'yyyy-MM-dd");
			dailyFileAppenderInfo.setThreshold(Level.INFO);
			// DailyRollingFileAppender dailyFileAppenderDebug = new
			// DailyRollingFileAppender(
			// layoutInfo, prefix + "logs/debug.log",
			// "'.'yyyy-MM-dd");
			// dailyFileAppenderDebug.setThreshold(Priority.DEBUG);

			// Appender
			rootLogger.addAppender(new ConsoleAppender(layoutInfo));
			rootLogger.addAppender(dailyFileAppenderError);
			rootLogger.addAppender(dailyFileAppenderInfo);
			// rootLogger.addAppender(dailyFileAppenderDebug);

		} catch (Exception ex) {
			Logger.getRootLogger().debug(ex);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) {

	}
}
