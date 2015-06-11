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

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.openehealth.twp.tewepo.configuration.Configuration;


/**
 * Servlet implementation for uploading files.
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class UploadFileServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(UploadFileServlet.class);//"webportal"

	/** Configuration */
	private final static Configuration conf = Configuration.getMainConfig("");

	/**
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		
		doPost(request, response);

	}

	/**
	 * Uploads selected file(s) which are saved in a temporary folder after
	 * choosing.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws ServletException
	 * @throws {@link IOException}
	 * 
	 *         Help:
	 *         http://www.developershome.com/wap/wapUpload/wap_upload.asp?page
	 *         =jsp
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd;
		if (ServletFileUpload.isMultipartContent(request)) {
			// Parse the HTTP request...
			ServletFileUpload servletFileUpload = new ServletFileUpload(
					new DiskFileItemFactory());
			try {
				// String
				// folderPath=getServletContext().getRealPath("/")+conf.getProperty("tmpFolderPath")+"//"+request.getSession().getId();
				// File folder= new File(folderPath);
				// if(!folder.exists())
				// folder.mkdir();

				File folder = getTmpFolder(request.getSession().getId());

				List fileItemsList = servletFileUpload.parseRequest(request);
				logger
						.info("UploadFileServlet - Anzahl der hochzuladenden Dateien: "
								+ fileItemsList.size());

				Iterator<FileItem> it = fileItemsList.iterator();
				while (it.hasNext()) {
					FileItem fileItem = (FileItem) it.next();

					if (fileItem.isFormField()) {
						/*
						 * The file item contains a simple name-value pair of a
						 * form field
						 */
					} else {
						/* The file item contains an uploaded file */
						logger.info("UploadFileServlet - fileItem.getName(): "
								+ fileItem.getName());
						File file = new File(folder, fileItem.getName());
						// + conf.getProperty("tmpFileEnding"));
						logger
								.info("UploadFileServlet - Filename: "
										+ file.getAbsolutePath());
						fileItem.write(file);
					}

				}
			} catch (FileUploadException e) {
				logger.error("UploadFileServlet - Fehler aufgetreten "
						+ e.getMessage());
				response
						.getOutputStream()
						.write(
								"ERROR: Es ist ein Fehler aufgetreten, bitte versuchen Sie es erneut! Falls der Fehler erneut auftritt wenden Sie sich bitte an den Administrator!"
										.getBytes());
			} catch (Exception e) {
				logger.error("UploadFileServlet - Fehler aufgetreten "
						+ e.getMessage());
				response
						.getOutputStream()
						.write(
								"ERROR: Es ist ein Fehler aufgetreten, bitte versuchen Sie es erneut! Falls der Fehler erneut auftritt wenden Sie sich bitte an den Administrator!"
										.getBytes());
			}
		}

		response.getOutputStream().write("SUCCESS".getBytes());

	}

	/**
	 * Gets the temporary created folder to keep selected files which are to
	 * upload.
	 * 
	 * @param sessionId
	 *            current active session
	 * 
	 * @return the created folder
	 */
	private File getTmpFolder(String sessionId) {

		String folderPath = getServletContext().getRealPath("/")
				+ Configuration.getMainConfig("").getProperty("tmpFolderPath")
				+ "/" + sessionId;
		File tmpFolder = new File(folderPath);
		if (!tmpFolder.isDirectory()) {
			tmpFolder.mkdirs();
			logger.info("UploadFileServlet - Der Ordner: " + folderPath
					+ " - wurde erstellt!");
		}
		logger.info("UploadFileServlet - TMP-Folder: "
				+ tmpFolder.getAbsolutePath());
		return tmpFolder;

	}
}
