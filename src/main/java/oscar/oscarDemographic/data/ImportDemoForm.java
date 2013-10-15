package oscar.oscarDemographic.data;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ImportDemoForm extends ActionForm
{
	private FormFile file1;

	public FormFile getFile1()
	{
		return file1;
	}

	public void setFile1(FormFile file1)
	{
		this.file1 = file1;
	}
}
