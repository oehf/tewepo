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

import java.util.GregorianCalendar;

import javax.persistence.Entity;

import org.apache.log4j.Logger;
import org.openehealth.tewepo.twp.dmp.dmc.server.config.Configuration;
import org.openehealth.twp.tewepo.database.DatabaseException;
import org.openehealth.twp.tewepo.database.PersistenceService;


/**
 * {@link ILockedIP}
 * 
 * @author Benjamin Schneider
 * 
 */
@Entity
@javax.persistence.Table(name = "lockedip")
public class LockedIP extends AbstractPersistentObject implements ILockedIP {
	/** The ID of this object */
	// private int id;

	/** The IP that is treated */
	private String ip;

	/** The number of times this IP failed to login */
	private int attemptNo;

	/** Weather this Object is active or not */
	private boolean active;

	/** The number of times the IP is allowed to fail the login */
	private final static String numberOfAllowedAffordsStr = Configuration.getMainConfig().getProperty("numberOfAllowedAffords");
	/**parse in int*/
	private final static int numberOfAllowedAffords =Integer.parseInt(numberOfAllowedAffordsStr);

	/** The time after a blocked IP is unblocked. (1000ms * 60sec * 10min) */
	private final static long waitingTime = Long.parseLong(Configuration.getMainConfig().getProperty("lockedIPwaitingTime"));
	
	/** Logging object */
	private final static Logger logger = Logger.getLogger("webportal");

	private GregorianCalendar lastUpdate = null;

	/**
	 * 
	 */
	public LockedIP() {
	}

	/**
	 * Creates an object from the IP's that failed the login.
	 * 
	 * @param ip
	 *            The IP of the person who failed the login
	 */
	public LockedIP(String ip) {
		this(ip, 1, true);
	}

	/**
	 * Object Constructor. Creates Object from the IP's that failed the login.
	 * 
	 * @param id
	 *            Object ID
	 * @param ip
	 *            The IP of the person who failed the login
	 * @param attemptNo
	 *            The number of times this IP failed to login
	 * @param active
	 *            If this IP is allowed to view the system or not
	 * 
	 */
	public LockedIP(String ip, int attemptNo, boolean active) {
		this.setIp(ip);
		this.setAttemptNo(attemptNo);
		this.setActive(active);
		this.lastUpdate = new GregorianCalendar();
	}

	/**
	 * Is used to manipulate the IPs that fail the login.
	 * 
	 * If the IP is not in the Database the method creates it and adds it into
	 * the Database. If the IP is already in the Database the method increments
	 * the number of time this IP has failed to login and saves the
	 * modifications into the Database.
	 * 
	 * @param ip
	 * 
	 */
	public static void lockIP(String ip) {
		ILockedIP lockedIP = LockedIP.getLockedIP(ip);
	

		if (lockedIP != null) {
			lockedIP.increment();
		} else {
			ILockedIP newLockedIP = new LockedIP(ip);
			newLockedIP.save();
		}
	}

	/**
	 * if the IP is blocked or not; e.g: if active is set to true or false. If
	 * the IP is not in the Database the result will be 'true'.
	 * 
	 * Designed to be used from outside the class; in the JSP for example. It
	 * has basically the same effect as the isAktiv() method without the
	 * complication of the Object.
	 * 
	 * @param ip
	 * 
	 * @return 'true' if the IP is not blocked and 'false' otherwise.
	 */
	public static boolean isActive(String ip) {
		ILockedIP lockedIP = LockedIP.getLockedIP(ip);

		if (lockedIP != null) {
			return lockedIP.isActive();
		} else { 
			return true;
			
		}
		
	}

	/**
	 * Is used to get the LockedIP object from the database that corresponds to
	 * this IP.
	 * 
	 * @param ip
	 * 
	 * @return The object if the object is in the database and null otherwise.
	 */
	public static ILockedIP getLockedIP(String ip) {
		ILockedIP lockIP = null;
		try {
			lockIP = PersistenceService.getService().getLockedIP(ip);
		} catch (DatabaseException dbe) {
			logger.error("Fehler beim DB Zugriff fuer die IP: " + ip);
		}
		return lockIP;
	}

	/**
	 * Is used to delete the oldest LockedIP that is blocked and has timely
	 * earned the right to view the system again.
	 * 
	 * @return The oldest blocked LockedIP in the System if it exists or null
	 *         otherwise.
	 */
	public static ILockedIP getOldLockedIP() {
		ILockedIP lockedIP = null;
		try {
			lockedIP = PersistenceService.getService().getOldestLockedIP();
			if (lockedIP != null) {
				 long bygoneTime = lockedIP.getBygoneTimes();
				 if (bygoneTime > waitingTime) {
					 lockedIP.setActive(false);
					 lockedIP.setAttemptNo(0);
					return lockedIP;
				} else {
					return null;
				}
			}else{
				logger.info("LockedIP - getOldLockedIP - Keine LockedIP hierzu vorhanden.");
				return null;			
			}
		} catch (DatabaseException dbe) {
			//logger.error("Fehler beim DB Zugriff auf die alte LockedIP.");
			//logger.error("DB-Error Message: "+dbe.getMessage());
		}
		return lockedIP;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getAttemptNo() {
		return attemptNo;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAttemptNo(int versuchNr) {
		this.attemptNo = versuchNr;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setActive(boolean aktiv) {
		this.active = aktiv;
	}

	/**
	 * {@inheritDoc}
	 */
	public void increment() {
		this.setAttemptNo(this.getAttemptNo() + 1);
		this.lastUpdate = new GregorianCalendar();
		if (this.getAttemptNo() >= numberOfAllowedAffords) {
			this.setActive(false);
			logger.info("LockedIP - increment - Die IP Adresse [ " + this.getIp()
							+ " ] ist gesperrt.");
		}
		this.save();
	}

	/**
	 * {@inheritDoc}
	 */
	public void save() {
		try {
			PersistenceService.getService().store(this);
		} catch (DatabaseException dbe) {
			logger.error("LockedIP - save - Fehler beim speichern der LockedIP: "
					+ this.getObjectID());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete() {
		try {
			PersistenceService.getService().remove(this);
		} catch (DatabaseException dbe) {
			logger.error("LockedIP - delete - Fehler beim loeschen der LockedIP.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public long getBygoneTimes() {
		long bygoneTime = System.currentTimeMillis()
				- this.lastUpdate.getTimeInMillis();
		logger.debug("LockedIP - getBygoneTimes - System Time: " + System.currentTimeMillis());
		logger.debug("LockedIP - getBygoneTimes - Passed Time: " + bygoneTime);
		return bygoneTime;
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object o) {
		try {
			ILockedIP comparativeIP = (ILockedIP) o;
			if (this.getObjectID() == comparativeIP.getObjectID()
					&& getObjectID() != NEW_CREATED) {
				return true;
			} else {
				return this.getIp().equals(comparativeIP.getIp())
						&& this.getAttemptNo() == comparativeIP.getAttemptNo();
			}
		} catch (ClassCastException e) {
			
			return false;
		}
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "ID: " + getObjectID() + " IP: " + getIp() + " Nr: "
				+ getAttemptNo() + " ms: " + getBygoneTimes();
	}
}
