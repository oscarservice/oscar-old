/**
 * Copyright (c) 2001-2002. Department of Family Medicine, McMaster University. All Rights Reserved.
 * This software is published under the GPL GNU General Public License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version. 
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * This software was written for the
 * Department of Family Medicine
 * McMaster University
 * Hamilton
 * Ontario, Canada
 */


/*
 * Utilities.java
 *
 * Created on May 31, 2007, 2:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package oscar.oscarLab.ca.all.util;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jpedal.PdfDecoder;
import org.jpedal.fonts.FontMappings;
import org.oscarehr.util.MiscUtils;

import oscar.OscarProperties;

import com.sun.pdfview.PDFFile;

public class Utilities {
       
	private static final Logger logger=MiscUtils.getLogger();
	
    private Utilities() {
    	// utils shouldn't be instantiated
    }
    
    public static ArrayList<String> separateMessages(String fileName) throws Exception{

        ArrayList<String> messages = new ArrayList<String>();
        try{
            InputStream is = new FileInputStream(fileName);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            String line = null;
            boolean firstPIDflag = false; //true if the first PID segment has been processed false otherwise
            boolean firstMSHflag = false; //true if the first MSH segment has been processed false otherwise
            //String mshSeg = br.readLine();
            
            StringBuilder sb = new StringBuilder();
            String mshSeg = "";
            
            while ((line = br.readLine()) != null) {
                if (line.length() > 3){
                    if (line.substring(0, 3).equals("MSH")){
                        if (firstMSHflag){
                            messages.add(sb.toString());
                            sb.delete(0, sb.length());
                        }
                        mshSeg = line;
                        firstMSHflag = true;
                        firstPIDflag = false;
                    } else if (line.substring(0, 3).equals("PID")){
                        if (firstPIDflag){
                            messages.add(sb.toString());
                            sb.delete(0, sb.length());
                            sb.append(mshSeg + "\r\n");
                        }
                        firstPIDflag = true;
                    }
                    sb.append(line + "\r\n");
                }
            }
                       
            // add the last message
            messages.add(sb.toString());
            
            is.close();
            br.close();
        }catch(Exception e){
            throw e;
        }
        
        return(messages);
    }
    
     public static ArrayList<String> separateMessages(String fileName, String labType)
    throws Exception
  {
    ArrayList messages = new ArrayList();
    if (labType.equals("TDIS")) {
      try {
        InputStream is = new FileInputStream(fileName);

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;
        boolean firstPIDflag = false;
        boolean firstMSHflag = false;

        StringBuilder sb = new StringBuilder();
        String mshSeg = "";

        while ((line = br.readLine()) != null) {
          if (line.length() > 3) {
            if (line.substring(0, 3).equals("MSH")) {
              if (firstMSHflag) {
                messages.add(sb.toString());
                sb.delete(0, sb.length());
              }
              mshSeg = line;
              firstMSHflag = true;
              firstPIDflag = false;
            } else if (line.substring(0, 3).equals("PID")) {
              if (firstPIDflag) {
                messages.add(sb.toString());
                sb.delete(0, sb.length());
                sb.append(new StringBuilder().append(mshSeg).append("\r\n").toString());
              }
              firstPIDflag = true;
            }

            if (line.substring(0, 3).equals("OBX")) {
              String[] obxSegArray = line.split("\\|");
              if (obxSegArray.length > 2)
                sb.append(new StringBuilder().append(line).append("\r\n").toString());
            }
            else
            {
              sb.append(new StringBuilder().append(line).append("\r\n").toString());
            }

          }

        }

        messages.add(sb.toString());

        is.close();
        br.close();
      } catch (Exception e) {
        throw e;
      }

      return messages;
    }if (labType.equals("KMH"))
      try {
        InputStream is = new FileInputStream(fileName);

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;
        boolean firstPIDflag = false;
        boolean firstMSHflag = false;

        StringBuilder sb = new StringBuilder();
        String mshSeg = "";

        while ((line = br.readLine()) != null) {
          if (line.length() > 3) {
            if (line.substring(0, 3).equals("MSH")) {
              String[] mshSegArray = line.split("\\|");
              logger.info(new StringBuilder().append("MSH 8 = ").append(mshSegArray[8]).toString());
              if ((mshSegArray[8].equals("ORU^T02")) || (mshSegArray[8].equals("ORU^T04"))) {
                mshSegArray[8] = "ORU^R01";
                for (int a = 0; a < mshSegArray.length; a++) {
                  if (a != mshSegArray.length - 1)
                    sb.append(new StringBuilder().append(mshSegArray[a]).append("|").toString());
                  else {
                    sb.append(mshSegArray[a]);
                  }
                }
                sb.append("\r\n");
                logger.info(new StringBuilder().append("MSH segment = ").append(sb.toString()).toString());
              }
            }
            sb.append(new StringBuilder().append(line).append("\r\n").toString());
          }

        }

        messages.add(sb.toString());

        is.close();
        br.close();
      } catch (Exception e) {
        throw e;
      }
    else if (labType.equals("GAMXRAY"))
    {
      try
      {
        InputStream is = new FileInputStream(fileName);

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null)
        {
          sb.append(new StringBuilder().append(line).append("\r\n").toString());
        }

        String report = sb.toString();
        String[] reports = report.split("<ReportInfo>");

        for (int i = 1; i < reports.length; i++) {
          String message = new StringBuilder().append("<?xml version='1.0' encoding='utf-8'?>\r\n<ReportInfo>\n\r").append(reports[i].split("</ArrayOfReportInfo")[0]).toString();
          messages.add(message);
        }

      }
      catch (Exception e)
      {
        logger.error("Error separating GAMXRAY messages", e);
      }
    }

    return messages;
  } 
    /**
     *
     * Save a Jakarta FormFile to a preconfigured place.
     *
     * @param file
     * @return
     */
    public static String saveFile(InputStream stream,String filename ){
        String retVal = null;
        
        
        try {
            OscarProperties props = OscarProperties.getInstance();
            //properties must exist
            String place= props.getProperty("DOCUMENT_DIR");
            
            if(!place.endsWith("/"))
                place = new StringBuilder(place).insert(place.length(),"/").toString();
            retVal = place+"LabUpload."+filename.replaceAll(".enc", "")+"."+(new Date()).getTime();
            
            logger.debug("saveFile place="+place+", retVal="+retVal);
            //write the  file to the file specified
            OutputStream os = new FileOutputStream(retVal);
            
            int bytesRead = 0;
            while ((bytesRead = stream.read()) != -1){
                os.write(bytesRead);
            }
            os.close();
            
            //close the stream
            stream.close();
        }catch (FileNotFoundException fnfe) {
        	logger.error("Error", fnfe);
            return retVal;
            
        }catch (IOException ioe) {
        	logger.error("Error", ioe);
            return retVal;
        }
        return retVal;
    }    
    
    public static String saveHRMFile(InputStream stream,String filename ){
    	String retVal = null;
    	String place = OscarProperties.getInstance().getProperty("OMD_hrm");
    	
    	try {
    	   	if(!place.endsWith("/")){
    	   		place = new StringBuilder(place).insert(place.length(),"/").toString();
    	   	}
    	   	retVal = place+"KeyUpload."+filename+"."+(new Date()).getTime();
    	
    	   	//write the  file to the file specified
    	   	OutputStream os = new FileOutputStream(retVal);
    	
    	   	int bytesRead = 0;
    	   	while ((bytesRead = stream.read()) != -1){
    	   		os.write(bytesRead);
    	   	}
    	   	os.close();
    	
    	   	//close the stream
    	   	stream.close();
		}catch (FileNotFoundException fnfe) {
			logger.error("Error", fnfe);
			return retVal;
    	}catch (IOException ioe) {
    		logger.error("Error", ioe);
    		return retVal;
    	}
    		return retVal;
    	}    
    
    
    /*
     *  Return a string corresponding to the data in a given InputStream
     */
    public static String inputStreamAsString(InputStream stream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        
        stream.close();
        br.close();
        return sb.toString();
    }

  public static int createImagesForPdf(String docDir, String fileName)
  {
    File documentCacheDir = getDocumentCacheDir(docDir);
    String pdffileName = fileName;
    if (!fileName.endsWith(".pdf")) pdffileName = new StringBuilder().append(fileName).append(".pdf").toString();
    File file = new File(docDir, pdffileName);
    PdfDecoder decode_pdf = new PdfDecoder(true);
    File ofile = null;
    int numberOfPages = 0;
    try
    {
      FontMappings.setFontReplacements();

      decode_pdf.useHiResScreenDisplay(true);

      decode_pdf.setExtractionMode(0, 96, 1.333333F);

      FileInputStream is = new FileInputStream(file);

      decode_pdf.openPdfFileFromInputStream(is, false);

      RandomAccessFile raf = new RandomAccessFile(file, "r");
      FileChannel channel = raf.getChannel();
      ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
      PDFFile pdffile = new PDFFile(buf);
      numberOfPages = pdffile.getNumPages();
      for (int p = 1; p <= numberOfPages; p++) {
        BufferedImage image_to_save = decode_pdf.getPageAsImage(p);

        String imageFileName = null;
        if (fileName.endsWith(".pdf")) imageFileName = fileName.split(".pdf")[0]; else {
          imageFileName = fileName;
        }
        File outfile = new File(documentCacheDir, new StringBuilder().append(imageFileName).append("_").append(p).append(".png").toString());
        if (!outfile.exists()) {
          decode_pdf.getObjectStore().saveStoredImage(new StringBuilder().append(documentCacheDir.getCanonicalPath()).append("/").append(imageFileName).append("_").append(p).append(".png").toString(), image_to_save, true, false, "png");
        }
        decode_pdf.flushObjectValues(true);

        decode_pdf.closePdfFile();
      }
    }
    catch (Exception e)
    {
      logger.error(new StringBuilder().append("Error decoding pdf file ").append(fileName).toString());
      MiscUtils.getLogger().error("Error", e);
      decode_pdf.closePdfFile();
    }

    return numberOfPages;
  }

  public static File getDocumentCacheDir(String docdownload)
  {
    File docDir = new File(docdownload);
    String documentDirName = docDir.getName();
    File parentDir = docDir.getParentFile();

    File cacheDir = new File(parentDir, new StringBuilder().append(documentDirName).append("_cache").toString());

    if (!cacheDir.exists()) {
      cacheDir.mkdir();
    }
    return cacheDir;
  }
}
