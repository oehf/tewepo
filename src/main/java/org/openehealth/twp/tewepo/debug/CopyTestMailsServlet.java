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
package org.openehealth.twp.tewepo.debug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openehealth.tewepo.twp.dmp.dmc.server.config.Configuration;
import org.openehealth.tewepo.twp.dmp.dmc.server.email.SendDicomMailHelper;
import org.openehealth.twp.tewepo.controller.Dispatcher;
import org.openehealth.twp.tewepo.helper.SessionGuard;


/**
 * Servlet implementation to copy test mails in debug folder.
 * 
 * @author Nilay Yueksekogul
 * 
 */
@SuppressWarnings("serial")
public class CopyTestMailsServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(Dispatcher.class);

	/**
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CopyTestMailsServlet() {
		super();
	}

	/*
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * Copy content of debug directory into cache/debug directory.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws ServletException
	 * @throws {@link IOException}
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			// validate request
			SessionGuard.INSTANCE.validate(request);

			RequestDispatcher rd;

			String pathIn = getServletContext().getRealPath("/") + "debug/";
			String pathOut = getServletContext().getRealPath("/")
					+ Configuration.getMainConfig()
							.getProperty("tmpFolderPath") + "/debug";
			File inF = new File(pathIn);

			SendDicomMailHelper helper = new SendDicomMailHelper(
					getServletContext().getRealPath("/"), "debug");
			File outF = helper.getTmpFolder();

			copyDirectory(inF, outF);

			request.setAttribute("message",
					"Testdateien wurden im Cache angelegt");
			request.setAttribute("debugMode", Configuration.getMainConfig()
					.getProperty("debugMode"));

			rd = request.getRequestDispatcher("dicomemailpage.jsp");
			rd.forward(request, response);
		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * Copy directory into another directory.
	 * 
	 * @param inF
	 * @param outF
	 * 
	 * @throws IOException
	 */
	public static void copyDirectory(File inF, File outF) throws IOException {
		if (inF.isDirectory()) {
			if (!outF.exists()) {
				outF.mkdir();
			}

			String[] children = inF.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(inF, children[i]), new File(outF,
						children[i]));
			}
		} else {
			InputStream in = new FileInputStream(inF);
			FileOutputStream out = new FileOutputStream(outF);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}

	/**
	 * Copy file into another file.
	 * 
	 * @param in
	 * @param out
	 * 
	 * @throws IOException
	 */
	public static void copyFile(File in, File out) throws IOException {
		FileChannel inChannel = new FileInputStream(in).getChannel();
		FileChannel outChannel = new FileOutputStream(out).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} catch (IOException e) {
			throw e;
		} finally {
			if (inChannel != null) {
				inChannel.close();
			}
			if (outChannel != null) {
				outChannel.close();
			}
		}
	}

}
