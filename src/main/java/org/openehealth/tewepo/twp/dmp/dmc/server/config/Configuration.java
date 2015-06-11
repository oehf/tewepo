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
package org.openehealth.tewepo.twp.dmp.dmc.server.config;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Configuration of the web portal.
 * 
 */
@SuppressWarnings("serial")
public class Configuration extends Properties {

	/** the name of the config-file */
	private static final String dmc_server_CONFIG = "Configuration.properties";
	private Logger logger = Logger.getLogger(Configuration.class);

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
			logger.error("Configuration - Unknown config key \"" + key + "\"");
			throw new Error("Unknown config key \"" + key + "\"");
		} else {
			return value;
		}
	}

	/**
	 * @see java.util.Properties#setProperty(java.lang.String, java.lang.String)
	 *      Sets to a given key the given value.
	 * 
	 * @param key
	 *            the required attribute to change it's content value the new
	 *            value to set to the required key
	 * @return the changed object key+value
	 */
	@Override
	public Object setProperty(String key, String value) {

		Object o = super.setProperty(key, value);
		if (o == null) {
			logger.error("Configuration - Value could not be set \"" + value
					+ "\"");
			throw new Error("Value could not be set \"" + value + "\"");
		}

		return o;
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
			logger
					.error("Configuration - Invalid integer format in config key \""
							+ key + "\"");
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
			logger
					.error("Configuration - Invalid boolean format in config key \""
							+ key + "\"");
			throw new Error("Invalid boolean format in config key \"" + key
					+ "\"");
		}
	}

	/**
	 * Returns a propertie object together with the valid global configuration
	 * 
	 * @return the global configuration
	 */
	public static Configuration getMainConfig() {
		Configuration prop = new Configuration();
		try {
			prop.load(Configuration.class
					.getResourceAsStream(dmc_server_CONFIG));
		} catch (FileNotFoundException fe) {

			throw new Error("File doesn't exist.");
		} catch (IOException ioe) {
			throw new Error("File could not be read.");
		}
		return prop;
	}

	public static void setMainConfig(String key, String value) {
		// load old one
		Configuration prop = getMainConfig();

		try {
			FileOutputStream out = new FileOutputStream(dmc_server_CONFIG);

			// modifies existing or adds new property
			prop.setProperty(key, value);

			// save modified property file
			prop.store(out, null);
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}