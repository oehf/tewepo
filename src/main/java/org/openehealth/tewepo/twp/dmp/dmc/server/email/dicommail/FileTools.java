/*
 * Created on 12.04.2005
 *
 */

package org.openehealth.tewepo.twp.dmp.dmc.server.email.dicommail;



import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.DicomInputStream;
import com.pixelmed.dicom.TagFromName;



/**
 * Class implements Tools for files
 * 
 * @author mcdi
 *
 *
 * 
 */
public class FileTools {

	/**
	 * Gets the files from directory
	 * 
	 * @param sDir - the path of the directory
	 * @return vFiles
	 */
	public static Vector GetFilesFromDir(String sDir) {
		Vector vFiles = new Vector();
		File dir = new File(sDir);
		File[] oFiles = dir.listFiles();

		for (int i = 0; i < oFiles.length; i++) {
			if (oFiles[i].isFile()) {
				vFiles.add(oFiles[i].getPath());
			}
		}
		return vFiles;
	}
	
	/**
	 * Deletes the directory
	 * 
	 * @param path - the path of the directory as a String
	 * @return true; if deleted the directory
	 * 			false; if directory is not deleted
	 */
	public static boolean deleteDir(String path) {
		
		File dir = new File(path);
		return deleteDir(dir);
	}
	
	/**
	 * * Deletes the directory
	 * 
	 * @param path - the path of the directory as a File
	 * @return true; if deleted the directory
	 * 			false; if directory is not deleted
	 */
	public static boolean deleteDir(File dir) {

		if(dir.isDirectory()){
			String[] entries= dir.list();
			for(int i=0; i<entries.length;i++){
				File currFile=new File(dir.getPath(),entries[i]);
				deleteDir(currFile);
			}
			if(dir.delete()){
				return true;
			}else{
				return false;
			}
		}else{
			if(dir.delete()){
				return true;
			}else{
				return false;
			}
		}
		
	}
	

	/**
	 * Gets not hidden Files from directory
	 * 
	 * @param sDir -  path of the directory as a String
	 * @return vFiles
	 */
	public static Vector GetNotHiddenFilesFromDir(String sDir) {
		Vector vFiles = new Vector();
		File dir = new File(sDir);
		File[] oFiles = dir.listFiles();

		for (int i = 0; i < oFiles.length; i++) {
			if (oFiles[i].isFile()) {
				String fileName = (String) oFiles[i].getName();
				// isHidden works for MacOs/Unix, but we add the same check for
				// Windows also
				if (oFiles[i].isHidden() || fileName.startsWith(".")) {
					// doing nothing
					;
				} else {
					vFiles.add(oFiles[i].getPath());
				}
			}
		}
		return vFiles;
	}

	/**
	 *  Gets not hidden files within subdirs
	 * 
	 * @param sDir - path of directory as String
	 * @return vFiles
	 */
	public static Vector GetNotHiddenFilesWithinSubdirs(String sDir) {
		//
		// 1. Get files
		//
		Vector vFiles = new Vector();
		vFiles = GetNotHiddenFilesFromDir(sDir);

		//
		// 2. Process all subdirs
		//
		File dir = new File(sDir);
		File[] oFiles = dir.listFiles();
		for (int i = 0; i < oFiles.length; i++) {
			if (oFiles[i].isDirectory()) {
				// Vector vSubFiles = new Vector();
				String sSubDirPath = (String) oFiles[i].getPath();
				// isHidden works for MacOs/Unix, but we add the same check for
				// Windows also
				if (oFiles[i].isHidden()) {
					// doing nothing
					;
				} else {
					// vSubFiles=GetNotHiddenFilesWithinSubdirs(sSubDirName);
					vFiles.addAll(GetNotHiddenFilesWithinSubdirs(sSubDirPath));
				}
			}
		}
		return vFiles;
	}

	/**
	 * Creates unique names with day, month, year and the hour
	 * 
	 * @return result
	 */
	public static String makeUniqName() {
		String result = new String();
		Calendar rightNow = Calendar.getInstance();
		int dayOfMonth = rightNow.get(Calendar.DAY_OF_MONTH);
		int month = rightNow.get(Calendar.MONTH) + 1;
		int year = rightNow.get(Calendar.YEAR);
		int sec = rightNow.get(Calendar.SECOND);
		int minuts = rightNow.get(Calendar.MINUTE);
		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		Random rnd = new Random(rightNow.getTimeInMillis());

		result = (((dayOfMonth < 10) ? "0" : "") + dayOfMonth) + "-"
				+ (((month < 10) ? "0" : "") + month) + "-" + year + "_" + hour
				+ "." + minuts + "." + sec + "_" + rnd.nextInt(10000);
		return result;
	}

	/**
	 * Creates unique names they are formatted e.g. 20100905.121012.200
	 * 
	 * @return result
	 */

	public static String makeUniqueNameFormatted() {
		String result = new String();
		Calendar rightNow = Calendar.getInstance();
		int dayOfMonth = rightNow.get(Calendar.DAY_OF_MONTH);
		int month = rightNow.get(Calendar.MONTH) + 1;
		int year = rightNow.get(Calendar.YEAR);
		int sec = rightNow.get(Calendar.SECOND);
		int minuts = rightNow.get(Calendar.MINUTE);
		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		Random rnd = new Random(rightNow.getTimeInMillis());

		result = year + "" + (((month < 10) ? "0" : "") + month) + ""
				+ (((dayOfMonth < 10) ? "0" : "") + dayOfMonth) + "." + hour
				+ "" + minuts + "" + sec + "."
				+ new Integer(rnd.nextInt(10000));
		return result;
	}

	/**
	 * Gets the current date
	 * 
	 * @return current date as String
	 */

	public static String getCurrDate() {
		return Calendar.YEAR + "" + Calendar.MONTH + "" + Calendar.DAY_OF_MONTH;
	}
	
	/**
	 * Gets the current year
	 * 
	 * @return yearInt 
	 */

	public static int getCurrYear() {
		
		int yearInt = Calendar.YEAR;
		
	return yearInt;
	}

	/**
	 * Checks if file is type of dicom
	 * 
	 * @param file
	 * @return true; if file is dicom. fales; if file is not dicom
	 * @throws java.io.IOException
	 */

	static public boolean isDICMfile(InputStream file)
			throws java.io.IOException {
		boolean result = false;
		byte[] buffer = new byte[132];

		result = ((file.read(buffer) != -1) && ((buffer[128] == 'D')
				&& (buffer[129] == 'I') && (buffer[130] == 'C') && (buffer[131] == 'M')));
		file.close();
		return result;
	}

	//
	// Simple listing functions
	//
	/**
	 * Function to listing the files
	 * 
	 * @param files
	 * @return lst
	 */
	static public Collection ArrayToColl(File[] files) {
		LinkedList lst = new LinkedList();
		int n = files.length;
		for (int i = 0; i < n; i++)
			lst.add(files[i]);
		return lst;
	}

	/**
	 * List files in folder
	 * 
	 * @param dir - an existing file
	 * @return List of files
	 */
	static public Collection ListFilesInFolder(File dir) {
		Collection colFiles = null;
		if (dir.exists()) {
			File[] files = dir.listFiles();
			files = (files != null) ? files : new File[0];
			colFiles = ArrayToColl(files);
		} else {
			colFiles = new LinkedList();
		}
		return colFiles;
	}

	/**
	 * List files in folders
	 * 
	 * @param colDirs - List of directories
	 * @return List of files
	 */
	static public Collection ListFilesInFolders(Collection colDirs) {
		LinkedList colFiles = new LinkedList();
		for (Iterator it = colDirs.iterator(); it.hasNext();) {
			Collection col = ListFilesInFolder((File) it.next());
			colFiles.addAll(col);
		}
		return colFiles;
	}

	//
	// File filtering methods
	//


	/**
	 * Gets the Study Instance UID
	 * 
	 * @param sFileName
	 * @return sStudyUID - String
	 * 
	 */
	public static String getStudyInstanceUID(String sFileName) {
		String sStudyUID = "";
		try {
			AttributeList list = new AttributeList();
			InputStream is = new FileInputStream(sFileName);
			list.read(new DicomInputStream(is), TagFromName.SeriesInstanceUID);
			is.close();
			// Read required data from dicom file
			sStudyUID = Attribute.getSingleStringValueOrDefault(list,
					TagFromName.StudyInstanceUID, "");
		} catch (Exception ex) {

		}
		return sStudyUID;
	}

	/**
	 * Gets the extension of a file
	 * @param f
	 * @return extension of the file
	 */
	static public String getExtension(File f) {
		String s = f.getName();
		return getExtension(s);
	}

	/**
	 * Gets the extension of a String
	 * @param sFile
	 * @return ext
	 */
	static public String getExtension(String sFile) {
		String ext = "";
		int i = sFile.lastIndexOf('.');

		if (i > 0 && i < sFile.length() - 1) {
			ext = sFile.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	
	/**
	 * Gets the extension and the name of a String
	 * @param sFile
	 * @return v
	 */
	static public Vector getExtensionAndName(String sFile) {
		Vector v = new Vector(2);
		int i = sFile.lastIndexOf('.');
		if (i > 0 && i < sFile.length() - 1) {
			v.add(0, sFile.substring(0, i));
			v.add(1, sFile.substring(i + 1).toLowerCase());
		} else {
			v.add(0, sFile);
			v.add(1, "");
		}
		return v;
	}

	/**
	 * 
	 * @author mcdi
	 *
	 */
	class StudyIdsFilter implements FileFilter {
		
		/**
		 * Constructor
		 */
		public StudyIdsFilter() {
			SetIds(new LinkedList());
		}
/**
 * 
 * @param ids
 */
		public StudyIdsFilter(Collection ids) {
			SetIds(ids);
		}
/**
 * 
 * @param ids
 */
		public void SetIds(Collection ids) {
			m_ids = new LinkedList(ids);
		}

		/**
		 * 
		 */
		public boolean accept(File f) {
			// if (f.isDirectory()) {
			// return false;
			// }
			// String sStudyId = FileTools.GetStudyId(f.getPath());
			// for (Iterator it = m_ids.iterator(); it.hasNext(); )
			// {
			// String id = (String)it.next();
			// if (sStudyId.equals(id))
			// {
			// return true;
			// }
			// }
			return false;
		}

		private LinkedList m_ids;
	}

}
