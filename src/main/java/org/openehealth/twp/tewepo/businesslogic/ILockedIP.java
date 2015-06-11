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

/**
 * Describes a locked IP. This interface is used to block IPs that try to login
 * for more than X times on a specific period of time and is the first line of
 * defense against system brute-force hacking.
 * 
 * @author Benjamin Schneider
 * 
 */
public interface ILockedIP extends PersistentObject {

	/** If ID == -1, it is not saved into the database */
	public final static int NEW_CREATED = -1;

	/**
	 * Returns the IP.
	 * 
	 * @return ip
	 */
	public abstract String getIp();

	/**
	 * Returns the wrong attempt number.
	 * 
	 * @return attempt number
	 */
	public abstract int getAttemptNo();

	/**
	 * Checks if the IP is active.
	 * 
	 * @return true, if active false, else
	 */
	public abstract boolean isActive();

	/**
	 * Sets the given IP.
	 * 
	 * @param ip
	 */
	public abstract void setIp(String ip);

	/**
	 * Sets the given attempt number.
	 * 
	 * @param versuchNr
	 */
	public abstract void setAttemptNo(int versuchNr);

	/**
	 * Set active or inactive.
	 * 
	 * @param aktiv
	 */
	public abstract void setActive(boolean aktiv);

	/**
	 * If the IP is already in the database this method increments the number of
	 * times this IP has failed to login and saves the modifications into the
	 * database. If the number of times resulting is bigger than the number
	 * allowed this method blocks that IP.
	 * 
	 */
	public abstract void increment();

	/**
	 * Saves the locked IP into the database.
	 * 
	 */
	public abstract void save();

	/**
	 * Deletes the locked IP from the database.
	 * 
	 */
	public abstract void delete();

	/**
	 * Returns the number of millis that have passed since the IP was blocked.
	 * 
	 * @return millis value in long type.
	 */
	public abstract long getBygoneTimes();

	/**
	 * Overrides the equals() method from the Object Class.
	 * 
	 * @param o
	 */
	public abstract boolean equals(Object o);

	/**
	 * Overrides the toString() method from the Object-Class.
	 */
	public abstract String toString();

}