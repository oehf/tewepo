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

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.apache.log4j.Logger;
import org.openehealth.twp.tewepo.businesslogic.Role.Roles;
import org.openehealth.twp.tewepo.configuration.Configuration;
import org.openehealth.twp.tewepo.database.DatabaseException;
import org.openehealth.twp.tewepo.database.PersistenceService;
import org.openehealth.twp.tewepo.helper.BCrypt;
import org.openehealth.twp.tewepo.helper.Utility;


/**
 * Each object of this class represents one person in the system. Each person
 * owns attributes and a number of certain roles.
 * 
 * @author Benjamin Schneider
 * 
 */
@Entity
@javax.persistence.Table(name = "person")
public class Person extends AbstractPersistentObject implements IPerson {

	/** the configuration */
	private final static Configuration conf = Configuration.getMainConfig("");

	private static Logger logger = Logger.getLogger(Person.class);

	/** the person logs in the system with this name */
	private String loginname;

	/**
	 * Contains all roles of the person.
	 */
	@ManyToMany(targetEntity = Role.class)
	private List<IRole> roles = new Vector<IRole>();

	/** encrypted password */
	private String password;

	// /** unique Identifikation */
	// private int id = -1;

	/** e-mail address of the person */
	private String emailaddress;
	
	/** title of the person */
	private String title;

	/** forename of the person */
	private String forename;

	/** surname of the person */
	private String surname;

	/** organisation which the person belongs to */
	private String organisation;

	/** department where the person works */
	private String department;

	/** occupationgroup which the person belongs to */
	private String occupationgroup;

	/** address of the person */
	@OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
	private IAddress address;

	private boolean accountActive = false;

	/**
	 * {@inheritDoc}
	 */
	public boolean isAccountActive() {
		return this.accountActive;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAccountActive(boolean accountActive) {
		this.accountActive = accountActive;
	}

	/**
	 * Default constructor
	 */
	public Person() {
		super();
	}

	/**
	 * Constructor for a person which doesn't exist in the database yet. No ID
	 * will be passed; yet it will be set on -1! The save()-method must be
	 * called to get an ID.
	 * 
	 * @param loginname
	 *            required name for the login
	 * @param rolle
	 *            role of the person
	 * @param password
	 *            required password for the login
	 * @param emailadress
	 *            e-mail address of the person. Messages will be sent to this
	 *            address.
	 * @param forename
	 *            forename of the person
	 * @param surname
	 *            surname of the person
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException} will be thrown, if one of the
	 *             required data contains an invalid character
	 */
	public Person(String loginname, IRole role, String password,
			String emailaddress, String forename, String surname,
			String organisation, String department, String occupationgroup,
			String street, String number, String zipcode, String location)
			throws BusinesslogicException {

		this.setLoginname(loginname);
		if (role != null)
			this.addRole(role.getRole());
		this.setPassword(password);
		this.setEmailaddress(emailaddress);
		this.setForename(forename);
		this.setSurname(surname);
		this.setOrganisation(organisation);
		this.setDepartment(department);
		this.setOccupationgroup(occupationgroup);
		this.setAddress(new Address(street, number, zipcode, location));
		// this.id = -1;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isProfessional() {
		List<IRole> allRoles = this.getRoles();

		if (allRoles != null) {
			for (IRole r : allRoles) {
				if (r.getRole().equals(Roles.ADMINISTRATOR)
						|| r.getRole().equals(Roles.PHYSICIAN))
					return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void clearProfessionalFields() {
		this.department = "";
		this.occupationgroup = "";
		this.organisation = "";
	}

	/**
	 * {@inheritDoc}
	 */
	public void sendPasswordRequestMail() {

		String tmpPassword = Utility.generateRandomString();
		if (Person.existsLogin(this.getLoginname())) {
			try {
				this.setPassword(BCrypt.hashpw(tmpPassword, BCrypt.gensalt()));
				this.save();
				// send mail
				String defaultSubject = "Anfrage für das Zurücksetzen des Passworts für den Benutzeraccount am Teleradiologiewebportal";
				String text = "\nSehr geehrte(r) "+this.getTitle()+"" + this.getForename() + " "
						+ this.getSurname() + ",\n";

				text = text
						+ "\n"
						+ "in dieser E-Mail erhalten Sie ein Übergangspasswort, mit der Sie sich wieder am Webportal anmelden können."
						+ " \n" + "\n";
				text = text + "Das Passwort lautet: " + tmpPassword + " \n"
						+ " \n";
				text = text
						+ "Bitte ändern Sie bei der Anmeldung am Portal das Passwort!";

				text = text
						+ "\n"
						+ "\n"
						+ "Diese Nachricht wurde automatisch vom Teleradiologie-Webportal Rhein-Neckar Dreieck generiert."
						+ "\n"
						+ "Wenn Sie sich mit uns in Verbindung setzen möchten, bitten wir Sie  eine E-Mail an die Administration zu senden."
						+ "\n" + "\n" + "Max Mustermann" + "\n"
						+ "Administration" + "\n"
						+ "E-Mail: max.mustermann@test.de"
						+ "\n" + "Impressum:" + "\n"
						+ "Testunternehmen" + "\n"
						+ "Teststraße 4" + "\n" + "12345 Teststadt";

				new Mail().sendMail(this.getEmailaddress(), defaultSubject,
						text);
				logger
						.info("Die Passwortanfrage-Mail wurde erfolgreich an den Benutzer: "
								+ this.getForename()
								+ " "
								+ this.getSurname()
								+ " gesendet.");

			} catch (Exception e) {
				logger
						.error("Person.java - Fehler beim Bearbeiten der Password-Reset-Anfrage f&uuml;r den Benutzer: "
								+ this.getForename()
								+ " "
								+ this.getSurname()
								+ ".");
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void sendUsernameRequestMail() {

		if (Person.existsLogin(this.getLoginname())) {
			try {

				// send mail
				String defaultSubject = "Benutzername-Anfrage für den Benutzeraccount am Teleradiologiewebportal";
				String text = "\nSehr geehrte(r) "+this.getTitle()+"" + this.getForename() + " "
						+ this.getSurname() + ",\n";
				text = text
						+ "\n\n"
						+ "in dieser E-Mail erhalten Sie Ihren Benutzernamen, mit der Sie sich wieder am Webportal anmelden können."
						+ "\n" + "\n";
				text = text + "Ihr Benutzername:   " + this.getLoginname();

				text = text
						+ "\n"
						+ "\n"
						+ "Diese Nachricht wurde automatisch vom Teleradiologie-Webportal Rhein-Neckar Dreieck generiert."
						+ "\n"
						+ "Wenn Sie sich mit uns in Verbindung setzen möchten, bitten wir Sie  eine E-Mail an die Administration zu senden."
						+ "\n" + "\n" + "Max Mustermann" + "\n"
						+ "Administration" + "\n"
						+ "E-Mail: max.mustermann@test.de"
						+ "\n" + "Impressum:" + "\n"
						+ "Testunternehmen" + "\n"
						+ "Teststraße 4" + "\n" + "12345 Teststadt";
				new Mail().sendMail(this.getEmailaddress(), defaultSubject,
						text);
				logger
						.info("Die Benutzernameanfragemail wurde erfolgreich an den Benutzer: "
								+ this.getForename()
								+ " "
								+ this.getSurname()
								+ " gesendet.");

			} catch (Exception e) {
				logger
						.error("Person.java - Fehler beim Bearbeiten der Benutzername-Anfrage f&uuml;r den Benutzer: "
								+ this.getForename()
								+ " "
								+ this.getSurname()
								+ ".");
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void sendAcivationEMail() {
		/*
		 * only if no role is available, otherwise the account must not be
		 * activated
		 */
		if (!this.isAccountActive() && getObjectID() != -1) {
			try {
				String code = BCrypt.hashpw(((new Date()).toString() + this
						.getObjectID()), BCrypt.gensalt());
				// entry into the database
				IActivationDatabaseEntry activationEntry = new ActivationDatabaseEntry();
				activationEntry.setActivationCode(code);
				activationEntry.setPerson(this);
				PersistenceService.getService().store(activationEntry);

				// send mail
				String defaultSubject = "Aktivierung des Accounts für das Teleradiologiewebportal";

				String text = "\n" + "Sehr geehrte(r) "+this.getTitle()+"" + this.getForename()
						+ " " + this.getSurname() + ",";
				text = text
						+ "\n\n"
						+ "in dieser E-Mail erhalten Sie Ihren Aktivierungslink, mit dem Sie sich am Portal aktivieren können."
						+ " \n" + "Bitte klicken Sie auf den folgenden Link:"
						+ " \n" + " \n";

				text = text + "<" + conf.getProperty("portalUrl")
						+ "Dispatcher?identity=activateaccount&code=" + code
						+ ">" + " \n" + " \n";
				text = text
						+ "Anschließend können Sie sich am Teleradiologie-Webportal anmelden."
						+ "\n" + "\n";

				text = text
						+ "\n"
						+ "Impressum:"
						+ "\n"
						+ "Testunternehmen"
						+ "\n"
						+ "Teststraße 4"
						+ "\n"
						+ "12345 Teststadt"
						+ "\n"
						+ "Diese Nachricht wurde automatisch vom Teleradiologie-Webportal Rhein-Neckar Dreieck generiert."
						+ "\n"
						+ "Wenn Sie sich mit uns in Verbindung setzen möchten, bitten wir Sie  eine E-Mail an die Administration zu senden."
						+ "\n" + "\n" + "Max Mustermann" + "\n"
						+ "Administration" + "\n"
						+ "E-Mail: max.mustermann@test.de";

				new Mail().sendMail(this.getEmailaddress(), defaultSubject,
						text);

				logger
						.info("Die Aktivierungsnachricht wurde erfolgreich an den Benutzer: "
								+ this.getForename()
								+ " "
								+ this.getSurname()
								+ " gesendet.");
			} catch (Exception e) {
				logger
						.error("Person.java - Beim Versenden der Aktivierungs-e-Mail an den Benutzer: "
								+ this.getForename()
								+ " "
								+ this.getSurname()
								+ " ist ein Fehler aufgetreten.");
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * Activates the user account which is stored in the database until the user
	 * clicks the link in the activation mail. This entry will be removed after
	 * the process.
	 * 
	 * @param code
	 *            activation code
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public static void activateAccount(String code) throws DatabaseException,
			BusinesslogicException {

		IActivationDatabaseEntry activationEntry = PersistenceService
				.getService().getActivationEntry(code);
		activationEntry.getPerson().setAccountActive(true);
		PersistenceService.getService().store(activationEntry.getPerson());
		PersistenceService.getService().remove(activationEntry);

	}

	/**
	 * {@inheritDoc}
	 */
	public void addRole(Roles role) throws BusinesslogicException {
		// Exception if a person is added to an existing role
		boolean exists = false;
		for (int i = 0; i < roles.size(); i++) {
			if (role.equals(roles.get(i).getRole())) {
				exists = true;
			}
		}
		if (!exists) {

			try {
				Role tmpRole = PersistenceService.getService().getRole(role);
				this.roles.add(tmpRole);
			} catch (DatabaseException e) {
				String message = "Rolle konnte nicht hinzugefügt werden";
				logger.error(message + " - " + e.getMessage());
				throw new BusinesslogicException(message);
			}
		}
		// this.roles.add(new R)
	}

	/**
	 * {@inheritDoc}
	 */
	public void setEmailaddress(String emailaddress)
			throws BusinesslogicException {

		if (org.openehealth.twp.tewepo.helper.Utility
				.isRichtigeEmailadresse(emailaddress)) {
			this.emailaddress = emailaddress;
		} else {
			throw new BusinesslogicException("E-Mail ungültig");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTitle(String title) throws BusinesslogicException {

		//if (Utility.isRightTitle(title)) {
			this.title = title;
//		} else {
//			throw new BusinesslogicException(
//					"Titel ungültig.");
//
//		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setForename(String forename) throws BusinesslogicException {

		if (Utility.isRightName(forename)) {
			this.forename = forename;
		} else {
			throw new BusinesslogicException(
					"Vorname ungültig, Sonderzeichen sind nicht zugelassen.");

		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSurname(String surname) throws BusinesslogicException {

		if (Utility.isRightName(surname)) {
			this.surname = surname;
		} else {
			throw new BusinesslogicException(
					"Nachname ungültig, Sonderzeichen sind nicht zugelassen.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPassword(String passwort) throws BusinesslogicException {

		if (passwort != null && passwort.length() > 6) {
			this.password = passwort;

		} else {
			throw new BusinesslogicException(
					"Ungültiges Passwort, das Passwort muss mindestes eine Ziffer und ein Sonderzeichen enthalten und aus min. 6 Zeichen bestehen.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLoginname(String loginname) throws BusinesslogicException {
		if (Utility.isRightLoginName(loginname))
			this.loginname = loginname;
		else
			throw new BusinesslogicException(
					"Ungültiger Loginname. Der Loginname muss aus min. 6 Zeichen bestehen und darf keine Sonderzeichen enthalten.");
	}

	// /* (non-Javadoc)
	// * @see org.openehealth.twp.tewepo.businesslogic.IPerson#setId(int)
	// */
	// public void setId(int id) throws BusinesslogicException {
	//
	// if (id >= 0)
	// this.id = id;
	// else
	// throw new BusinesslogicException("ID ung&uuml;ltig");
	// }

	/**
	 * {@inheritDoc}
	 */
	public void setOccupationgroup(String occupationgroup)
			throws BusinesslogicException {
		if (Utility.isRightAddressString(occupationgroup))
			this.occupationgroup = occupationgroup;
		else
			throw new BusinesslogicException(
					"Ungültige Berufsgruppenbezeichnung. Die Bezeichnung muss aus min. 3 Zeichen bestehen, Sonderzeichen sind nicht zugelassen.");
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDepartment(String department) throws BusinesslogicException {
		if (Utility.isRightAddressString(department))
			this.department = department;
		else
			throw new BusinesslogicException(
					"Ungültiger Abteilungsname. Die Bezeichnung muss aus min. 3 Zeichen bestehen, Sonderzeichen sind nicht zugelassen.");
	}

	/**
	 * {@inheritDoc}
	 */
	public void setOrganisation(String organisation)
			throws BusinesslogicException {
		if (Utility.isRightAddressString(organisation))
			this.organisation = organisation;
		else
			throw new BusinesslogicException(
					"Ungültiger Organisationsname. Die Bezeichnung muss aus min. 3 Zeichen bestehen, Sonderzeichen sind nicht zugelassen.");
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAddress(IAddress address) throws BusinesslogicException {
		if (address != null)
			this.address = address;
		else
			throw new BusinesslogicException("Ungültige Adresse");
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRoles(List<IRole> roles) {
		this.roles = roles;
	}

	/**
	 * {@inheritDoc}
	 */
	public long save() throws DatabaseException {

		long neuID = PersistenceService.getService().store(this);

		return neuID;

	}

	/**
	 * This method checks the syntax of the loginname and the password and calls
	 * the person in the database through another database-method.
	 * 
	 * @param loginname
	 *            loginname of the person
	 * @param passwort
	 *            password
	 * 
	 * @return the person with its loginname and password or null, if the person
	 *         does not exist.
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException} will be thrown, if the
	 *             loginname or the password contains an invalid character.
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public static IPerson login(String loginname, String passwort)
			throws BusinesslogicException, DatabaseException {

		if (!org.openehealth.twp.tewepo.helper.Utility.isRightLoginName(loginname)) {
			throw new BusinesslogicException("Ungültiger Loginname!<br>");
		}

		List<IPerson> possiblePersons = PersistenceService.getService()
				.getPersonsFromDB(loginname);

		for (IPerson p : possiblePersons) {
			if (p.getLoginname().equals(loginname)) {
				if (BCrypt.checkpw(passwort, p.getPassword()))
					return p;
			}
		}

		return null;

	}

	/**
	 * This method checks if the loginname already exists.
	 * 
	 * @param loginname
	 * 
	 * @return true if loginname exists false else
	 */
	public static boolean existsLogin(String loginname) {

		try {
			if (PersistenceService.getService().getPersonsFromDB(loginname)
					.size() > 0)
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 
	 * @param username
	 * @param surname
	 * @param forename
	 * @param email
	 * @return
	 * @throws DatabaseException
	 */
	public static boolean existMailadress(String email) throws DatabaseException {
		System.out.println("EMAIL: "+PersistenceService.getService().getPerson(email));
		try{
			if(PersistenceService.getService().getPerson(email) != null){
				return true;
			}
		}catch (Exception e) {
			return false;
		}
		return false;
	}
	
	/**
	 * Looks for a certain person in the database with the given surname,
	 * forename and e-mail address.
	 * 
	 * @param surname
	 *            surname of the person looked for
	 * @param forename
	 *            forename of the person looked for
	 * @param email
	 *            e-mail address of the person looked for
	 * 
	 * @return found person or null, if not found.
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public static IPerson getPerson(String surname, String forename,
			String email) throws DatabaseException {

		return PersistenceService.getService().getPerson(surname, forename,
				email);

	}

	/**
	 * Gets the person
	 * 
	 * @param username
	 * @param surname
	 * @param forename
	 * @param email
	 * @return
	 * @throws DatabaseException
	 */
	public static IPerson getPerson(String username, String surname,
			String forename, String email) throws DatabaseException {

		return PersistenceService.getService().getPerson(username, surname,
				forename, email);

	}
	
	

	/**
	 * Looks for a certain person in the database with the given ID.
	 * 
	 * @param id
	 *            ID of the person looked for
	 * @return found person or null, if not found.
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public static IPerson getPerson(long id) throws DatabaseException {

		return PersistenceService.getService().getPerson(id);

	}

	/**
	 * Gets all persons in the database.
	 * 
	 * @return a list of all persons in the database null, if there's no person
	 *         stored
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public static Vector<IPerson> getAllPersons() throws DatabaseException {

		return PersistenceService.getService().getAllPersons();

	}

	/**
	 * 
	 */
	@Override
	public String toString() {

		return getSurname() + ' ' + this.getForename() + ' '
				+ this.getLoginname() + ' ' + this.getPassword();

	}

	/**
	 * {@inheritDoc}
	 */
	public void removeRole(Roles role) {

		int position = -1;
		for (int i = 0; i < roles.size(); i++) {
			if (role.equals(roles.get(i).getRole())) {
				position = i;
				break;
			}
		}
		if (position != -1) {
			roles.remove(position);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public String getEmailaddress() {
		return emailaddress;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getTitle() {
		
		if(title == null){
			return "";
		}
		
		return title;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public String getForename() {
		return forename;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<IRole> getRoles() {
		return roles;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getLoginname() {
		return loginname;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOccupationgroup() {
		return occupationgroup;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOrganisation() {
		return organisation;
	}

	/**
	 * {@inheritDoc}
	 */
	public IAddress getAddress() {
		return address;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * {@inheritDoc}
	 */
	public long getId() {
		return super.getObjectID();
	}

}
