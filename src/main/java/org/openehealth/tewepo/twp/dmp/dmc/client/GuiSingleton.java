/*
 * Copyright 2012 the original author or authors.
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
package org.openehealth.tewepo.twp.dmp.dmc.client;

/**
 * Singleton-Class for GUI
 * 
 * @author devmis
 * 
 */
public class GuiSingleton {

	public static GwtGuiDemo singleton = null;

	/**
	 * Default Constructor
	 */
	private GuiSingleton() {
	}

	/**
	 * Singleton-Pattern
	 * 
	 * @return an instance of the class
	 */
	public static GwtGuiDemo getInstance() {
		return singleton;
	}

	/**
	 * Sets an instance of the class
	 * 
	 * @param instance
	 */
	public static void setInstance(GwtGuiDemo instance) {
		singleton = instance;
	}

}
