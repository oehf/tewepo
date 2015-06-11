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
package org.openehealth.twp.tewepo.helper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This class guards the current session.
 * 
 * @author Benjamin Schneider
 * 
 */
public final class SessionGuard {

	public static final SessionGuard INSTANCE = new SessionGuard();

	public static final String MARKER = "marker";

	/**
	 * Marks and validates the current session.
	 * 
	 * @param request
	 * 
	 * @throws ServletException
	 *             , if session invalidated
	 */
	public void validate(HttpServletRequest request) throws ServletException {
		String currentMarker = getMarker(request);
		HttpSession session = request.getSession();

		String sessionMarker = (String) session.getAttribute(MARKER);

		if (currentMarker == null || !currentMarker.equals(sessionMarker)) {
			request.getSession().invalidate();
			throw new ServletException("invalidated Session!");
		}

	}

	/**
	 * This method supplies the IP address of the client.
	 * 
	 * @param request
	 * 
	 * @return remote address
	 */
	public String getMarker(HttpServletRequest request) {
		return request.getRemoteAddr();
		//	return request.getHeader("x-forwarded-for");
	}

}
