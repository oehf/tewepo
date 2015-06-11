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
package org.openehealth.twp.tewepo.database;

import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.openehealth.twp.tewepo.businesslogic.AbstractPersistentObject;
import org.openehealth.twp.tewepo.businesslogic.ActivationDatabaseEntry;
import org.openehealth.twp.tewepo.businesslogic.Address;
import org.openehealth.twp.tewepo.businesslogic.IActivationDatabaseEntry;
import org.openehealth.twp.tewepo.businesslogic.IAddress;
import org.openehealth.twp.tewepo.businesslogic.ILockedIP;
import org.openehealth.twp.tewepo.businesslogic.ILogLastFiles;
import org.openehealth.twp.tewepo.businesslogic.ILogLastRecipients;
import org.openehealth.twp.tewepo.businesslogic.IPerson;
import org.openehealth.twp.tewepo.businesslogic.IRole;
import org.openehealth.twp.tewepo.businesslogic.LockedIP;
import org.openehealth.twp.tewepo.businesslogic.LogLastFiles;
import org.openehealth.twp.tewepo.businesslogic.LogLastRecipients;
import org.openehealth.twp.tewepo.businesslogic.PersistentObject;
import org.openehealth.twp.tewepo.businesslogic.Person;
import org.openehealth.twp.tewepo.businesslogic.Role;
import org.openehealth.twp.tewepo.businesslogic.Role.Roles;


/**
 * Database persistence class.
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("unchecked")
public class PersistenceService {

	private static PersistenceService instance = null; // singelton instance

	private EntityManagerFactory emf = null;
	private EntityManager em = null;

	private static Logger log = Logger.getLogger(PersistenceService.class);

	private PersistenceService() {
		this.emf = Persistence.createEntityManagerFactory("db_tewepo");
		this.em = emf.createEntityManager();
	}

	/**
	 * Return the persistance serve. If the instance is null an new persistance
	 * service will be created.
	 * 
	 * @return An instance of the persistence service.
	 */
	public static PersistenceService getService() {
		if (instance == null) {
			instance = new PersistenceService();
		}

		return instance;
	}

	/**
	 * Stores an object in the database.
	 * 
	 * @param obj
	 * 
	 * @return object ID
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public long store(PersistentObject obj) throws DatabaseException {
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			AbstractPersistentObject merged = (AbstractPersistentObject) em
					.merge(obj);
			em.persist(merged);
			tx.commit();

			return merged.getObjectID();
		} catch (PersistenceException e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			log.error("Fehler beim Speichern: " + e);
			throw new DatabaseException(e.getMessage());
		}
	}

	/**
	 * Gets the person with the given ID.
	 * 
	 * @param id
	 *            ID of the person
	 * 
	 * @return person
	 * 
	 * @throws DatabaseException
	 *             , if there's no person with the given ID
	 *             {@link DatabaseException}
	 */
	public IPerson getPerson(long id) throws DatabaseException {
		assert id >= 0;

		try {
			IPerson pers = em.find(Person.class, id);
			if (pers == null) {
				throw new DatabaseException(
						"Kein Eintrag zur Uebergebenen ID gefunden!");
			}
			return pers;
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/**
	 * Gets all persons stored in the database.
	 * 
	 * @return a vector with persons
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public Vector<IPerson> getAllPersons() throws DatabaseException {
		try {
			List<IPerson> persons = em.createNativeQuery(
					"SELECT * FROM person p", Person.class).getResultList();
			Vector<IPerson> vectorPersons = new Vector<IPerson>();
			for (IPerson p : persons) {
				vectorPersons.add(p);
			}

			return vectorPersons;
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/**
	 * Gets a vector with all persons in the system name by given loginname from
	 * the database.
	 * 
	 * @return a vector with all found persons
	 * 
	 * @throws DatabaseException
	 *             Wird geworfen, wenn ein schwerer Datenbankfehler auftritt.
	 *             {@link DatabaseException}
	 */
	public List<IPerson> getPersonsFromDB(String loginname)
			throws DatabaseException {

		try {
			List<IPerson> persons = em.createNativeQuery(
					"SELECT * FROM person p  WHERE p.loginname= :param",
					Person.class).setParameter("param", loginname)
					.getResultList();

			return persons;
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	public IPerson getPerson(String email) throws DatabaseException {

		try {

			Query query = em.createNativeQuery(
					"SELECT * FROM person p WHERE p.emailaddress= :param3",
					Person.class);
			query.setParameter("param3", email);

			List<IPerson> persons = query.getResultList();

			return persons.get(0);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	/**
	 * Gets a person from the database by given surname, forename and e-mail
	 * address
	 * 
	 * @param surname
	 *            of the required person
	 * @param forename
	 *            of the required person
	 * @param email
	 *            e-mail address of the required person
	 * 
	 * @return the found person
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public IPerson getPerson(String surname, String forename, String email)
			throws DatabaseException {

		try {

			Query query = em
					.createNativeQuery(
							"SELECT * FROM person p WHERE p.surname=:param1 AND p.forename=:param2 AND p.emailaddress= :param3",
							Person.class);
			query.setParameter("param1", surname);
			query.setParameter("param2", forename);
			query.setParameter("param3", email);

			List<IPerson> persons = query.getResultList();

			return persons.get(0);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	public IPerson getPerson(String username, String surname, String forename,
			String email) throws DatabaseException {

		try {

			Query query = em
					.createNativeQuery(
							"SELECT * FROM person p WHERE p.loginname=:param AND p.surname=:param1 AND p.forename=:param2 AND p.emailaddress= :param3",
							Person.class);
			query.setParameter("param", username);
			query.setParameter("param1", surname);
			query.setParameter("param2", forename);
			query.setParameter("param3", email);

			List<IPerson> persons = query.getResultList();

			return persons.get(0);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}

	}
	public IPerson getPerson(String username, String title,  String surname, String forename,
			String email) throws DatabaseException {

		try {

			Query query = em
					.createNativeQuery(
							"SELECT * FROM person p WHERE p.loginname=:param AND p.surname=:param1 AND p.forename=:param2 AND p.emailaddress= :param3",
							Person.class);
			query.setParameter("param", username);
	
			query.setParameter("param1", surname);
			query.setParameter("param2", forename);
			query.setParameter("param3", email);

			List<IPerson> persons = query.getResultList();

			return persons.get(0);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	/**
	 * Gets a list with last used recipients by the given specific user ID.
	 * 
	 * @param userId
	 * 
	 * @return list of last logged recipients
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public List<ILogLastRecipients> getLogLastRecipients(String userId)
			throws DatabaseException {

		try {

			Query query = em
					.createNativeQuery(
							"SELECT * FROM log_lastrecipients l WHERE l.person_id=:param1 ORDER BY date DESC",
							LogLastRecipients.class);
			query.setParameter("param1", userId);

			//log.info("LOGGING: DB Result List: " + query.getResultList());

			return query.getResultList();

		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	/**
	 * Gets a list with last used recipients by the given specific user ID.
	 * 
	 * @param userId
	 * 
	 * @return list of last logged recipients
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public List<ILogLastFiles> getLogLastFiles(long userId)
			throws DatabaseException {

		try {

			Query query = em
					.createNativeQuery(
							"SELECT * FROM log_lastfiles l WHERE l.person_id=:param1 ORDER BY date DESC",
							LogLastFiles.class);
			query.setParameter("param1", userId);

			//log.debug("LOGGING: DB Result List: " + query.getResultList());

			return query.getResultList();

		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	/**
	 * Gets address of the user from the database by the given ID.
	 * 
	 * @param id
	 * 
	 * @return the found address
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public IAddress getAddress(long id) throws DatabaseException {
		assert id >= 0;

		try {
			IAddress address = em.find(Address.class, id);
			if (address == null) {
				throw new DatabaseException(
						"Kein Eintrag zur Uebergebenen ID gefunden!");
			}
			return address;
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/**
	 * Gets the role from the database by the given ID.
	 * 
	 * @param id
	 * 
	 * @return the found role
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public IRole getRole(long id) throws DatabaseException {
		assert id >= 0;

		try {
			IRole role = em.find(Role.class, id);
			if (role == null) {
				throw new DatabaseException(
						"Kein Eintrag zur Uebergebenen ID gefunden!");
			}
			return role;
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/**
	 * Gets the role of a person by the given roles.
	 * 
	 * @param role
	 * 
	 * @return the found role
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public Role getRole(Roles role) throws DatabaseException {
		try {
			int roleValue = role.ordinal();

			List<Role> roles = em.createNativeQuery(
					"SELECT * FROM role r WHERE r.role = :param", Role.class)
					.setParameter("param", roleValue).getResultList();

			return roles.get(0);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/**
	 * Gets the activation entry from the database by a given code.
	 * 
	 * @param code
	 * 
	 * @return activation entry from the database
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public IActivationDatabaseEntry getActivationEntry(String code)
			throws DatabaseException {
		try {
			List<IActivationDatabaseEntry> activationEntries = em
					.createNativeQuery(
							"SELECT * FROM activationdatabaseentry a WHERE a.activationCode = :param",
							ActivationDatabaseEntry.class).setParameter(
							"param", code).getResultList();

			return activationEntries.get(0);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/**
	 * Gets the locked IP from the database by a given string IP.
	 * 
	 * @param ip
	 * 
	 * @return the locked IP
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public ILockedIP getLockedIP(String ip) throws DatabaseException {
		try {
			List<ILockedIP> ips = em.createNativeQuery(
					"SELECT * FROM lockedip l WHERE l.ip = :param",
					LockedIP.class).setParameter("param", ip).getResultList();

			if (ips.size() > 0)
				return ips.get(0);
			else
				return null;
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/**
	 * Is used to get the oldest blocked GesperrtIP from the Database.
	 * 
	 * @return The oldest blocked GesperrtIP if it exists and null otherwise
	 * 
	 * @throws DatenbankException
	 *             if a SQL exception occures
	 */
	public ILockedIP getOldestLockedIP() throws DatabaseException {

		try {

			ILockedIP ip = (LockedIP) em
					.createNativeQuery(
							"SELECT * FROM lockedip l where l.lastUpdate=(SELECT MIN(lastUpdate) FROM lockedip l)",
							LockedIP.class).getSingleResult();

			return ip;

		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/**
	 * Is used to get the last time the Object with the ID='id' was modified.
	 * 
	 * @param id
	 *            object ID
	 * 
	 * @return The last time this object was modified.
	 * 
	 * @throws DatenbankException
	 *             if a SQL exception occures {@link DatabaseException}
	 */
	public ILockedIP getLockedIPLastUpdate(long id) throws DatabaseException {

		try {
			List<ILockedIP> ips = em.createNativeQuery(
					"SELECT * FROM lockedip l WHERE l.id = :param",
					LockedIP.class).setParameter("param", id).getResultList();

			return ips.get(0);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	/**
	 * Removes an object from the database.
	 * 
	 * @param obj
	 *            to delete
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public void remove(PersistentObject obj) throws DatabaseException {
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			em.remove(obj);
			tx.commit();

		} catch (PersistenceException e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			log.error("Fehler beim löschen: " + e);
			throw new DatabaseException(e.getMessage());
		}
	}
}
