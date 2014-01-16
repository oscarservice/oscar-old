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

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IOscarService {
	
	@WebMethod
	public List<EformInfo> getEforms();
	
	@WebMethod
	public List<EformInfo> getEformsByName(String name);
	
	@WebMethod
	public List<EformInfo> getEformsByLocation(String location);
	
	@WebMethod
	public List<EformInfo> getEformsByCategory(String category);
	
	@WebMethod
	public List<EformInfo> getEformsByGroup(String group);
	
	@WebMethod
	public List<EformInfo> getEformsByKeyword(String keyword);
	
	@WebMethod
	public MyFile uploadFile(int fid) throws FileTransferException;
	
	@WebMethod
	public Image showImage(int fid,String imageName);
	
	@WebMethod
	public int getNewEform(Date time);
	
	@WebMethod
	public int getEformsNum();
}
