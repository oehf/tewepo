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
package org.openehealth.twp.tewepo.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Configuration of the web portal.
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class Configuration extends Properties {

	/** the name of the config-file */
	private static final String TEWEPO_CONFIG = "Configuration.properties";

	private static URI CONFIGURI = null;

	/**
	 * @see java.util.Properties#setProperty(java.lang.String, java.lang.String)
	 * 
	 *      Sets to a given key the given value.
	 * 
	 * @param key
	 *            the required attribute to change it's content value the new
	 *            value to set to the required key
	 * 
	 * @return the changed object key+value
	 */
	@Override
	public Object setProperty(String key, String value) {

		Object o = super.setProperty(key, value);
		if (o == null) {
			throw new Error("Value could not be set \"" + value + "\"");
		}
		return o;
	}

	/**
	 * Gets the value of the given attribute as String.
	 * 
	 * @param key
	 *            the required attribute
	 * 
	 * @return the value of the required attribute.
	 */
	@Override
	public String getProperty(String key) {
		String value = super.getProperty(key);
		if (value == null) {
			throw new Error("Unknown config key \"" + key + "\"");
		} else {
			return value;
		}
	}

	/**
	 * Gets the value of the given attribute as Integer.
	 * 
	 * @param key
	 *            the required attribute
	 * 
	 * @return the value of the required attribute.
	 */
	public int getIntProperty(String key) {
		try {
			return Integer.parseInt(getProperty(key));
		} catch (NumberFormatException e) {
			throw new Error("Invalid integer format in config key \"" + key
					+ "\"");
		}
	}

	/**
	 * Gets the value of the given attribute as boolean.
	 * 
	 * @param key
	 *            the required attribute
	 * 
	 * @return the value of the required attribute.
	 */
	public boolean getBoolProperty(String key) {
		String value = getProperty(key);
		if (value.equalsIgnoreCase("true")) {
			return true;
		} else if (value.equalsIgnoreCase("false")) {
			return false;
		} else {
			throw new Error("Invalid boolean format in config key \"" + key
					+ "\"");
		}
	}

	/**
	 * Gets a properties-object with the valid global configuration by reading
	 * the configuration file.
	 * 
	 * @return prop the global configuration
	 */
	public static Configuration getMainConfig(String path) {

		Configuration prop = new Configuration();

		FileInputStream fis = null;
		try {
			File newFile = null;

			if (path.length() > 0) {
				newFile = new File(path + TEWEPO_CONFIG);
				
				CONFIGURI = newFile.toURI();
			} else {

				CONFIGURI = Configuration.class.getResource(TEWEPO_CONFIG)
						.toURI();
				
				newFile = new File(CONFIGURI);
			}
			fis = new FileInputStream(newFile);
			prop.load(fis);

			// prop.getProperty("maxConnections");
		} catch (FileNotFoundException fe) {
			throw new Error("File doesn't exist.");
		} catch (IOException ioe) {
			throw new Error("File could not be read.");
		} catch (URISyntaxException ue) {
			throw new Error("Invalid string.");
		}

		return prop;
	}

	/**
	 * Writes the configuration file.
	 */
	public void serialize() {
		try {

			File newFile = new File(CONFIGURI);
			FileOutputStream fos = new FileOutputStream(newFile);
			this.store(fos, "Configuration File");
			fos.close();

		} catch (FileNotFoundException fe) {
			throw new Error("File doesn't exist.");
		} catch (IOException ioe) {
			throw new Error("File could not be read.");
		}

	}
}