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
	

}
