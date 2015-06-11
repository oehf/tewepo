/*
 * Created on 12.04.2005
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */

package org.openehealth.tewepo.twp.dmp.dmc.server.email.dicommail;


import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;
import java.util.Vector;


/**
 * @author mcdi
 *
 * Window - Preferences - Java - Code Style - Code Templates
 *
 * Bob need: One filter for GetAllFiles (return CFileFields), GetAllExtensions, GetAllStudyID;
 *           Idea is to save time (1 loop over files, instead of 4)
 */

public class dbridge_FileTools {
	
	/**
	 * Get files from directory
	 * 
	 * @param sDir
	 * @return
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
	 * Get not hidden files from directory
	 * 
	 * @param sDir
	 * @return
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
	 * Get not hidden files within sub-directories
	 * 
	 * @param sDir
	 * @return
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
	 * Creates unique names
	 * 
	 * @return
	 */
	static public String makeUniqName() {
		String result = new String();
		Calendar rightNow = Calendar.getInstance();
		int dayOfMonth = rightNow.get(Calendar.DAY_OF_MONTH);
		int month = rightNow.get(Calendar.MONTH) + 1;
		int year = rightNow.get(Calendar.YEAR);
		int sec = rightNow.get(Calendar.SECOND);
		int minuts = rightNow.get(Calendar.MINUTE);
		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		Random rnd = new Random(rightNow.getTimeInMillis());

		result = dayOfMonth + "-" + month + "-" + year + "_" + hour + "."
				+ minuts + "." + sec + "_" + rnd.nextInt(10000);
		return result;
	}

	/**
	 * Checks if DICOM file 
	 * 
	 * @param file
	 * @return
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
	 * @param files[]
	 */
	static public Collection ArrayToColl(File[] files) {
		LinkedList lst = new LinkedList();
		int n = files.length;
		for (int i = 0; i < n; i++)
			lst.add(files[i]);
		return lst;
	}

	/**
	 *List files in folder
	 * 
	 * @param dir
	 * @return
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
	 * @param colDirs
	 * @return
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
	 * Get filtered files
	 * 
	 * @param colDirs
	 * @param oFilter
	 */
	public static Collection GetFilteredFiles(Collection colDirs,
			FileFilter oFilter) {
		return (new CFilesFiltering(oFilter, new CCollectFilles()))
				.FilterFiles(ListFilesInFolders(colDirs));
	}

	/**
	 * Get filtered files
	 * 
	 * @param dir
	 * @param oFilter
	 * @return
	 */
	public static Collection GetFilteredFiles(File dir, FileFilter oFilter) {
		return (new CFilesFiltering(oFilter, new CCollectFilles()))
				.FilterFiles(ListFilesInFolder(dir));
	}

	/**
	 * Filter files
	 * 
	 * @param colFiles
	 * @param oFilter
	 * @return
	 */
	public static Collection FilterFiles(Collection colFiles, FileFilter oFilter) {
		return (new CFilesFiltering(oFilter, new CCollectFilles()))
				.FilterFiles(colFiles);
	}

	/**
	 * Filter files
	 * 
	 * @param colFields
	 * @param oFilter
	 * @return
	 */
	public static Collection FilterFields(Collection colFields,
			FileFilter oFilter) {
		return (new CFilesFiltering(oFilter, new CCollectFilles()))
				.FilterFiles(colFields);
	}

	// //
	// // StudyId retriever
	// //
	// public static String GetStudyId(String sFileName)
	// {
	// String sStudyUid = "";
	// try
	// {
	// AttributeList list = new AttributeList();
	// InputStream is = new FileInputStream(sFileName);
	// list.read(new DicomInputStream(is), TagFromName.SeriesInstanceUID);
	// is.close();
	// // Read required data from dicom file
	// sStudyUid = Attribute.getSingleStringValueOrDefault(list,
	// TagFromName.StudyInstanceUID, "");
	// }
	// catch(Exception ex)
	// {
	//			
	// }
	// return sStudyUid;
	// }
	// public static Collection GetAllStudyIds(Collection colFiles)
	// {
	// return (new CFilesFiltering(new CEmptyFileFilter(), new
	// CCollectStudyIds())).FilterFiles(colFiles);
	// }

	//
	// Extension methods
	//
	
	/**
	 * Get All Extensions
	 * 
	 * @param colFiles
	 */
	public static Collection GetAllExtensions(Collection colFiles) {
		return (new CFilesFiltering(new CEmptyFileFilter(),
				new CCollectExtensions())).FilterFiles(colFiles);
	}

	/**
	 * Get extension
	 * 
	 * 
	 * @param f
	 * @return
	 */
	static public String getExtension(File f) {
		String s = f.getName();
		return getExtension(s);
	}

	/**
	 * Get extension
	 * 
	 * @param sFile
	 * @return
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
	 * Get extension and name
	 * 
	 * @param sFile
	 * @return
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

	
}



/**
 * 
 */
class ExtensionsFilter implements FileFilter {
	/**
	 * 
	 */
	public ExtensionsFilter() {
		SetTypes(new LinkedList());
	}

	/**
	 * 
	 * @param types
	 */
	public ExtensionsFilter(Collection types) {
		SetTypes(types);
	}

	/**
	 * 
	 * @param types
	 */
	public void SetTypes(Collection types) {
		m_types = new LinkedList(types);
	}

	/**
	 * @param f
	 */
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return false;
		}
		String extension = dbridge_FileTools.getExtension(f);
		if (extension != null) {
			for (Iterator it = m_types.iterator(); it.hasNext();) {
				String ext = (String) it.next();
				if (extension.equals(ext)) {
					return true;
				}
			}
		}
		return false;
	}

	private LinkedList m_types;
}


/**
 * 
 */
class CEmptyFileFilter implements FileFilter {
	/**
	 * @param f
	 */
	public boolean accept(File f) {
		return true;
	}
}

/**
 * 
 * @author devmis
 *
 */
class AllFilesFilter implements FileFilter {
	/**
	 * @param f
	 */
	public boolean accept(File f) {
		if (f.isFile())
			return true;
		else
			return false;
	}
}

/**
 * 
 * @author devmis
 *
 */
class NotHiddenFilesFilter implements FileFilter {
	
	/**
	 * @param f
	 */
	public boolean accept(File f) {
		if (f.isFile()) {
			String fileName = (String) f.getName();
			// isHidden works for MacOs/Unix, but we add the same check for
			// Windows also
			if (f.isHidden() || fileName.startsWith("."))
				return false;
			else
				return true;
		}
		return false;
	}
}


/**
 * 
 */
class CFilesFiltering {
	/**
	 * 
	 */
	public CFilesFiltering() {
		Init(new CEmptyFileFilter(), new CEmptyFilesProcessor(),
				new CEmptyStatusListener());
	}

	/**
	 * 
	 * @param oFilter
	 */
	public CFilesFiltering(FileFilter oFilter) {
		Init(oFilter, new CEmptyFilesProcessor(), new CEmptyStatusListener());
	}

	/**
	 * 
	 * @param oFilter
	 * @param oProcessor
	 */
	public CFilesFiltering(FileFilter oFilter, IFilesProcessor oProcessor) {
		Init(oFilter, oProcessor, new CEmptyStatusListener());
	}

	/**
	 * 
	 * @param oFilter
	 * @param oProcessor
	 * @param oListener
	 */
	public CFilesFiltering(FileFilter oFilter, IFilesProcessor oProcessor,
			IStatusListener oListener) {
		Init(oFilter, oProcessor, oListener);
	}

	/**
	 * 
	 * @param oFilter
	 * @param oProcessor
	 * @param oListener
	 */
	private void Init(FileFilter oFilter, IFilesProcessor oProcessor,
			IStatusListener oListener) {
		SetFilter(oFilter);
		SetStatusListener(oListener);
		SetFilesProcessor(oProcessor);
	}

	/**
	 * 
	 * @param oFilter
	 */
	public void SetFilter(FileFilter oFilter) {
		mFilter = oFilter;
	}

	/**
	 * 
	 * @param oListener
	 */
	public void SetStatusListener(IStatusListener oListener) {
		mStatusListener = oListener;
	}

	/**
	 * 
	 * @param oProcessor
	 */
	public void SetFilesProcessor(IFilesProcessor oProcessor) {
		mProcessor = oProcessor;
	}

	/**
	 * 
	 * @param colFiles
	 * @return
	 */
	public Collection FilterFiles(Collection colFiles) {
		int n = colFiles.size();

		mStatusListener.StatusStart(n);
		mProcessor.Begin(n);

		int i = 0;
		for (Iterator it = colFiles.iterator(); it.hasNext();) {
			File f = (File) it.next();

			if (mFilter.accept(f))
				mProcessor.NextFile(f);

			mStatusListener.StatusNext(++i, n);
		}

		mProcessor.End();
		mStatusListener.StatusEnd();

		return mProcessor.GetResultColl();
	}

	private FileFilter mFilter;
	private IStatusListener mStatusListener;
	private IFilesProcessor mProcessor;
}

/**
 * 
 * @author devmis
 *
 */
interface IFilesProcessor {
	public void Begin(int n);

	public void NextFile(File f);

	public void End();

	public Collection GetResultColl();
}

/**
 * 
 * @author devmis
 *
 */
class CEmptyFilesProcessor implements IFilesProcessor {
	
	/**
	 * @param nMax
	 */
	public void Begin(int nMax) {
	}

	/**
	 * @param f
	 */
	public void NextFile(File f) {
	}

	/**
	 * 
	 */
	public void End() {
	}

	/**
	 * 
	 */
	public Collection GetResultColl() {
		return new Vector();
	}
}

/**
 * 
 * 
 */
class CCollectFilles implements IFilesProcessor {
	
	/**
	 * @param nMax
	 */
	public void Begin(int nMax) {
		mResult = new Vector(nMax);
	}

	/**
	 * @param f
	 */
	public void NextFile(File f) {
		mResult.add(f);
	}

	/**
	 * 
	 */
	public void End() {
	}

	/**
	 * 
	 */
	public Collection GetResultColl() {
		return mResult;
	}

	private Vector mResult;
}

/**
 * 
 * @author devmis
 *
 */
class CCollectExtensions implements IFilesProcessor {
	
	/**
	 * @param nMax
	 */
	public void Begin(int nMax) {
		mResult = new TreeSet();
	}

	/**
	 * 
	 * @param f
	 */
	public void NextFile(File f) {
		mResult.add(dbridge_FileTools.getExtension(f.toString()));
	}

	/**
	 * 
	 */
	public void End() {
	}

	/**
	 * 
	 */
	public Collection GetResultColl() {
		return mResult;
	}

	/**
	 * 
	 */
	private TreeSet mResult;
}


/**
 * 
 * 
 */
interface IStatusListener {
	
	/**
	 * 
	 * @param n
	 */
	public void StatusStart(int n);

	/**
	 * 
	 * @param i
	 * @param n
	 */
	public void StatusNext(int i, int n);

	/**
	 * 
	 */
	public void StatusEnd();
}

/**
 * 
 * @author devmis
 *
 */
class CEmptyStatusListener implements IStatusListener {
	
	/**
	 * @param n
	 */
	public void StatusStart(int n) {
	}

	/**
	 * @param i
	 * @param n
	 */
	public void StatusNext(int i, int n) {
	}

	/**
	 * 
	 */
	public void StatusEnd() {
	}
}
