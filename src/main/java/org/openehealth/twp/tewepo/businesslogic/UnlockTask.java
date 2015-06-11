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
package org.openehealth.twp.tewepo.businesslogic;

import java.util.TimerTask;

import org.apache.log4j.Logger;

/**
 * This Class runs as a Daemon Thread and tests if there are some blocked IPs
 * that need to be unblocked.
 * 
 * @author Benjamin Schneider
 * 
 */
public class UnlockTask extends TimerTask {

	/** Logging object */
	private final static Logger logger = Logger.getLogger("webportal");

	/**
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {

		ILockedIP oldLockedIP = LockedIP.getOldLockedIP();

		if (oldLockedIP != null) {
			logger.warn("Loeschung von LockedIP: " + oldLockedIP.getIp());
			oldLockedIP.delete();
		} else {
			logger.debug("Nichts zum Loeschen.");
		}
	}

}
