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

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.openehealth.twp.tewepo.businesslogic.UnlockTask;


/**
 * This Class helps to create Deamon Threads on Deployment and could be used in
 * different ways for one time initializations. In this case, it is used to
 * monitor the time passed after an IP is blocked. After a defined time the
 * locked IP will be deleted from the database and the user is allowed to log in
 * again.
 * 
 * @author Benjamin Schneider
 * 
 */
public class DeployListener implements ServletContextListener {

	// /** Logging Object */
	// private final static Logger logger =
	// Logger.getLogger(DeployListener.class);

	/** Timer Object to be used to create a TimerTask */
	Timer timer = null;

	/** The Task that deletes Banned IPs after an amount of time */
	UnlockTask unlockTask = null;

	/**
	 * Called once, on Deploy.
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {

		if (timer == null) {
			timer = new Timer(true);
			unlockTask = new UnlockTask();
			timer.scheduleAtFixedRate(unlockTask, 60000, 60000);
		}
	}

	/**
	 * Called once, on Destroy, a.k.a Undeploy.
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {

		if (timer != null) {
			timer.cancel();
			timer = null;
			
		}
	}

}
