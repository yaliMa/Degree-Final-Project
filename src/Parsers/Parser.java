package Parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @author Yael Mathov
 *
 */
public class Parser {
	
	private static final String REFSEQ_URL_TOKEN = "http://www.ncbi.nlm.nih.gov/nuccore/";
	
	private static final String REF_SEQ_FILE_NAME_PREFIX = "./proteinsFiles/refSeq/";
	private static final String REF_SEQ_FILE_NAME_POSTFIX = "_RefSeq.txt";

	private static final String NUCCORE_FILE_NAME_PREFIX = "./proteinsFiles/nuccore/";
	private static final String NUCCORE_FILE_NAME_POSTFIX = "_NuccoreText.txt";
	
	private static final String NUCCORE_URL_PREFIX = "http://www.ncbi.nlm.nih.gov/sviewer/viewer.fcgi?val=";
	private static final String NUCCORE_URL_POSTFIX = "&db=nuccore&dopt=genbank&extrafeat=976&fmt_mask=0&retmode=html&withmarkup=on&log$=seqview&maxplex=3&maxdownloadsize=1000000";
	

	/*************************** Public Methods *************************/
	/**
	 * Reads the source code from the gene's Refseq web site and save it in 
	 * a file (path: ./proteinsFiles/refSeq/<_proteinIndex>__RefSeq.txt).
	 * This test doesn't includes the information about the exons, but it contains 
	 * part of the URL to the Refseq web site which contains this information (I call 
	 * this sub-URL "nuccore").
	 *
	 * @param _proteinIndex
	 * @param _refSeqIndex
	 * @return
	 */
	public static String readRefseqExonInfoToFile(String _proteinIndex, String _refSeqIndex)
	{
		String 	nuccoreURL;
		String 	nuccoreFileName;
		String 	refSeqFileName;
		
		// Construct the file name to the RefSeq source code
		refSeqFileName = REF_SEQ_FILE_NAME_PREFIX + _proteinIndex + REF_SEQ_FILE_NAME_POSTFIX;
		
		// Getting the RefSeq from the web
		if(getTextFromURL((REFSEQ_URL_TOKEN + _refSeqIndex), refSeqFileName) != 0)
		{
			System.out.println("ERROR: [ ExonParser ] - Couldn't get refseq source code");
			return null;
		}
		
		// searching the nuccore link in the RefSeq source code
		nuccoreURL = getNuccore(refSeqFileName);
		if(nuccoreURL != null)
		{
			// Construct the file name to the nuccore source code
			nuccoreFileName = NUCCORE_FILE_NAME_PREFIX + _proteinIndex + NUCCORE_FILE_NAME_POSTFIX;
			
			// Getting the RefSeq from the web
			if(getTextFromURL(nuccoreURL, nuccoreFileName) == 0){
				return nuccoreFileName;
			}
		}
		else{
			System.out.println("ERROR: [ ExonParser ] - Couldn't get nuccore URL");
		}
		return null;
	}
	
	//------------------------------------------------------------------//

	/**
	 * Reads the source code from the web site in the _URL link and save it to 
	 * the file in the path: _FileNamePath
	 */
	public static int getTextFromURL(String _URL, String _FileNamePath)
	{
		/* Create URL object from the input */
		URL link = null;
		try {
			link = new URL(_URL);	
		} catch (MalformedURLException e) {
			System.out.println("ERROR: [ getTextFromURL ] - Can't create URL");
			return -1;
		}

		/* Create buffer reader and open stream to the web site */
		BufferedReader refSeqReader = null;
		try {
			refSeqReader = new BufferedReader(new InputStreamReader(link.openStream()));
		} catch (IOException e) {
			System.out.println("ERROR: [ getTextFromURL ] - Can't open stream. \nLink: " + _URL);
			return -2;
		}

		/* Reads the text from the Buffer and save in a string. */
		PrintWriter writer = null;

		try {
			writer = new PrintWriter(_FileNamePath, "UTF-8");
		} catch (FileNotFoundException e2) {
			System.out.println("ERROR: [ getTextFromURL ] - Can't Create file - File not found");
			return -3;
		} catch (UnsupportedEncodingException e2) {
			System.out.println("ERROR: [ getTextFromURL ] - Can't Create file - Unsupported encoding");
			return -3;
		}

		String temp;
		try {
			while ((temp = refSeqReader.readLine()) != null)
			{
				writer.println(temp);
			}
		} catch (IOException e1) {
			System.out.println("ERROR: [ getTextFromURL ] - Failed to read from stream");
			return -4;
		}

		/*Close Stream*/
		try {
			writer.close();
			refSeqReader.close();
		} catch (IOException e) {
			System.out.println("ERROR: [ getTextFromURL ] - Can't close stream");
			return -5;
		}
		
		return 0;
	}
	
	
	//------------------------------------------------------------------//

	/**
	 * Get a file name and return the file's text.
	 * 
	 * @param _fileName 
	 * @return String that contain the text from the file
	 */
	public static String readFromFile(String _fileName)
	{
		BufferedReader buffRead = null;
		StringBuilder fileText = new StringBuilder();
		 
		try {
 			String currentLine;
 			buffRead = new BufferedReader(new FileReader(_fileName));
 
			while ((currentLine = buffRead.readLine()) != null) 
			{
				fileText.append(currentLine);
			}
		} 
		catch (IOException e) {
			System.out.println("ERROR: [ readFromFile ] - Can't Read from file :" + _fileName);
			return null;
		} 
		finally {
			try {
				if (buffRead != null)
				{
					buffRead.close();
				}
			} 
			catch (IOException ex) {
				System.out.println("ERROR: [ readFromFile ] - Can't close stream: " + _fileName);
				return null;
			}
		}
		return fileText.toString();
	}
	
	/************************** Private Methods *************************/
	
	/**
	 * Since the information about the exons in the Refseq web site is hidden I can't get
	 * the information I need from the Refseq source code. Luckily, I found out that the
	 * Refseq's source code contains a link to the page that shows the information about the exons.
	 * I could find the infix to the above link by searching "/nuccore" in the Refseq's source
	 * code. In this method I'm extracting the infix of the "nuccore" URL and returning the full
	 * URL as an output.
	 */
	private static String getNuccore(String _refSeqFileName)
	{
		String refSeqText = readFromFile(_refSeqFileName);
		
		if(refSeqText != null)
		{
			String[] splitResult = refSeqText.split("/nuccore");

			for(int i = 0 ; i < splitResult.length ; i++)
			{
				splitResult[i] = splitResult[i].substring(1, 10);
			}
			if(splitResult.length > 2)
			{
				return (NUCCORE_URL_PREFIX + splitResult[splitResult.length - 2] + NUCCORE_URL_POSTFIX);
			}
		}
		return null;
	}
	

}
