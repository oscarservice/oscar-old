package oscar.oscarDemographic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logger.info("in ImportDemoAction : execute");
		
		try
		{
			ImportDemoForm frm = (ImportDemoForm) form;
			logger.info("frm = "+frm);
			
			FormFile formFile = frm.getFile1();
			logger.info("formFile = "+formFile);
			
			//Thread.sleep(5000);
			SunnyBrookImportDemoUtil demoUtil = new SunnyBrookImportDemoUtil();
			File ioFile = getIOFile(formFile); 
			Map<String,Object> resultMap = demoUtil.importFile(ioFile);
			
			String statusMsg = "";
			if(resultMap!=null)
			{
				Demographic demographic = null;
				if(resultMap.get("DEMO")!=null)
					demographic = (Demographic) resultMap.get("DEMO");
				
				String patientName = getPatientName(demographic);
				if(resultMap.get("DEMO_EXISTS")!=null && resultMap.get("DEMO_EXISTS").toString().equalsIgnoreCase("true"))
				{
					statusMsg = "Patient '"+patientName+"' already exists";
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
