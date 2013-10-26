package oscar.oscarDemographic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.oscarehr.common.model.Demographic;

import oscar.oscarDemographic.data.ImportDemoForm;
import oscar.util.SunnyBrookImportDemoUtil;

public class ImportDemoAction extends Action
{
	private Logger logger = Logger.getLogger(ImportDemoAction.class);
	
	private static Map<String, Map<String, Object>> demoTobeUpdated = new HashMap<String, Map<String,Object>>();
	private static int demoUpdatecnt = 1;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logger.info("in ImportDemoAction : execute");
		
		SunnyBrookImportDemoUtil demoUtil = new SunnyBrookImportDemoUtil();
		
		try
		{
			String actionVal = request.getParameter("actionVal");
			Map<String,Object> resultMap = null;
			
			if(actionVal!=null && actionVal.equalsIgnoreCase("update"))
			{
				String key = request.getParameter("DEMO_UPDATE_KEY");
				resultMap = demoTobeUpdated.get(key);
			}
			else if(actionVal!=null && actionVal.equalsIgnoreCase("cancel_update"))
			{
				String key = request.getParameter("DEMO_UPDATE_KEY");
				demoTobeUpdated.remove(key);
				
				return mapping.findForward("import");
			}
			else
			{
				ImportDemoForm frm = (ImportDemoForm) form;
				logger.info("frm = "+frm);
				
				FormFile formFile = frm.getFile1();
				logger.info("formFile = "+formFile);
				
				File ioFile = getIOFile(formFile); 
				resultMap = demoUtil.importFile(ioFile);
			}
			
			String statusMsg = "";
			if(resultMap!=null)
			{
				Demographic demoFromDB = null;
				if(resultMap.get("DEMO")!=null)
					demoFromDB = (Demographic) resultMap.get("DEMO");
				
				String patientName = getPatientName(demoFromDB);
				if(resultMap.get("DEMO_EXISTS")!=null && resultMap.get("DEMO_EXISTS").toString().equalsIgnoreCase("true"))
				{
					Demographic demoFromFile = (Demographic) resultMap.get("DEMO_TO_BE_IMPORTED");
					patientName = getPatientName(demoFromFile);
					
					if(actionVal!=null && actionVal.equalsIgnoreCase("update"))
					{
						//update
						if(demoFromFile!=null)
						{
							demoUtil.updateDemo(demoFromFile, demoFromDB);
							statusMsg = "Patient '"+patientName+"' has been updated successfully";
							
							request.setAttribute("status", "DEMO_UPDATED");
						}
					}
					else
					{
						//prompt to update
						String key = "DEMO_UPDATE_KEY_"+demoUpdatecnt++;
						
						request.setAttribute("status", "DEMO_EXISTS");
						request.setAttribute("DEMO_UPDATE_KEY", key);
						
						statusMsg = "Patient '"+patientName+"' already exists";
						demoTobeUpdated.put(key, resultMap);						
					}
				}
				else
				{
					statusMsg = "Patient '"+patientName+"' has been imported successfully";
				}
			}
			
			request.setAttribute("status_msg", statusMsg);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			request.setAttribute("status", "error");
			//request.setAttribute("status_msg", "Error while importing patient. "+e.getMessage());
			request.setAttribute("status_msg", "Error while importing patient.");
			
			
		}
		
		return mapping.findForward("success");
	}
	
	private String getPatientName(Demographic demographic)
	{
		String name = "";
		
		if(demographic!=null)
		{
			if(demographic.getLastName()!=null)
				name = demographic.getLastName();
			if(demographic.getFirstName()!=null)
			{
				if(name.trim().length()>0)
					name = name+", "+demographic.getFirstName();
				else
					name = demographic.getFirstName();
			}
		}
		
		return name;
	}
	
	private File getIOFile(FormFile formFile) throws IOException
	{
		File file = null;
		
		if(formFile!=null)
		{
			file = File.createTempFile("import_demo", null);
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(formFile.getFileData());
			fout.close();
		}
		
		return file;
	}
}
