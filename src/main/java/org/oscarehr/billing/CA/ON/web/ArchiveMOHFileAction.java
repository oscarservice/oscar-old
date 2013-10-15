package org.oscarehr.billing.CA.ON.web;

import java.io.File;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.oscarehr.billing.CA.ON.util.EDTFolder;

public class ArchiveMOHFileAction extends Action {

	private static final Logger log = Logger.getLogger(ArchiveMOHFileAction.class);

	@SuppressWarnings("deprecation")
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse servletResponse) {
		String[] mohFiles = request.getParameterValues("mohFile");
		if (mohFiles == null) { 
			log.debug("No files provided.");
			return actionMapping.findForward("showInbox");
		}
		File archivePath = new File(EDTFolder.ARCHIVE.getPath());
		File inboxPath = new File(EDTFolder.INBOX.getPath());
		File newFile, oldFile;
		if (!archivePath.exists()) {
			archivePath.mkdir();
		}
		for (String mohFile : mohFiles) {
			mohFile = URLDecoder.decode(mohFile);
			oldFile = new File(inboxPath, mohFile);
			newFile = new File(archivePath, mohFile);
			if (newFile.exists()) {
				String modifier = "(0)";
				Pattern p = Pattern.compile(".*(\\(\\d+\\))$");
			    Matcher m = p.matcher(mohFile);
			    if (m.find()) { modifier = m.group(1); }
				while (newFile.exists()) {
					modifier = "(" + (Integer.parseInt(modifier.substring(1).substring(0, modifier.length() - 2)) + 1) + ")";
					newFile = new File(archivePath, mohFile + modifier);
				}
			}
			boolean success = oldFile.renameTo(newFile);
			if (!success) { log.error("Unable to move " + newFile.getName()); }
		}
		return actionMapping.findForward("showInbox");
	}
}