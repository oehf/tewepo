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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.openehealth.twp.tewepo.businesslogic.TreeNode;
import org.openehealth.twp.tewepo.configuration.Configuration;
import org.openehealth.twp.tewepo.helper.SessionGuard;


/**
 * Servlet implementation to show log files.
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class ShowLogFilesServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(ShowLogFilesServlet.class); //"webportal"

	/** the configuration */
	private final static Configuration conf = Configuration.getMainConfig("");

	/**
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	/**
	 * Opens the (selected or current) log file in the given path and shows the
	 * archiv tree on the web interface.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws ServletException
	 * @throws {@link IOException}
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// validate request
		SessionGuard.INSTANCE.validate(request);

		RequestDispatcher rd;

		/**
		 * Path of log files
		 */
		String logFilesFolderPath = getServletContext().getRealPath("/")
				+ conf.getProperty("pathLogFiles");

		/**
		 * Name of the log file to open
		 */
		String logfilename = request.getParameter("logfilename");

		/**
		 * Path of the lof file to open
		 */
		String fullLogfilePath = logFilesFolderPath + logfilename;

		/**
		 * Path of the current log file (today); this won't be shown in the tree
		 */
		String aktuellInfoLogfilePath = logFilesFolderPath + "info.log";

		/**
		 * If a log file from the archiv is selected, this will be opened,
		 * otherwise the current file (of today).
		 */
		if (logfilename != null && !logfilename.equals("")) {
			request.setAttribute("logfile", openLogFile(fullLogfilePath));
		} else {
			request
					.setAttribute("logfile",
							openLogFile(aktuellInfoLogfilePath));
		}

		// pattern String: "1|0|2009|#" --> nodeId | parentNodeId | nodeName |
		// nodeUrl
		String[] fileNamesArray = getLogFilesNameArray();

		Vector<TreeNode> nodes = new Vector<TreeNode>();

		// all filenames passed through an Vector with strings in form of the
		// pattern
		// create strings
		int counter = 1;
		if (fileNamesArray != null && fileNamesArray.length > 0) {
			for (String filename : fileNamesArray) {
				// one element of the resultarray
				TreeNode tmpNode = new TreeNode();

				String year = getYearFromString(filename);
				String month = getMonthFromString(filename);
				String day = getDayFromString(filename);

				if (!year.equals("") && !month.equals("") && !day.equals("")) {
					// insert year start
					tmpNode.setNodeId(counter);
					tmpNode.setParentNodeId(0);
					tmpNode.setNodeName(year);
					tmpNode.setEmptyNodeUrl();
					tmpNode.setYear(year);

					boolean exists = false;
					int yearNodeId = tmpNode.getNodeId();
					for (TreeNode node : nodes) {
						if (tmpNode.getNodeName().equals(node.getNodeName())
								&& tmpNode.getYear().equals(node.getYear())) {
							exists = true;
							yearNodeId = node.getNodeId();
							break;
						}
					}
					if (!exists) {
						nodes.add(tmpNode);
					}
					// insert year end
					counter++;
					tmpNode = new TreeNode();
					// insert month start
					tmpNode.setNodeId(counter);
					tmpNode.setParentNodeId(yearNodeId);
					if (month.equals("01"))
						tmpNode.setNodeName("Januar");
					else if (month.equals("02"))
						tmpNode.setNodeName("Februar");
					else if (month.equals("03"))
						tmpNode.setNodeName("M&auml;rz");
					else if (month.equals("04"))
						tmpNode.setNodeName("April");
					else if (month.equals("05"))
						tmpNode.setNodeName("Mai");
					else if (month.equals("06"))
						tmpNode.setNodeName("Juni");
					else if (month.equals("07"))
						tmpNode.setNodeName("Juli");
					else if (month.equals("08"))
						tmpNode.setNodeName("August");
					else if (month.equals("09"))
						tmpNode.setNodeName("September");
					else if (month.equals("10"))
						tmpNode.setNodeName("Oktober");
					else if (month.equals("11"))
						tmpNode.setNodeName("November");
					else if (month.equals("12"))
						tmpNode.setNodeName("Dezember");
					tmpNode.setEmptyNodeUrl();
					tmpNode.setYear(year);
					tmpNode.setMonth(month);

					exists = false;
					int monthNodeId = tmpNode.getNodeId();
					for (TreeNode node : nodes) {
						if (tmpNode.getNodeName().equals(node.getNodeName())
								&& tmpNode.getYear().equals(node.getYear())
								&& tmpNode.getMonth().equals(node.getMonth())) {
							exists = true;
							monthNodeId = node.getNodeId();
							break;
						}
					}
					if (!exists) {
						nodes.add(tmpNode);
					}
					// insert month end

					counter++;
					tmpNode = new TreeNode();
					// insert day start
					tmpNode.setNodeId(counter);
					tmpNode.setParentNodeId(monthNodeId);
					tmpNode.setNodeName(day);
					tmpNode.setEmptyNodeUrl();
					tmpNode.setYear(year);
					tmpNode.setMonth(month);
					tmpNode.setDay(day);

					exists = false;
					int dayNodeId = tmpNode.getNodeId();
					for (TreeNode node : nodes) {
						if (tmpNode.getNodeName().equals(node.getNodeName())
								&& tmpNode.getYear().equals(node.getYear())
								&& tmpNode.getMonth().equals(node.getMonth())
								&& tmpNode.getDay().equals(node.getDay())) {
							exists = true;
							dayNodeId = node.getNodeId();
							break;
						}
					}
					if (!exists) {
						nodes.add(tmpNode);
					}
					// insert day end
					counter++;
					tmpNode = new TreeNode();
					// insert file start
					tmpNode.setNodeId(counter);
					tmpNode.setParentNodeId(dayNodeId);
					tmpNode.setNodeName(filename); 
					tmpNode.setNodeUrl(filename);

					nodes.add(tmpNode);
					counter++;

					// insert file end
				}

			}
		}
		nodes.trimToSize();

		String[] resultStringArray = null;
		if (nodes.capacity() > 0) {
			// Array with the result strings
			resultStringArray = new String[nodes.capacity()];
			counter = 0;

			for (TreeNode n : nodes) {
				resultStringArray[counter] = n.toString();
				counter++;
			}
		} else {
			resultStringArray = new String[1];
			resultStringArray[0] = "Kein Logfile vorhanden!";
		}
		request.setAttribute("treenodes", resultStringArray);

		rd = request.getRequestDispatcher("showlogfilespage.jsp");
		rd.forward(request, response);

	}

	/**
	 * Reads the log file.
	 * 
	 * @param logfilename
	 *            selected log file
	 * @return entries in the log file
	 */
	private Vector<String> openLogFile(String logfilename) {

		BufferedReader br;
		Vector<String> eintraege = new Vector<String>();
		try {
			br = new BufferedReader(new FileReader(logfilename));

			for (String line; (line = br.readLine()) != null;) {
				// if (line.indexOf("DEBUG") <0){
				eintraege.add(StringEscapeUtils.escapeHtml(line));
				// }
			}
		} catch (FileNotFoundException e) {
			logger.error("ShowLogFilesServlet - Error:" + e.getMessage());
		} catch (IOException e) {
			logger.error("ShowLogFilesServlet - Error:" + e.getMessage());
		}

		return eintraege;
	}

	/**
	 * Gets the year from a complete date as a string.
	 * 
	 * @param filename
	 * 
	 * @return the year as a string
	 */
	private String getYearFromString(String filename) {

		int lastIndex = filename.lastIndexOf(".");
		String dateString = filename.substring(lastIndex + 1);
		// split filename in individual ingredients
		// String[] tmpFileNameElementsArray = filename.split(".");

		/*
		 * search individual ingredients form filename by year
		 */

		// for (String tmpElement : tmpFileNameElementsArray) {
		// date contains alway a - and must have the following format:
		// yyyy-mm-dd
		if (dateString.contains("-")) {
			String[] tmpDateStringArray = dateString.split("-");
			if (tmpDateStringArray.length > 0)
				return tmpDateStringArray[0];
		}
		// }
		return "";
	}

	/**
	 * Gets the month from a complete date as a string.
	 * 
	 * @param filename
	 * 
	 * @return the month as a string
	 */
	private String getMonthFromString(String filename) {

		int lastIndex = filename.lastIndexOf(".");
		String dateString = filename.substring(lastIndex + 1);

		// split filename in individual ingredients
		// String[] tmpFileNameElementsArray = filename.split(".");
		/*
		 * search individual ingredients form filename by month
		 */
		// for (String tmpElement : tmpFileNameElementsArray) {
		// date contains alway a - and must have the following format:
		// yyyy-mm-dd
		if (dateString.contains("-")) {
			String[] tmpDateStringArray = dateString.split("-");
			if (tmpDateStringArray.length > 1)
				return tmpDateStringArray[1];
		}
		// }
		return "";
	}

	/**
	 * Gets the day from a complete date as a string.
	 * 
	 * @param filename
	 * 
	 * @return the day as a string
	 */
	private String getDayFromString(String filename) {
		int lastIndex = filename.lastIndexOf(".");
		String dateString = filename.substring(lastIndex + 1);
		// split filename in individual ingredients
		// String[] tmpFileNameElementsArray = filename.split(".");
		/*
		 * search individual ingredients form filename by day
		 */
		// for (String tmpElement : tmpFileNameElementsArray) {
		// date contains alway a - and must have the following format:
		// yyyy-mm-dd
		if (dateString.contains("-")) {
			String[] tmpDateStringArray = dateString.split("-");
			if (tmpDateStringArray.length > 2)
				return tmpDateStringArray[2];

		}
		// }
		return "";
	}

	/**
	 * Gets the names of all log files.
	 * 
	 * @return a list with the names of found log files in a path.
	 */
	private String[] getLogFilesNameArray() {
		String logFilesFolderPath = getServletContext().getRealPath("/")
				+ conf.getProperty("pathLogFiles");

		File logFileRoot = new File(logFilesFolderPath);
		return logFileRoot.list();
	}
}
