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

/**
 * @author Rohit Prajapati (rohitprajapati54@gmail.com)
 */
package oscar.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.oscarehr.PMmodule.caisi_integrator.CaisiIntegratorManager;
import org.oscarehr.PMmodule.dao.ProgramProviderDAO;
import org.oscarehr.PMmodule.dao.ProviderDao;
import org.oscarehr.PMmodule.dao.SecUserRoleDao;
import org.oscarehr.PMmodule.model.ProgramAccess;
import org.oscarehr.PMmodule.model.ProgramProvider;
import org.oscarehr.PMmodule.model.SecUserRole;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.utility.ProgramAccessCache;
import org.oscarehr.PMmodule.utility.RoleCache;
import org.oscarehr.billing.CA.ON.dao.BillingClaimDAO;
import org.oscarehr.billing.CA.ON.model.BillingClaimHeader1;
import org.oscarehr.caisi_integrator.ws.CachedDemographicNote;
import org.oscarehr.caisi_integrator.ws.DemographicWs;
import org.oscarehr.casemgmt.dao.RoleProgramAccessDAO;
import org.oscarehr.casemgmt.model.CaseManagementNote;
import org.oscarehr.casemgmt.model.CaseManagementNoteExt;
import org.oscarehr.casemgmt.model.Issue;
import org.oscarehr.casemgmt.service.CaseManagementManager;
import org.oscarehr.casemgmt.service.CaseManagementPrintPdf;
import org.oscarehr.casemgmt.web.NoteDisplay;
import org.oscarehr.casemgmt.web.NoteDisplayLocal;
import org.oscarehr.casemgmt.web.NoteDisplayNonNote;
import org.oscarehr.common.dao.DemographicDao;
import org.oscarehr.common.dao.FacilityDao;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.common.model.Facility;
import org.oscarehr.util.LoggedInInfo;
import org.oscarehr.util.MiscUtils;
import org.oscarehr.util.SpringUtils;

import oscar.OscarProperties;
import oscar.eform.EFormUtil;
import oscar.oscarEncounter.data.EctFormData;
import oscar.oscarEncounter.data.EctFormData.PatientForm;
import oscar.oscarLab.ca.all.pageUtil.LabPDFCreator;
import oscar.oscarLab.ca.all.parsers.Factory;
import oscar.oscarLab.ca.all.parsers.MessageHandler;
import oscar.oscarLab.ca.on.CommonLabResultData;
import oscar.oscarLab.ca.on.LabResultData;

import com.quatro.model.security.Secrole;

public class EChartExportUtil
{
	private Logger logger = Logger.getLogger(EChartExportUtil.class);

	private CaseManagementManager caseManagementMgr = SpringUtils.getBean(CaseManagementManager.class);
	private DemographicDao demographicDao = (DemographicDao) SpringUtils.getBean("demographicDao");
	private ProgramProviderDAO programProviderDAO = SpringUtils.getBean(ProgramProviderDAO.class);
	private ProviderDao providerDao = SpringUtils.getBean(ProviderDao.class);
	private FacilityDao facilityDao = SpringUtils.getBean(FacilityDao.class);
	private ProgramManager programManager = SpringUtils.getBean(ProgramManager.class);
	private RoleProgramAccessDAO roleProgramAccessDAO = (RoleProgramAccessDAO) SpringUtils.getBean("RoleProgramAccessDAO");

	private BillingClaimDAO billingClaimDao = (BillingClaimDAO) SpringUtils.getBean("billingClaimDAO");
	private static final Integer MAX_INVOICES = 20;

	private static OscarProperties oscarProperties = OscarProperties.getInstance();

	/*public void exportEChart(String demographicNo) throws Exception
	{
		Demographic demographic = demographicDao.getDemographic(demographicNo);

		String demoName = demographic.getFirstName() + "_" + demographic.getLastName();

		String outputFileName = demographicNo + "_" + demoName + "_echart.pdf";

		File file = new File(ICFHTDemoExportUtil.EXPORTED_PDF_DEST_DIR, outputFileName);
		FileOutputStream fout = new FileOutputStream(file);
		exportEChart(demographic, fout);
	}*/

	public void exportEChart(Demographic demographic, OutputStream outStream) throws Exception
	{
		boolean printCPP = true;
		boolean printRx = true;
		boolean printLabs = true;

		String demono = demographic.getDemographicNo() + "";
		// get notes id

		String ids = getNotesIds(demono, demographic.getProviderNo());

		Map<String, String> demoDtlMap = new HashMap<String, String>();
		demoDtlMap.put("DEMO_ID", demographic.getDemographicNo()+"");
		demoDtlMap.put("demoName", demographic.getFirstName() + " " + demographic.getLastName());
		demoDtlMap.put("demoSex", demographic.getSex());
		demoDtlMap.put("demoAge", demographic.getAge());
		demoDtlMap.put("mrp", getMRP(demographic.getProviderNo()));
		String dob = demographic.getFormattedDob();
		dob = convertDateFmt(dob);
		demoDtlMap.put("demoDOB", dob);

		String providerNo = "";

		String[] noteIds;
		if (ids.length() > 0)
			noteIds = ids.split(",");
		else
			noteIds = (String[]) Array.newInstance(String.class, 0);

		List<CaseManagementNote> notes = new ArrayList<CaseManagementNote>();
		List<String> remoteNoteUUIDs = new ArrayList<String>();
		String uuid;
		for (int idx = 0; idx < noteIds.length; ++idx)
		{
			if (noteIds[idx].startsWith("UUID"))
			{
				uuid = noteIds[idx].substring(4);
				remoteNoteUUIDs.add(uuid);
			}
			else
			{
				notes.add(this.caseManagementMgr.getNote(noteIds[idx]));
			}
		}

		Facility currentFacility = null;
		List<Integer> facilityIds = providerDao.getFacilityIds(providerNo);
		if (facilityIds != null && facilityIds.size() > 0)
		{
			Integer firstFacilityId = facilityIds.get(0);
			currentFacility = facilityDao.find(firstFacilityId);
		}

		// LoggedInInfo loggedInInfo = LoggedInInfo.loggedInInfo.get();
		// if (loggedInInfo.currentFacility.isIntegratorEnabled() &&
		// remoteNoteUUIDs.size() > 0)
		if (currentFacility != null && currentFacility.isIntegratorEnabled() && remoteNoteUUIDs.size() > 0)
		{
			DemographicWs demographicWs = CaisiIntegratorManager.getDemographicWs();
			List<CachedDemographicNote> remoteNotes = demographicWs.getLinkedCachedDemographicNotes(ConversionUtils.fromIntString(demono));
			for (CachedDemographicNote remoteNote : remoteNotes)
			{
				for (String remoteUUID : remoteNoteUUIDs)
				{
					if (remoteUUID.equals(remoteNote.getCachedDemographicNoteCompositePk().getUuid()))
					{
						CaseManagementNote fakeNote = getFakedNote(remoteNote);
						notes.add(fakeNote);
						break;
					}
				}
			}
		}

		// we're not guaranteed any ordering of notes given to us, so sort by
		// observation date
		oscar.OscarProperties p = oscar.OscarProperties.getInstance();
		String noteSort = p.getProperty("CMESort", "");
		if (noteSort.trim().equalsIgnoreCase("UP"))
		{
			Collections.sort(notes, CaseManagementNote.noteObservationDateComparator);
			Collections.reverse(notes);
		}
		else
			Collections.sort(notes, CaseManagementNote.noteObservationDateComparator);

		List<CaseManagementNote> issueNotes;
		List<CaseManagementNote> tmpNotes;
		HashMap<String, List<CaseManagementNote>> cpp = null;
		// if (request.getParameter("printCPP").equalsIgnoreCase("true")) {
		if (printCPP)
		{
			cpp = new HashMap<String, List<CaseManagementNote>>();
			String[] issueCodes = { "OMeds", "SocHistory", "MedHistory", "Concerns", "Reminders", "FamHistory", "RiskFactors" };
			for (int j = 0; j < issueCodes.length; ++j)
			{
				List<Issue> issues = caseManagementMgr.getIssueInfoByCode(providerNo, issueCodes[j]);
				String[] issueIds = getIssueIds(issues);// = new
														// String[issues.size()];
				tmpNotes = caseManagementMgr.getNotes(demono, issueIds);
				issueNotes = new ArrayList<CaseManagementNote>();
				for (int k = 0; k < tmpNotes.size(); ++k)
				{
					if (!tmpNotes.get(k).isLocked())
					{
						List<CaseManagementNoteExt> exts = caseManagementMgr.getExtByNote(tmpNotes.get(k).getId());
						boolean exclude = false;
						for (CaseManagementNoteExt ext : exts)
						{
							if (ext.getKeyVal().equals("Hide Cpp"))
							{
								if (ext.getValue().equals("1"))
								{
									exclude = true;
								}
							}
						}
						if (!exclude)
						{
							issueNotes.add(tmpNotes.get(k));
						}
					}
				}
				cpp.put(issueCodes[j], issueNotes);
			}
		}
		String demoNo = null;
		List<CaseManagementNote> othermeds = null;
		if (printRx)
		{
			demoNo = demono;
			if (cpp == null)
			{
				List<Issue> issues = caseManagementMgr.getIssueInfoByCode(providerNo, "OMeds");
				String[] issueIds = getIssueIds(issues);// new
														// String[issues.size()];
				othermeds = caseManagementMgr.getNotes(demono, issueIds);
			}
			else
			{
				othermeds = cpp.get("OMeds");
			}
		}

		// Create new file to save form to
		String path = OscarProperties.getInstance().getProperty("DOCUMENT_DIR");
		String fileName = path + "EncounterForm-" + UtilDateUtilities.getToday("yyyy-MM-dd.hh.mm.ss") + ".pdf";
		FileOutputStream out = new FileOutputStream(fileName);

		CaseManagementPrintPdf printer = new CaseManagementPrintPdf(demoDtlMap, out);
		printer.printDocHeaderFooter();
		printer.printCPP(cpp);
		printer.printRx(demoNo, othermeds);
		printer.printAllergies(demoNo);
		printer.printNotes(notes);

		printer.finish();

		List<Object> pdfDocs = new ArrayList<Object>();
		pdfDocs.add(fileName);

		if (printLabs)
		{
			try
			{
				// get the labs which fall into the date range which are attached to
				// this patient
				CommonLabResultData comLab = new CommonLabResultData();
				ArrayList<LabResultData> labs = comLab.populateLabResultsData("", demono, "", "", "", "U");
				LinkedHashMap<String, LabResultData> accessionMap = new LinkedHashMap<String, LabResultData>();
				for (int i = 0; i < labs.size(); i++)
				{
					LabResultData result = labs.get(i);
					if (result.isHL7TEXT())
					{
						if (result.accessionNumber == null || result.accessionNumber.equals(""))
						{
							accessionMap.put("noAccessionNum" + i + result.labType, result);
						}
						else
						{
							if (!accessionMap.containsKey(result.accessionNumber + result.labType))
								accessionMap.put(result.accessionNumber + result.labType, result);
						}
					}
				}
				for (LabResultData result : accessionMap.values())
				{
					try
					{
						Date d = result.getDateObj();
						// TODO:filter out the ones which aren't in our date range if
						// there's a date range????
						String segmentId = result.segmentID;
						MessageHandler handler = Factory.getHandler(segmentId);
						String fileName2 = OscarProperties.getInstance().getProperty("DOCUMENT_DIR") + "//" + handler.getPatientName().replaceAll("\\s", "_") + "_" + handler.getMsgDate() + "_LabReport.pdf";
						OutputStream os2 = new FileOutputStream(fileName2);
						LabPDFCreator pdfCreator = new LabPDFCreator(os2, segmentId, LoggedInInfo.loggedInInfo.get().loggedInProvider.getProviderNo());
						pdfCreator.printPdf();
						pdfDocs.add(fileName2);
					}
					catch (Exception e)
					{
						logger.error("Error while printing lab result data - "+e.getMessage(), e);
					}
				}
			}
			catch (Exception e)
			{
				logger.error("Error while printing labs - "+e.getMessage(), e);
			}

		}
		ConcatPDF.concat(pdfDocs, outStream);
	}

	private CaseManagementNote getFakedNote(CachedDemographicNote remoteNote)
	{
		CaseManagementNote note = new CaseManagementNote();

		if (remoteNote.getObservationDate() != null)
			note.setObservation_date(remoteNote.getObservationDate().getTime());
		note.setNote(remoteNote.getNote());

		return (note);
	}

	public String[] getIssueIds(List<Issue> issues)
	{
		String[] issueIds = new String[issues.size()];
		int idx = 0;
		for (Issue i : issues)
		{
			issueIds[idx] = String.valueOf(i.getId());
			++idx;
		}
		return issueIds;
	}

	private String getMRP(String providerNo)
	{
		String name = "";
		oscar.oscarEncounter.data.EctProviderData.Provider prov = new oscar.oscarEncounter.data.EctProviderData().getProvider(providerNo);
		if(prov!=null)
		{
			name = prov.getFirstName() + " " + prov.getSurname();
		}

		return name;
	}

	protected String convertDateFmt(String strOldDate)
	{
		String strNewDate = new String();
		if (strOldDate != null && strOldDate.length() > 0)
		{
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			try
			{

				Date tempDate = fmt.parse(strOldDate);
				strNewDate = new SimpleDateFormat("dd-MMM-yyyy").format(tempDate);

			}
			catch (ParseException ex)
			{
				MiscUtils.getLogger().error("Error", ex);
			}
		}

		return strNewDate;
	}

	private String getNotesIds(String demographicNo, String providerNo)
	{
		String str = "";
		ArrayList<NoteDisplay> notesList = getCaseMgmntNotes(demographicNo, providerNo);
		if (notesList != null)
		{
			for (NoteDisplay noteDisplay : notesList)
			{
				if (noteDisplay!=null && noteDisplay instanceof NoteDisplayLocal)
				{
					if (str.length() == 0)
						str = noteDisplay.getNoteId() + "";
					else
						str = str + "," + noteDisplay.getNoteId();
				}
			}
		}
		return str;
	}

	private ArrayList<NoteDisplay> getCaseMgmntNotes(String demoNo, String providerNo)
	{
		List<CaseManagementNote> notes = caseManagementMgr.getNotes(demoNo);

		String programId = "";

		List<ProgramProvider> programProvList = programProviderDAO.getProgramProvidersByProvider(providerNo);
		if (programProvList != null && programProvList.size() > 0)
			programId = programProvList.get(0).getProgramId() + "";

		// filter notes based on role and program/provider mappings
		//notes = filterNotes(notes, providerNo, programId);

		/*
		 * // apply provider filter notes = applyProviderFilters(notes,
		 * caseForm.getFilter_providers());
		 * 
		 * // apply if we are filtering on role String[] roleId =
		 * caseForm.getFilter_roles(); if (roleId != null && roleId.length > 0)
		 * notes = applyRoleFilter(notes, roleId);
		 * 
		 * // apply if we are filtering on issues if (checkedIssues != null &&
		 * checkedIssues.length > 0) notes = applyIssueFilter(notes,
		 * checkedIssues);
		 */

		this.caseManagementMgr.getEditors(notes);

		ArrayList<NoteDisplay> notesToDisplay = new ArrayList<NoteDisplay>();
		for (CaseManagementNote noteTemp : notes)
		{
			notesToDisplay.add(new NoteDisplayLocal(noteTemp));
		}

		// addRemoteNotes(notesToDisplay, demoNo, null, programId);
		// addGroupNotes(notesToDisplay, demoNo, null);

		SecUserRoleDao secUserRoleDao = SpringUtils.getBean(SecUserRoleDao.class);
		List<SecUserRole> userRolesList = secUserRoleDao.getUserRoles(providerNo);
		String userRolesStr = "";
		if (userRolesList != null && userRolesList.size() > 0)
		{
			for (SecUserRole secUserRole : userRolesList)
			{
				if (secUserRole!=null)
				{
					if (userRolesStr.length() == 0)
						userRolesStr = secUserRole.getRoleName();
					else
						userRolesStr = userRolesStr + "," + secUserRole.getRoleName();
				}
			}
		}
		// add eforms to notes list as single line items
		String roleName = (String) userRolesStr + "," + demoNo;
		ArrayList<HashMap<String, ? extends Object>> eForms = EFormUtil.listPatientEForms(EFormUtil.DATE, EFormUtil.CURRENT, demoNo, roleName);

		// add forms to notes list as single line items
		ArrayList<PatientForm> allPatientForms = EctFormData.getGroupedPatientFormsFromAllTables(Integer.valueOf(demoNo));

		for (HashMap<String, ? extends Object> eform : eForms)
		{
			notesToDisplay.add(new NoteDisplayNonNote(eform));
		}

		// add forms to notes list as single line items
		// ArrayList<PatientForm>
		// allPatientForms=EctFormData.getGroupedPatientFormsFromAllTables(demographicId);
		for (PatientForm patientForm : allPatientForms)
		{
			notesToDisplay.add(new NoteDisplayNonNote(patientForm));
		}

		if (oscar.OscarProperties.getInstance().getProperty("billregion", "").equalsIgnoreCase("ON"))
		{
			fetchInvoices(notesToDisplay, demoNo);
		}

		// sort the notes
		String noteSort = oscar.OscarProperties.getInstance().getProperty("CMESort", "");
		if (noteSort.trim().equalsIgnoreCase("UP"))
			notesToDisplay = sortNotes(notesToDisplay, "observation_date_asc");
		else
			notesToDisplay = sortNotes(notesToDisplay, "observation_date_desc");

		return notesToDisplay;
	}

	public List<CaseManagementNote> filterNotes(Collection<CaseManagementNote> notes, String providerNo, String programId)
	{

		List<CaseManagementNote> filteredNotes = new ArrayList<CaseManagementNote>();

		if (notes.isEmpty())
		{
			return filteredNotes;
		}

		// Get Role - if no ProgramProvider record found, show no issues.
		@SuppressWarnings("unchecked")
		List ppList = programProviderDAO.getProgramProviderByProviderProgramId(providerNo, new Long(programId));
		if (ppList == null || ppList.isEmpty())
		{
			return new ArrayList<CaseManagementNote>();
		}

		ProgramProvider pp = (ProgramProvider) ppList.get(0);
		Secrole role = pp.getRole();

		Map programAccessMap = ProgramAccessCache.getAccessMap(new Long(programId));

		// iterate through the issue list
		for (CaseManagementNote cmNote : notes)
		{
			String noteRole = cmNote.getReporter_caisi_role();
			String noteRoleName = RoleCache.getRole(Long.valueOf(noteRole)).getName().toLowerCase();
			ProgramAccess pa = null;
			boolean add = false;

			// write
			pa = null;
			// read
			pa = (ProgramAccess) programAccessMap.get("read " + noteRoleName + " notes");
			if (pa != null)
			{
				if (pa.isAllRoles() || isRoleIncludedInAccess(pa, role))
				{
					// filteredIssues.add(cmIssue);
					add = true;
				}
			}
			else
			{
				logger.info(noteRoleName + " is null");
				if (Long.parseLong(noteRole) == role.getId().longValue())
				{
					// default
					logger.info("noteRole " + noteRole + " = Provider Role from secRole " + role.getId());
					add = true;
				}
			}

			// apply defaults
			if (!add)
			{
				if (Long.parseLong(noteRole) == role.getId().longValue())
				{
					logger.info("noteRole " + noteRole + " = Provider Role from secRole " + role.getId());
					add = true;
				}
			}

			// global default role access
			String accessName = "read " + noteRoleName + " notes";
			if (roleProgramAccessDAO.hasAccess(accessName, role.getId()))
			{
				add = true;
			}

			// did it pass the test?
			if (add)
			{
				filteredNotes.add(cmNote);
			}
		}

		// filter notes based on facility
		if (OscarProperties.getInstance().getBooleanProperty("FILTER_ON_FACILITY", "true"))
		{
			filteredNotes = notesFacilityFiltering(filteredNotes);
		}

		return filteredNotes;
	}

	private List<CaseManagementNote> notesFacilityFiltering(List<CaseManagementNote> notes)
	{

		ArrayList<CaseManagementNote> results = new ArrayList<CaseManagementNote>();

		for (CaseManagementNote caseManagementNote : notes)
		{
			String programId = caseManagementNote.getProgram_no();

			if (programId == null || "".equals(programId))
			{
				results.add(caseManagementNote);
			}
			else
			{
				if (programManager.hasAccessBasedOnCurrentFacility(Integer.parseInt(programId)))
					results.add(caseManagementNote);
			}
		}

		return results;
	}

	public boolean isRoleIncludedInAccess(ProgramAccess pa, Secrole role)
	{
		boolean result = false;

		for (Iterator iter = pa.getRoles().iterator(); iter.hasNext();)
		{
			Secrole accessRole = (Secrole) iter.next();
			if (role.getId().longValue() == accessRole.getId().longValue())
			{
				return true;
			}
		}
		return result;
	}

	private void fetchInvoices(ArrayList<NoteDisplay> notes, String demographic_no)
	{
		List<BillingClaimHeader1> bills = billingClaimDao.getInvoices(demographic_no, MAX_INVOICES);

		for (BillingClaimHeader1 h1 : bills)
		{
			notes.add(new NoteDisplayNonNote(h1));
		}
	}

	private ArrayList<NoteDisplay> sortNotes(ArrayList<NoteDisplay> notes, String field)
	{
		logger.info("Sorting notes by field: " + field);

		if (field == null || field.equals("") || field.equals("update_date"))
		{
			return notes;
		}

		if (field.equals("providerName"))
		{
			Collections.sort(notes, NoteDisplay.noteProviderComparator);
		}
		if (field.equals("programName"))
		{
			Collections.sort(notes, NoteDisplay.noteRoleComparator);
		}
		if (field.equals("observation_date_asc"))
		{
			Collections.sort(notes, NoteDisplay.noteObservationDateComparator);
			Collections.reverse(notes);
		}
		if (field.equals("observation_date_desc"))
		{
			Collections.sort(notes, NoteDisplay.noteObservationDateComparator);
		}

		return notes;
	}
}
