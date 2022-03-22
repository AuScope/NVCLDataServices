package org.auscope.nvcl.server.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.jms.Destination;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.auscope.nvcl.server.util.Utility;
import org.auscope.nvcl.server.vo.ConfigVo;
import org.auscope.nvcl.server.vo.MessageVo;
import org.auscope.nvcl.server.vo.TsgParamVo;
import org.springframework.jms.core.JmsTemplate;

/**
 * Service class that provide the following services :
 * <ol>
 * <li>Create script file
 * <li>Execute TSG dataset download
 * <li>Compress downloaded dataset into a zip file
 * <li>Move the zip file to the http/ftp root folder
 * <li>Clean up cached and log files
 * <li>Browse queue messages
 * </ol>
 *
 * @author Florence Tan
 */

public class NVCLDownloadSvc {

	
	private static final Logger logger = LogManager.getLogger(NVCLDownloadSvc.class);

	/**
	 * Creating a script file with file name base on : datasetiddd.txt
	 * 
	 * @param	ConfigVo	configuration value object containing the config.properties info
	 * @param	urlParamVo	URL parameters specify by the requestor
	 * @return	String 		returning script fileName if successfully created else return fail as fileName 
	 */
	public String createScriptFile(TsgParamVo tsgParamVo) {

		String fileNameNoExt = null;
		String fileName = null;
		try {

			//Creating new file base on dataset id 
			String dlDirName = tsgParamVo.getDatasetid();
			fileNameNoExt = dlDirName;
			fileName = dlDirName + ".txt";
			String scriptDir = config.getTsgScriptPath();
			File scriptFile = new File(scriptDir, fileName);
			logger.debug("script file : " + scriptFile);
			scriptFile.createNewFile();
			//Create new download directory name which is same as scriptFile name
			File downloadDir = new File(config.getDownloadCachePath(),  dlDirName);
			logger.debug("downloadDir : " + downloadDir);
			if (!downloadDir.exists()) {
				downloadDir.mkdir();
			}
			//Writing to Script File
			PrintWriter output = new PrintWriter(scriptFile);
			output.println("task_begin");
			output.println("operation download ");
			output.println("Connection_string " + config.getJdbcURL());
			if (!Utility.stringIsBlankorNull(config.getAzureBlobStoreConnectionString())) output.println("AzureBlobStore " + config.getAzureBlobStoreConnectionString());
			output.println("Database_type " + config.getJdbcDbType());
			output.println("Username " + config.getJdbcUsername());
			output.println("Password " + config.getJdbcPassword());
			output.println("output_dir " + downloadDir);
			output.println("Uuid " + tsgParamVo.getDatasetid());
			output.println("task_end");
			output.flush();
			output.close();
		} catch (IOException e) {
			logger.error("script file generating exception  : " + e);
			fileNameNoExt = "fail";
		}

		return fileNameNoExt;
	}





	/**
	 * Executing the TSG Program to download the TSG Dataset, wait for the process to end.
	 * Get the exitValue of the process, 0=success and !0=program terminated abnormally
	 * 
	 * @param	tsgExePath		Path for the tsg exe program
	 * @param	scriptFileName	The full path where script file reside and the name 
	 * 							of the script file to be executed by the tsg exe program
	 * @return	int				return an integer value, 0=success, anything else=fail	
	 */	 
	public int execTSGDownload(String tsgExePath, String scriptFile) {

		try {
			//generating command and executing command
			//for windows env :
			// e.g. Command : "C:\Program Files\The Spectral Geologist\tsgeol7.exe" /script=C:\TSG\script\ba8a2605-7676-45c7-88a6-505b85748bb.txt
			//for unix env (using WINE):
			// e.g. command : /usr/local/nvcl/runtsg.sh /script=/usr/local/nvcl/.wine/drive_c/scripts/ba8a2605-7676-45c7-88a6-505b85748bb.txt

			logger.debug("Start executing download process...");
			Runtime rt = Runtime.getRuntime();
			//String acommand = "\"" + tsgExePath + "\" /script=" + scriptFile;
			//check if tsgExePath is for windows env or unix env (with comma)
			String[] exePath = null;			 
			Process proc = null;

			//this program allows tsgExePath in config.properties to have more than one parameters.
			//comma is used to separate the exe program path and parameter(s)
			exePath = tsgExePath.split(",");

			//generate command line
			if (exePath.length > 1) {
				//parameter found
				String[] aCommand = new String[exePath.length+1];
				for (int i = 0; i < exePath.length; i++) {
					aCommand[i] = exePath[i] ;
				}
				aCommand[exePath.length]="/script=" + scriptFile;				 
				logger.debug("tsg Command and parameter array : " + Arrays.toString(aCommand));
				proc = rt.exec(aCommand);				 
			} else {
				//no parameter specify
				String aCommand = null;
				aCommand = tsgExePath + " /script=" + scriptFile;
				logger.debug("tsg Command : " + aCommand);
				proc = rt.exec(aCommand);
			}


			//handling error message if any 
			logger.debug("Start reading STDOUT and STDERR from the process...");		 
			ProcessStreamReader inputStream = new ProcessStreamReader(proc.getInputStream());
			ProcessStreamReader errorStream = new ProcessStreamReader(proc.getErrorStream());
			inputStream.start();
			errorStream.start();

			//waiting for the process to complete and getting the result value, 0=success, anything else =faill
			logger.debug("Waiting for the external process to complete...");
			int exitVal = proc.waitFor();
			//waiting for both reader threads to die
			logger.debug("Waiting for both reader threads to die");
			inputStream.join();
			errorStream.join();

			//if error messages produced
			String errString = errorStream.getString();
			if(errString != null && errString.trim().length() > 0) {
				logger.error("ERRORS from executing the external process : " + errString);
			}

			//if any STDOUT from the process
			String inString = inputStream.getString();
			if(inString != null && inString.trim().length() > 0) {
				logger.error("STDOUT from the external process : " + inString);
			}

			//return with the result value
			logger.debug("returning exitVal : " + exitVal);
			return exitVal;

		} catch (IOException ioex) {
			logger.error("IOException : " + ioex);
			return 1;
		} catch (InterruptedException iex) {
			logger.error("InterruptedException : " + iex);
			return 1;
		} catch (Exception ex) {
			logger.error("Exception : " + ex);
			return 1;			 
		}
	}


	/**
	 * Zip the folder that consists of the downloaded TSG dataset
	 * 
	 * @param	downloadPath	The full path where the tsgdataset has been downloaded to
	 * @param	folderName		Folder with downloaded TSG dataset that need to be 
	 * 							compressed into a zip file
	 * @return	int				return an integer value, 0=success, anything else=fail	
	 */	 
	public int zipFolder(String downloadCachePath,String folderName) {

		int returnVal = 0;
		try {
			File inFolder = new File(downloadCachePath+folderName);
			File outFilename = new File(downloadCachePath+folderName+".zip");
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));
			int len = inFolder.getAbsolutePath().lastIndexOf(File.separator);
			String baseName = inFolder.getAbsolutePath().substring(0,len+1);
			addFolderToZip(inFolder, out, baseName);
			out.close();
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException : " + e);
			returnVal = 1;
		} catch (IOException e) {
			logger.error("IOException : " + e);
			returnVal = 1;
		}

		return returnVal;

	}


	/**
	 * Zip a specific file
	 * 
	 * @param	cachePath	The full path where the file located 
	 * @return	int			Return an integer value, 0=success, anything else=fail	
	 */	 
	public int zipFile(String downloadCachePath, String fileName) {

		int returnVal = 0;
		String xmlFileName = downloadCachePath + fileName + ".xml";
		String zipFileName = downloadCachePath + fileName + ".zip";

		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
			FileInputStream in  = new FileInputStream(xmlFileName);
			ZipEntry zipEntry = new ZipEntry(fileName+".xml");
			out.putNextEntry(zipEntry);
			IOUtils.copy(in,out); 
			out.closeEntry();
			//note : need to close the FileInputeStream, else not able to delete the file
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException : " + e);
			returnVal = 1;
		} catch (IOException e) {
			logger.error("IOException : " + e);
			returnVal = 1;
		}

		return returnVal;

	}


	private static void addFolderToZip(File folder, ZipOutputStream zip, String baseName) throws IOException {
		File[] files = folder.listFiles();
		for (File file: files) {
			if (file.isDirectory()) {
				addFolderToZip(file,zip,baseName);
			} else {
				String name = file.getAbsolutePath().substring(baseName.length());
				ZipEntry zipEntry = new ZipEntry(name);
				zip.putNextEntry(zipEntry);
				FileInputStream inStream = new FileInputStream(file); 
				IOUtils.copy(inStream,zip);
				zip.closeEntry();
				//note : need to close the FileInputeStream, else not able to delete the file
				inStream.close();

			}
		}
	}

	/**
	 * Move/Copy the zip file from the tsg download location to http/ftp download root path
	 * 
	 * @param	zipFile			zip file name 
	 * @param	targetFolder	Target folder where the zip file need to move to
	 * @param	downloadCache	if download.cache is yes, use FileInputStream and FileOutputStream object to copy/overwrite file	 
	 */
	public int moveFile(String zipFile, String targetFolder, String downloadCache) {
		int moveSuccess = 0;
		try {
			File file = new File(zipFile);
			logger.debug("file.getName() : " + file.getName());
			File targetDir = new File(targetFolder);				 
			File dest = new File(targetDir, file.getName());
			logger.debug("newFile : " + dest);
			if (downloadCache.equals("yes")) {
				//with download.cache=yes, we have previously checked and confirm the zip file doesn't exists in download.rootpath,
				//thus just move the zip file to the target folder
				boolean success = file.renameTo(dest);
				if (success) {
					logger.debug("zip file successfully moved to target download dir..."); 
				} else {
					logger.debug("failed to move zip file to target download dir...");
					moveSuccess = 1;
				}				
			} else {
				//with download.cache=no, meaning downloaded files in cache folder will be removed but not the zip file in 
				//download.rootpath where user can download it anytime.  thus use copyTo with REPLACE_EXISTING option and remove 
				//the original zip file
				FileInputStream from = new FileInputStream(file);
				FileOutputStream to  = new FileOutputStream(dest);
				byte[] buffer = new byte[1024];
				int bytes_read;
				while ((bytes_read = from.read(buffer)) != -1 ) {
					//read and write until eof
					to.write(buffer, 0, bytes_read);
				}
				from.close();
				to.close();
				//finally remove the zip file in cache download folder

			}
		} catch (IOException e) {
			logger.error("IOException occured while moving / copying file : " + e);
			moveSuccess = 1;
		} catch (Exception e) {
			logger.error("exception occured while moving / copying file : " + e);
			moveSuccess = 1;
		}
		return moveSuccess;
	}

	/**
	 * Remove specific file in specific folder
	 * 
	 * @param	downloadCachePath 	The directory where all the cached or downloaded tsg dataset
	 * 						are stored.
	 * @param	fileName		The file that need to be removed.	 
	 */
	public boolean removeFile(String downloadCachePath,String fileName) {
		boolean removeStatus = true;
		try {
			File file = new File(downloadCachePath+fileName);
			removeStatus = file.delete();
			logger.debug("removeStatus for " + file + " : " + removeStatus);
		} catch (Exception e) {
			logger.error("error occured while removing file : " + e);
			removeStatus = false;
		}

		return removeStatus;
	}


	/**
	 * Clean up the cached files which include the main downloaded dataset folder and
	 * script file (same name as the folder name). 
	 * 
	 * @param	downloadCachePath 	The directory where all the cached or downloaded tsg dataset
	 * 						are stored.
	 * @param	folderName		The folder that need to be removed.	 
	 */
	public boolean removeFolder(String downloadCachePath,String folderName) {
		boolean removeStatus = true;
		try {
			File folder = new File(downloadCachePath+folderName);
			removeStatus = deleteDir(folder);
			logger.debug("removeStatus for " + folder + " : " + removeStatus);
		} catch (Exception e) {
			logger.error("error occured while removing folder : " + e);
			removeStatus = false;
		}

		return removeStatus;
	}

	/**
	 * Remove directory including all sub directory
	 * @param 	dir 		directory to be removed
	 * @return	boolean 	if successfully removed the directory
	 * @throws IOException
	 */
	private static boolean deleteDir(File dir) throws IOException {

		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();

	}


	/**
	 * Browse queue message(s) from both request and reply queue and set them
	 * to a Map.
	 * 
	 * @param	email	Requestor's email, use as key for retrieving
	 * 				queue message(s)  
	 * @return Map		Returning a map that consists of two lists : 
	 * 				a) a list of request message(s)
	 * 				b) a list of reply message(s)			 
	 */
	public Map<String, Object> browseMessage(String email, JmsTemplate jmsTemplate, 
			Destination reqDestination, Destination repDestination) {
		logger.debug("in browseMessage...");
		logger.debug("email : " + email);
		logger.debug("jmsTemplate" + jmsTemplate);
		logger.debug("reqDestination : " + reqDestination);
		logger.debug("repDestination : " + repDestination);

		Map<String, Object> msgMap = new HashMap<String, Object>();
		NVCLDownloadQueueBrowser nvclDownloadQueueBrowser = new NVCLDownloadQueueBrowser();
		nvclDownloadQueueBrowser.setJmsTemplate(jmsTemplate);
		List<MessageVo> reqMsgList = (ArrayList<MessageVo>) nvclDownloadQueueBrowser.browseQueueMessages(email,reqDestination);
		List<MessageVo> repMsgList = (ArrayList<MessageVo>) nvclDownloadQueueBrowser.browseQueueMessages(email,repDestination);
		msgMap.put("request",reqMsgList);
		msgMap.put("reply",repMsgList);
		return msgMap;
	}

	
	private ConfigVo config;
	public void setConfig(ConfigVo config) {
			this.config = config;
	}
	
}
