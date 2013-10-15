package oscar.oscarLab.ca.all.upload.handlers;

import java.util.ArrayList;
import org.apache.log4j.Logger;

import oscar.oscarLab.ca.all.upload.MessageUploader;
import oscar.oscarLab.ca.all.util.Utilities;
import oscar.oscarLab.ca.on.LabResultData;


public class AlphaHL7Handler implements MessageHandler {

//	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(AlphaHL7Handler.class);
	
	@Override
	public String parse(String serviceName, String fileName, int fileId) {
		try {
			ArrayList<String> messages = Utilities.separateMessages(fileName);
			for(String msg:messages) {
				 MessageUploader.routeReport(serviceName, LabResultData.ALPHAHL7, msg, fileId);
			}
		} catch (Exception e) {
			MessageUploader.clean(fileId);
			logger.error("Could not upload message: ", e);
			return null;
		}
		return ("success");
	}
	
	public static void main(String[] args){
		new AlphaHL7Handler().parse("", "/home/tiger/Alpha_HL7/00029423.dat", 0);
	}

}
