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


package org.oscarehr.measurements.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.common.dao.OscarAppointmentDao;
import org.oscarehr.common.model.Appointment;
import org.oscarehr.util.LoggedInInfo;
import org.oscarehr.util.MiscUtils;
import org.oscarehr.util.SpringUtils;

import oscar.OscarProperties;

import oscar.oscarEncounter.oscarMeasurements.dao.MeasurementsDao;
import oscar.oscarEncounter.oscarMeasurements.model.Measurements;

public class MeasurementDataAction extends DispatchAction {

	private static Logger logger = MiscUtils.getLogger();
	private static MeasurementsDao measurementsDao = (MeasurementsDao) SpringUtils.getBean("measurementsDao");
	OscarAppointmentDao appointmentDao = (OscarAppointmentDao)SpringUtils.getBean("oscarAppointmentDao");

	public ActionForward getLatestValues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String demographicNo = request.getParameter("demographicNo");
		String typeStr = request.getParameter("types");
		String appointmentNo = request.getParameter("appointmentNo");
		int apptNo = 0;
		if(appointmentNo != null && appointmentNo.length()>0 && !appointmentNo.equalsIgnoreCase("null")) {
			apptNo = Integer.parseInt(appointmentNo);
		}

		int prevApptNo = 0;
		if(apptNo > 0) {
			List<Appointment> appts = appointmentDao.getAppointmentHistory(Integer.parseInt(demographicNo));
			if(appts.size() > 1) {
				for(int x=0;x<appts.size();x++) {
					Appointment appt = appts.get(x);
					if(appt.getId().intValue() == apptNo && x < appts.size()-1) {
						prevApptNo = appts.get(x+1).getId();
					}
				}
			}
		}


		String fresh =request.getParameter("fresh");
		HashMap<String,Boolean> freshMap = new HashMap<String,Boolean>();
		if(fresh!=null) {
			String tmp[] = fresh.split(",");
			for(int x=0;x<tmp.length;x++) {
				freshMap.put(tmp[x],true);
			}
		}
		if(typeStr == null || typeStr.length() == 0) {
			//error
		}
		String[] types = typeStr.split(",");

		Map<String,Measurements> measurementMap = measurementsDao.getMeasurements(demographicNo,types);

		Date nctTs = null;
		Date applanationTs=null;

		StringBuilder script = new StringBuilder();
		for(String key:measurementMap.keySet()) {
			Measurements value = measurementMap.get(key);
			if((freshMap.get(key)==null) ||(freshMap.get(key) != null && value.getAppointmentNo() == Integer.parseInt(appointmentNo))) {
				script.append("jQuery(\"[measurement='"+key+"']\").val(\""+value.getDataField().replace("\n", "\\n").replace("\"","\\\"").replace("\r","")+"\").attr({itemtime: \"" + value.getDateEntered().getTime() + "\", appointment_no: \"" + value.getAppointmentNo() + "\"});\n");
				if(apptNo>0 && apptNo == value.getAppointmentNo()) {
					script.append("jQuery(\"[measurement='"+key+"']\").addClass('examfieldwhite');\n");
				}
				if(prevApptNo>0 && value.getAppointmentNo() == prevApptNo) {
					script.append("jQuery(\"[measurement='"+key+"']\").attr('prev_appt','true');\n");
				}
				if(apptNo>0 && value.getAppointmentNo() == apptNo) {
					script.append("jQuery(\"[measurement='"+key+"']\").attr('current_appt','true');\n");
				}
				if(key.equals("os_iop_applanation") || key.equals("od_iop_applanation")) {
					if(applanationTs == null) {
						applanationTs = value.getDateObserved();
					} else if(value.getDateObserved().after(applanationTs)) {
						applanationTs = value.getDateObserved();
					}
				}
				if(key.equals("os_iop_nct") || key.equals("od_iop_nct")) {
					if(nctTs == null) {
						nctTs = value.getDateObserved();
					} else if(value.getDateObserved().after(nctTs)) {
						nctTs = value.getDateObserved();
					}
				}
				oscar.OscarProperties props1 = oscar.OscarProperties.getInstance();
			    String eyeform = props1.getProperty("cme_js");
				if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
					if(key.equals("iop_ra") || key.equals("iop_la")) {
						if(applanationTs == null) {
							applanationTs = value.getDateObserved();
						} else if(value.getDateObserved().after(applanationTs)) {
							applanationTs = value.getDateObserved();
						}
					}
					if(key.equals("iop_rn") || key.equals("iop_ln")) {
						if(nctTs == null) {
							nctTs = value.getDateObserved();
						} else if(value.getDateObserved().after(nctTs)) {
							nctTs = value.getDateObserved();
						}
					}
				}
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(applanationTs!=null)
			script.append("jQuery(\"#applanation_ts\").html('"+sdf.format(applanationTs)+"');\n");
		if(nctTs != null)
			script.append("jQuery(\"#nct_ts\").html('"+sdf.format(nctTs)+"');\n");
		
		
		/*oscar.OscarProperties props1 = oscar.OscarProperties.getInstance();
	    String eyeform = props1.getProperty("cme_js");
		MeasurementsDao measurementsDao = (MeasurementsDao) SpringUtils.getBean("measurementsDao");
		List<Measurements> measurementList = measurementsDao.getMeasurements(Integer.parseInt(demographicNo));
		boolean in_v3 = false;
		String v2_type[] = {"od_ar_sph","od_ar_cyl","od_ar_axis","os_ar_sph","os_ar_cyl","os_ar_axis","od_k1","od_k2","od_k2_axis","os_k1","os_k2","os_k2_axis","od_sc_distance","od_cc_distance","od_ph_distance","os_sc_distance","os_cc_distance","os_ph_distance","od_sc_near","od_cc_near","os_sc_near","os_cc_near",
		"od_manifest_refraction_sph","od_manifest_refraction_cyl","od_manifest_refraction_axis","od_manifest_distance","os_manifest_refraction_sph","os_manifest_refraction_cyl","os_manifest_refraction_axis","os_manifest_distance","od_cycloplegic_refraction_sph","od_cycloplegic_refraction_cyl","od_cycloplegic_refraction_axis","od_cycloplegic_distance","os_cycloplegic_refraction_sph","os_cycloplegic_refraction_cyl","os_cycloplegic_refraction_axis","os_cycloplegic_distance","od_iop_nct","os_iop_nct","od_iop_applanation","os_iop_applanation","od_cct","os_cct","od_color_vision","od_pupil","od_amsler_grid","od_pam","od_confrontation","os_color_vision","os_pupil","os_amsler_grid","os_pam","os_confrontation",
		"EOM","od_cornea","od_conjuctiva_sclera","od_anterior_chamber","od_angle_up","od_angle_middle0","od_angle_middle1","od_angle_middle2","od_angle_down","od_iris","od_lens","os_cornea","os_conjuctiva_sclera","os_anterior_chamber","os_angle_up","os_angle_middle0","os_angle_middle1","os_angle_middle2","os_angle_down","os_iris","os_lens","od_disc","od_cd_ratio_horizontal","od_macula","od_retina","od_vitreous","os_disc","os_cd_ratio_horizontal","os_macula","os_retina","os_vitreous","od_face","od_upper_lid","od_lower_lid","od_punctum","od_lacrimal_lake","os_face","os_upper_lid","os_lower_lid","os_punctum","os_lacrimal_lake","od_lacrimal_irrigation","od_nld","od_dye_disappearance","os_lacrimal_irrigation","os_nld","os_dye_disappearance",
		"od_mrd","od_levator_function","od_inferior_scleral_show","od_cn_vii","od_blink","od_bells","od_lagophthalmos","os_mrd","os_levator_function","os_inferior_scleral_show","os_cn_vii","os_blink","os_bells","os_lagophthalmos","od_hertel","od_retropulsion","os_hertel","os_retropulsion"};
		String v3_type[] = {"v_rs","v_rc","v_rx","v_ls","v_lc","v_lx","v_rk1","v_rk2","v_rkx","v_lk1","v_lk2","v_lkx","v_rdsc","v_rdcc","v_rph","v_ldsc","v_ldcc","v_lph","v_rnsc","v_rncc","v_lnsc","v_lncc",
		"v_rds","v_rdc","v_rdx","v_rdv","v_lds","v_ldc","v_ldx","v_ldv","v_rcs","v_rcc","v_rcx","v_rcv","v_lcs","v_lcc","v_lcx","v_lcv","iop_rn","iop_ln","iop_ra","iop_la","cct_r","cct_l","o_rcolour","o_rpupil","o_ramsler","o_rpam","o_rconf","o_lcolour","o_lpupil","o_lamsler","o_lpam","o_lconf",
		"v_stereo","a_rk","a_rconj","a_rac","a_rangle_1","a_rangle_2","a_rangle_3","a_rangle_4","a_rangle_5","a_riris","a_rlens","a_lk","a_lconj","a_lac","a_langle_1","a_langle_2","a_langle_3","a_langle_4","a_langle_5","a_liris","a_llens","p_rdisc","p_rcd","p_rmac","p_rret","p_rvit","p_ldisc","p_lcd","p_lmac","p_lret","p_lvit","ext_rface","ext_rul","ext_rll","ext_rpunc","ext_rlake","ext_lface","ext_lul","ext_lll","ext_lpunc","ext_llake","ext_rirrig","ext_rnld","ext_rdye","ext_lirrig","ext_lnld","ext_ldye",
		"lid_rmrd","lid_rlev","lid_riss","lid_rcn7","lid_rblink","lid_rbell","lid_rlag","lid_lmrd","lid_llev","lid_liss","lid_lcn7","lid_lblink","lid_lbell","lid_llag","ext_rhertel","ext_rretro","ext_lhertel","ext_lretro"};
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			for(int i = 0;i < v2_type.length ; i ++ ){
				for(Measurements key1:measurementList) {
					if(v2_type[i].equals(key1.getType())){
						in_v3 = false;
						for(Measurements key2:measurementList) {							
							if(v3_type[i].equals(key2.getType())){
								Date d1 = null;
								Date d2 = null;
								d1 = key1.getDateObserved();
								d2 = key2.getDateObserved();
								if(d1.after(d2)){
									script.append("jQuery(\"[measurement='"+v3_type[i]+"']\").val(\""+key1.getDataField().replace("\n", "\\n").replace("\"","\\\"").replace("\r","")+"\").attr({itemtime: \"" + key1.getDateEntered().getTime() + "\", appointment_no: \"" + key1.getAppointmentNo() + "\"});\n");
									if(v3_type[i].equals("iop_rn") || v3_type[i].equals("iop_ln")){
										if(nctTs == null) {
											nctTs = key1.getDateObserved();
										}else if(key1.getDateObserved().after(nctTs)){
											nctTs = key1.getDateObserved();
										}
										script.append("jQuery(\"#nct_ts\").html('"+sdf.format(nctTs)+"');\n");
									}
									if(v3_type[i].equals("iop_ra") || v3_type[i].equals("iop_la")){
										if(applanationTs == null) {
											applanationTs = key1.getDateObserved();
										}else if(key1.getDateObserved().after(applanationTs)){
											applanationTs = key1.getDateObserved();
										}
										script.append("jQuery(\"#applanation_ts\").html('"+sdf.format(applanationTs)+"');\n");
									}
								}else{
									script.append("jQuery(\"[measurement='"+v3_type[i]+"']\").val(\""+key2.getDataField().replace("\n", "\\n").replace("\"","\\\"").replace("\r","")+"\").attr({itemtime: \"" + key2.getDateEntered().getTime() + "\", appointment_no: \"" + key2.getAppointmentNo() + "\"});\n");
									if(v3_type[i].equals("iop_rn") || v3_type[i].equals("iop_ln")){
										if(nctTs == null) {
											nctTs = key2.getDateObserved();
										}else if(key2.getDateObserved().after(nctTs)){
											nctTs = key2.getDateObserved();
										}
										script.append("jQuery(\"#nct_ts\").html('"+sdf.format(nctTs)+"');\n");
									}
									if(v3_type[i].equals("iop_ra") || v3_type[i].equals("iop_la")){
										if(applanationTs == null) {
											applanationTs = key2.getDateObserved();
										}else if(key2.getDateObserved().after(applanationTs)){
											applanationTs = key2.getDateObserved();
										}
										script.append("jQuery(\"#applanation_ts\").html('"+sdf.format(applanationTs)+"');\n");
									}
								}
								in_v3 = true;
							}
						}
						if(!in_v3){
							script.append("jQuery(\"[measurement='"+v3_type[i]+"']\").val(\""+key1.getDataField().replace("\n", "\\n").replace("\"","\\\"").replace("\r","")+"\").attr({itemtime: \"" + key1.getDateEntered().getTime() + "\", appointment_no: \"" + key1.getAppointmentNo() + "\"});\n");
							if(v2_type[i].equals("od_iop_nct") || v2_type[i].equals("os_iop_nct")){
								if(nctTs == null) {
									nctTs = key1.getDateObserved();
								}else if(key1.getDateObserved().after(nctTs)){
									nctTs = key1.getDateObserved();
								}
								script.append("jQuery(\"#nct_ts\").html('"+sdf.format(nctTs)+"');\n");
							}
							if(v2_type[i].equals("od_iop_applanation") || v2_type[i].equals("os_iop_applanation")){
								if(applanationTs == null) {
									applanationTs = key1.getDateObserved();
								}else if(key1.getDateObserved().after(applanationTs)){
									applanationTs = key1.getDateObserved();
								}
								script.append("jQuery(\"#applanation_ts\").html('"+sdf.format(applanationTs)+"');\n");
							}
							in_v3 = false;
						}
					}			
				}
			}
		}*/
		

		response.getWriter().print(script);
		return null;
	}

	public ActionForward getMeasurementsGroupByDate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String demographicNo = request.getParameter("demographicNo");
		String[] types = (request.getParameter("types") != null ? request.getParameter("types") : "").split(",");

		List<Date> measurementDates = measurementsDao.getDatesForMeasurements(demographicNo, types);
		HashMap<String, HashMap<String, Measurements>> measurementsMap = new HashMap<String, HashMap<String, Measurements>>();

		for (Date d : measurementDates) {
			Calendar c = Calendar.getInstance();
			c.setTime(d);

			Date outDate = c.getTime();

			if (!measurementsMap.keySet().contains(outDate.getTime() + ""))
				measurementsMap.put(outDate.getTime() + "", measurementsDao.getMeasurementsPriorToDate(demographicNo, d));
		}

		boolean isJsonRequest = request.getParameter("json") != null && request.getParameter("json").equalsIgnoreCase("true");

		if (isJsonRequest) {
			JSONObject json = JSONObject.fromObject(measurementsMap);
			response.getOutputStream().write(json.toString().getBytes());
		}
		return null;
	}


	public ActionForward saveValues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String demographicNo = request.getParameter("demographicNo");
		String providerNo = LoggedInInfo.loggedInInfo.get().loggedInProvider.getProviderNo();
		String strAppointmentNo = request.getParameter("appointmentNo");
		int appointmentNo = Integer.parseInt(strAppointmentNo);

		boolean isJsonRequest = request.getParameter("json") != null && request.getParameter("json").equalsIgnoreCase("true");

		try {

			Enumeration e = request.getParameterNames();
			Map<String,String> measurements = new HashMap<String,String>();

			while(e.hasMoreElements()) {
				String key = (String)e.nextElement();
				String values[] = request.getParameterValues(key);
				if(key.equals("action") || key.equals("demographicNo") || key.equals("appointmentNo"))
					continue;
				//if(values.length>0 && values[0]!=null && values[0].length()>0) { //should be able to save empty value...to clear or correct the wrong measurements.
				if(values.length>0) {
					measurements.put(key,values[0]);
					Measurements m = new Measurements();
					m.setComments("");
					m.setDataField(values[0]);
					m.setDateEntered(new Date());
					m.setDateObserved(new Date());
					m.setDemographicNo(Integer.parseInt(demographicNo));
					m.setMeasuringInstruction("");
					m.setProviderNo(providerNo);
					m.setType(key);
					m.setAppointmentNo(appointmentNo);
					measurementsDao.addMeasurements(m);
				}
			}

			if (isJsonRequest) {
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("success", true);
				JSONObject json = JSONObject.fromObject(hashMap);
				response.getOutputStream().write(json.toString().getBytes());
			}

		} catch (Exception e) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("success", false);
			MiscUtils.getLogger().error("Couldn't save measurements", e);
			JSONObject json = JSONObject.fromObject(hashMap);
			response.getOutputStream().write(json.toString().getBytes());
		}

		return null;
	}
}
