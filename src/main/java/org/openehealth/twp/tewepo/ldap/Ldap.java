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
package org.openehealth.twp.tewepo.ldap;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * An object of this class represents an LDAP account.
 * 
 * @author Benjamin Schneider
 * 
 */
public class Ldap {

	final static String ADMIN_NAME = "CN=Administrator,CN=Users,DC=home,DC=tutorials,DC=de";

	final static String ADMIN_PASSWORD = "DUMMYDUMMYDUMMY";

	final static String SEARCH_BASE = "DC=home,DC=tutorials,DC=de";

	static LdapContext ctx;

	/**
	 * Initializes the data.
	 * 
	 * @throws Exception
	 */
	static void init() throws Exception {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, ADMIN_NAME);
		env.put(Context.SECURITY_CREDENTIALS, ADMIN_PASSWORD);
		// Der entsprechende Dom�nen-Controller
		env.put(Context.PROVIDER_URL, "ldap://192.168.75.96:340");
		ctx = new InitialLdapContext(env, null);
	}

	/**
	 * Finds users by account name.
	 * 
	 * @param accountName
	 *            of the user
	 * 
	 * @return list of all found users by the given account name
	 * 
	 * @throws Exception
	 */
	static List<String> findUsersByAccountName(String accountName)
			throws Exception {
		List<String> list = new ArrayList<String>();

		String snPrefix = accountName.substring(0, 2) + "*";
		String givenNamePrefix = accountName.substring(2) + "*";

		// LDAP query ..
		String searchFilter = "(&(objectClass=user)(sn=" + snPrefix
				+ ")(givenName=" + givenNamePrefix + "))";

		SearchControls searchControls = new SearchControls();
		String[] resultAttributes = { "sn", "givenName", "sAMAccountName" };
		searchControls.setReturningAttributes(resultAttributes);
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		NamingEnumeration<SearchResult> results = ctx.search(SEARCH_BASE,
				searchFilter, searchControls);

		while (results.hasMoreElements()) {
			SearchResult searchResult = (SearchResult) results.nextElement();
			list.add(searchResult.toString());
		}
		return list;
	}

}
