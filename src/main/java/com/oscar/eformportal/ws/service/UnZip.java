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
package com.oscar.eformportal.ws.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.oscarehr.util.MiscUtils;

import oscar.OscarProperties;
import oscar.eform.EFormUtil;


public class UnZip {
	public static void decompression(String sourcefiles,EformInfo info) throws IOException{
	String decompreDirectory = OscarProperties.getInstance().getProperty("eformPortal_tmp_dir");
	ZipFile readfile = null;
	try {
		readfile =new ZipFile(sourcefiles);
		Enumeration takeentrie = readfile.entries();
		ZipEntry zipEntry = null;
		File credirectory = new File(decompreDirectory);
		credirectory.mkdirs();
		while (takeentrie.hasMoreElements()) {
			zipEntry = (ZipEntry) takeentrie.nextElement();
			String entryName = zipEntry.getName();
			String endName = entryName.substring(entryName.indexOf(".")+1,entryName.length());
			InputStream in = null;
			FileOutputStream out = null;
			try {
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					File  createDirectory = new File(decompreDirectory+ File.separator + name);
					createDirectory.mkdirs();
				} else {
					int index = entryName.lastIndexOf("\\");
					if (index != -1) {
						File createDirectory = new File(decompreDirectory+ File.separator+ entryName.substring(0, index));
						createDirectory.mkdirs();
					}
					index = entryName.lastIndexOf("/");
					if (index != -1) { 
						File createDirectory = new File(decompreDirectory + File.separator + entryName.substring(0, index));
						createDirectory.mkdirs();
					}
					if("png".equalsIgnoreCase(endName)||"jpg".equalsIgnoreCase(endName)||"css".equalsIgnoreCase(endName)){
						File unpackfile = new File(OscarProperties.getInstance().getProperty("eform_image") + File.separator + zipEntry.getName());
						out = new FileOutputStream(unpackfile);
					}
					if("html".equalsIgnoreCase(endName))
					{
			             EFormUtil.saveEForm(info.getName(), "", zipEntry.getName(), info.getContent(), false, "");

					}
					in = readfile.getInputStream(zipEntry);
					
					if(out!=null){
					int c;
					byte[] by = new byte[1024];
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.flush();
					}
				}
			} catch (IOException ex) {
				MiscUtils.getLogger().debug(ex.toString());
				throw new IOException("Decompression failure: "+ex.toString());
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException ex) {
						MiscUtils.getLogger().debug(ex.toString());
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException ex) {
					MiscUtils.getLogger().debug(ex.toString());
					}
				}
				in=null;
				out=null;
			}

		}
	} catch (IOException ex) {
		MiscUtils.getLogger().debug(ex.toString());
	} finally {
		if (readfile != null) {
			try {
				readfile.close();
			} catch (IOException ex) {
				MiscUtils.getLogger().debug(ex.toString());
				throw new IOException("Dcompression failure: "+ ex.toString());
			}
		}
	}
	}
}
