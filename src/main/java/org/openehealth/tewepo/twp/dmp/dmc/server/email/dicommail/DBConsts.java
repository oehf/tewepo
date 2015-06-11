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
package org.openehealth.tewepo.twp.dmp.dmc.server.email.dicommail;

/**
 * <p>Title: dbridge</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

/*
 * Created: 23.02.05
 * Modified 24.03.05-20.05.05
 * Modified 10.08.05
 * Modified 25.11.05
 * Modified 2.02.06-27.02.06
 * Modified 10.05.06-4.06.06
 */

import java.io.File;


/**
 * Declarate constant variables
 * 
 * @author devmis
 *
 */
class DBConsts {
	// OOO setting
	public static final String WINDOW_ICON_FILENAME = "./lib/res/dbridge_client.gif";

	// User standard folders
	public static final String EXT_DIR = "Ext";
	public static final String LOC_DIR = "Loc";
	public static final String ARCHIVE_DIR = "Archive";
	public static final String DECRYPTED_DIR = "Decrypted";
	public static final String WAIT_DIR = "Wait";

	// Temporary files extensions
	public static final String DECRYPT_TMP_EXT = "decr_tmp";
	public static final String ASC_TMP_EXT = "asc_tmp";
	public static final String BUILD_TMP_EXT = "bld_tmp";

	// Garbage collector settings
	// public static final String GARBAGE_START_TIME="03:00"; // time
	// public static final long GARBAGE_REPEAT_PERIOD=24; // hours
	// public static final double GARBAGE_BAD_FILE_DIR_TIMEOUT=2; // Hours
	public static String GARBAGE_START_TIME = "03:00"; // time
	public static long GARBAGE_REPEAT_PERIOD = 24; // hours
	public static double GARBAGE_BAD_FILE_DIR_TIMEOUT = 2; // Hours

	// logger
	public static final String LOG_FILE = "current.log";

	// FileManagers Constants
	public static int DCM_FILES_SEND_TOGETHER = 10; // n files in a send gruppe
	public static int DCM_PARAMS_SEND = 6; // n parameters

	// XTags Constants
	public static final String XTAG_STUDYID = "X-TELEMEDICINE-STUDYID";
	public static final String XTAG_ID = "X-TELEMEDICINE-SETID";
	public static final String XTAG_PART = "X-TELEMEDICINE-SETPART";
	public static final String XTAG_TOTAL = "X-TELEMEDICINE-SETTOTAL";
	public static final String XTAG_OCID = "X-TELEMEDICINE-ORIGINAL-CONTENT-ID";
	public static final String XTAG_DNKEYID = "X-TELEMEDICINE-DISPOSITION-NOTIFICATION-KEYID";
	public static final String XTAG_DNTO = "X-TELEMEDICINE-DISPOSITION-NOTIFICATION-TO";
	public static final String XTAG_REPORT = "X-TELEMEDICINE-REPORT";
	public static final String XTAG_CONTENTID = "X-TELEMEDICINE-ORIGINAL-CONTENT-ID";
	public static final String DN_RFC = "Disposition-Notification-To";
	public static final String OMID_RFC = "Original-Message-ID";
	public static final String MID_RFC = "Message-ID";

	// MDN constants
	public static final String MDN_OK = "automatic-action/MDN-sent-automatically;displayed";
	public static final String MDN_WARNING = "automatic-action/MDN-sent-automatically;displayed/warning";
	public static final String MDN_ERROR = "automatic-action/MDN-sent-automatically;deleted/error"; // resend
																									// is
																									// possible
	public static final String MDN_HARDERROR = "automatic-action/MDN-sent-automatically;deleted"; // resend
																									// is
																									// not
																									// possible
	public static final int MDN_NONE = 0; // No conception
	public static final int MDN_RFC = 1; // RFC conception
	public static final int MDN_PGP = 2; // PGP/MIME conception
	public static final int MDN_MIX = 3; // Mixed

	// PGP Signature constants
	public static final int PGPSIGN_NONE = 0; // No conception
	public static final int PGPSIGN_RFC = 1; // RFC conception
	public static final int PGPSIGN_PGP = 2; // PGP/MIME conception
	public static final int PGPSIGN_MIX = 3; // Mixed

	// SMTP connection constants
	public static final int SMTP_RECONNECT_NFILES = 100; // reconnect after
															// sending every 100
															// files
	public static final int SMTP_RECONNECT_TIME = 600000; // reconnect every 10
															// minutes
	// SMTP split files usage
	// public static final boolean SMTP_SEND_SPLITTED=true;
	// public static final int SMTP_SPLIT_SIZE=5*1024*1024; // bytes 5 megs

	// MessageParses Constants
	public static final String PARSER = "_PARSER";

	// Transaction DB
	public static final String TRANSACTION_DB_Name = "."
			+ (new File("*.*")).separator + "dbmdb.dbs";

}
