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

package oscar.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.oscarehr.common.model.Demographic;

import oscar.OscarProperties;

public class SunnyBrookDemoImportJob  extends TimerTask
{
	private static Logger logger = Logger.getLogger(SunnyBrookDemoImportJob.class);
	private static OscarProperties oscarProperties = OscarProperties.getInstance();
	
	private static final File SOURCE_DIR = new File(oscarProperties.getProperty("SUNNY_BROOK_DEMO_IMPORT_SOURCE_FOLDER"));
	private static final File SUCCESS_DIR = new File(oscarProperties.getProperty("SUNNY_BROOK_DEMO_IMPORT_SOURCE_FOLDER")+"/success");
	private static final File ERROR_DIR = new File(oscarProperties.getProperty("SUNNY_BROOK_DEMO_IMPORT_SOURCE_FOLDER")+"/error");
	
	@Override
	public void run()
	{
		logger.info("### sunny brook demo import job started ###");

		doJob();

		logger.info("### sunny brook demo import job finished ###");
	}

	public void doJob()
	{
		File[] txtFilesArr = getFilesTobeImported();
		logger.info("total no. of files to be imported : "+(txtFilesArr!=null?txtFilesArr.length:"0"));
		
		if(txtFilesArr!=null)
		{
			SunnyBrookImportDemoUtil importDemoUtil = new SunnyBrookImportDemoUtil();
			
			for (File file : txtFilesArr)
			{
				try
				{
					Map<String, Object> demoImportResultMap = importDemoUtil.importFile(file);
					logger.info("demoImportResultMap = "+demoImportResultMap);
					
					if(demoImportResultMap!=null)
					{
						//if demo exists
						if(demoImportResultMap.get("DEMO_EXISTS")!=null && demoImportResultMap.get("DEMO_EXISTS").toString().equalsIgnoreCase("true"))
						{
							//update demo
							Demographic demoFromFile = null;
							Demographic demoFromDB = null;
							
							if(demoImportResultMap.get("DEMO_TO_BE_IMPORTED")!=null && demoImportResultMap.get("DEMO")!=null)
							{
								demoFromFile = (Demographic) demoImportResultMap.get("DEMO_TO_BE_IMPORTED");
								demoFromDB = (Demographic) demoImportResultMap.get("DEMO");
								
								importDemoUtil.updateDemo(demoFromFile, demoFromDB);
								logger.info("demographic has been updated. demo no. : "+(demoFromDB!=null?demoFromDB.getDemographicNo():"null"));
							}
						}
						else
						{
							if(demoImportResultMap.get("DEMO")!=null)
							{
								Demographic insertedDemo = (Demographic) demoImportResultMap.get("DEMO");
								
								logger.info("demographic has been inserted. demo no. : "+(insertedDemo!=null?insertedDemo.getDemographicNo():"null"));
							}
						}
					}
					moveFile(SUCCESS_DIR, file);
				}
				catch (Exception e)
				{
					logger.error("error while importing file : "+(file!=null?file.getAbsolutePath():"null"), e);
					moveFile(ERROR_DIR, file);
				}
			}
		}
	}
	
	private void moveFile(File dir, File file)
	{
		if(file!=null && file.exists())
		{
			if(dir==null || !dir.exists())
				dir.mkdir();
			
			file.renameTo(new File(dir, file.getName()));
		}
	}
	
	private File[] getFilesTobeImported()
	{
		File[] fileArr = null;
		
		if(SOURCE_DIR!=null && SOURCE_DIR.exists() && SOURCE_DIR.isDirectory())
		{
			File[] fileArrTmp = SOURCE_DIR.listFiles();
			if(fileArrTmp!=null)
			{
				List<File> txtFileList = new ArrayList<File>();
				for (File file : fileArrTmp)
				{
					if(file!=null && file.isFile() && file.getName().endsWith(".txt"))
					{
						txtFileList.add(file);
					}
				}
				fileArr = txtFileList.toArray(new File[]{});
			}
		}
		
		return fileArr;
	}
}
