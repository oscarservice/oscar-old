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


package org.oscarehr.eyeform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.oscarehr.eyeform.dao.SpecsHistoryDao;
import org.oscarehr.eyeform.model.EyeformSpecsHistory;
import org.oscarehr.util.SpringUtils;
import oscar.OscarProperties;

import oscar.oscarEncounter.oscarMeasurements.model.Measurements;

public class MeasurementFormatter {

	Map<String,Measurements> mmap=new HashMap<String,Measurements>();
	oscar.OscarProperties props1 = oscar.OscarProperties.getInstance();
    String eyeform = props1.getProperty("cme_js");
	
	public MeasurementFormatter(List<Measurements> measurements) {
		for(Measurements m:measurements) {
			mmap.put(m.getType(), m);
		}
	}
		
	public String getGlasseshistory(Map<String,Boolean> includeMap,int app_no){
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("Glasses Rx") != null && this.getGlassesRx(app_no).length()>0){
			sb.append(this.getGlassesRx(app_no));
		}
			
		if(sb.length()>0) {
			sb.insert(0, "GLASSES HISTORY:");
		}
		return sb.toString();
	}
	
	public String getVisionAssessment(Map<String,Boolean> includeMap) {
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("Distance vision (sc)") != null && this.getVisionAssessmentDistanceVision_sc().length()>0){
			sb.append("Distance vision (sc) ");
			sb.append(this.getVisionAssessmentDistanceVision_sc());
		}
        if("eyeform3".equals(eyeform)){
    		if(includeMap.get("Distance vision (cc)") != null && this.getVisionAssessmentDistanceVision_cc().length()>0){
    			sb.append("Distance vision (cc) ");
    			sb.append(this.getVisionAssessmentDistanceVision_cc());
    		}
    		if(includeMap.get("Distance vision (ph)") != null && this.getVisionAssessmentDistanceVision_ph().length()>0){
    			sb.append("Distance vision (ph) ");
    			sb.append(this.getVisionAssessmentDistanceVision_ph());
    		}
    		if(includeMap.get("Intermediate vision (sc)") != null && this.getVisionAssessmentIntermediateVision_sc().length()>0){
    			sb.append("Intermediate vision (sc) ");
    			sb.append(this.getVisionAssessmentIntermediateVision_sc());
    		}
    		if(includeMap.get("Intermediate vision (cc)") != null && this.getVisionAssessmentIntermediateVision_cc().length()>0){
    			sb.append("Intermediate vision (cc) ");
    			sb.append(this.getVisionAssessmentIntermediateVision_cc());
    		}
    		if(includeMap.get("Near vision (sc)") != null && this.getVisionAssessmentNearVision_sc().length()>0){
    			sb.append("Near vision (sc) ");
    			sb.append(this.getVisionAssessmentNearVision_sc());
    		}
    		if(includeMap.get("Near vision (cc)") != null && this.getVisionAssessmentNearVision_cc().length()>0){
    			sb.append("Near vision (cc) ");
    			sb.append(this.getVisionAssessmentNearVision_cc());
    		}
        }else if("eyeform3.2".equals(eyeform)){
        	if(includeMap.get("Distance vision (cc)") != null && this.getVisionAssessmentDistanceVision_cc().length()>0){
    			sb.append("\nDistance vision (cc) ");
    			sb.append(this.getVisionAssessmentDistanceVision_cc());
    		}
    		if(includeMap.get("Distance vision (ph)") != null && this.getVisionAssessmentDistanceVision_ph().length()>0){
    			sb.append("\nDistance vision (ph) ");
    			sb.append(this.getVisionAssessmentDistanceVision_ph());
    		}
    		if(includeMap.get("Intermediate vision (sc)") != null && this.getVisionAssessmentIntermediateVision_sc().length()>0){
    			sb.append("\nIntermediate vision (sc) ");
    			sb.append(this.getVisionAssessmentIntermediateVision_sc());
    		}
    		if(includeMap.get("Intermediate vision (cc)") != null && this.getVisionAssessmentIntermediateVision_cc().length()>0){
    			sb.append("\nIntermediate vision (cc) ");
    			sb.append(this.getVisionAssessmentIntermediateVision_cc());
    		}
    		if(includeMap.get("Near vision (sc)") != null && this.getVisionAssessmentNearVision_sc().length()>0){
    			sb.append("\nNear vision (sc) ");
    			sb.append(this.getVisionAssessmentNearVision_sc());
    		}
    		if(includeMap.get("Near vision (cc)") != null && this.getVisionAssessmentNearVision_cc().length()>0){
    			sb.append("\nNear vision (cc) ");
    			sb.append(this.getVisionAssessmentNearVision_cc());
    		}
        }else{	
			if(includeMap.get("Auto-refraction") != null && this.getVisionAssessmentAutoRefraction().length()>0) {
				sb.append("Auto-refraction ");
				sb.append(this.getVisionAssessmentAutoRefraction());
			}
			if(includeMap.get("Keratometry") != null && this.getVisionAssessmentKeratometry().length()>0) {
				sb.append("Keratometry ");
				sb.append(this.getVisionAssessmentKeratometry());
			}
			if(includeMap.get("Distance vision (sc)") != null && this.getVisionAssessmentVision("distance", "sc").length()>0) {
				sb.append("Distance vision (sc) ");
				sb.append(this.getVisionAssessmentVision("distance", "sc"));
			}
			if(includeMap.get("Distance vision (cc)") != null && this.getVisionAssessmentVision("distance", "cc").length()>0) {
				sb.append("Distance vision (cc) ");
				sb.append(this.getVisionAssessmentVision("distance", "cc"));
			}
			if(includeMap.get("Distance vision (ph)") != null && this.getVisionAssessmentVision("distance", "ph").length()>0) {
				sb.append("Distance vision (ph)");
				sb.append(this.getVisionAssessmentVision("distance", "ph"));
			}
			if(includeMap.get("Near vision (sc)") != null && this.getVisionAssessmentVision("near", "sc").length()>0) {
				sb.append("Near vision (sc) ");
				sb.append(this.getVisionAssessmentVision("near", "sc"));
			}
			if(includeMap.get("Near vision (cc)") != null && this.getVisionAssessmentVision("near", "cc").length()>0) {
				sb.append("Near vision (cc) ");
				sb.append(this.getVisionAssessmentVision("near", "cc"));
			}
        }
		if(sb.length()>0) {
			sb.insert(0, "VISION ASSESSMENT:\n");
		}
		return sb.toString();
	}
	
	public String getManifestVision(Map<String,Boolean> includeMap) {
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("Auto-refraction") != null && this.getAutoRefraction().length()>0) {
			sb.append("Auto-refraction ");
			sb.append(this.getAutoRefraction());
		}
		if("eyeform3.2".equals(eyeform)){
			if(includeMap.get("Keratometry") != null && this.getKeratometry().length()>0) {
				sb.append("\nKeratometry ");
				sb.append(this.getKeratometry());
			}
			if(includeMap.get("Manifest distance") != null && getManifestDistance().length()>0) {
				sb.append("\nManifest distance ");
				sb.append(getManifestDistance());
			}
			if(includeMap.get("Manifest near") != null && getManifestNear().length()>0) {
				sb.append("\nManifest near ");
				sb.append(getManifestNear());
			}
			if(includeMap.get("Cycloplegic refraction") != null && this.getCycloplegicRefraction().length()>0) {
				sb.append("\nCycloplegic refraction ");
				sb.append(this.getCycloplegicRefraction());
			}
		}else{
			if(includeMap.get("Keratometry") != null && this.getKeratometry().length()>0) {
				sb.append("Keratometry ");
				sb.append(this.getKeratometry());
			}
			if(includeMap.get("Manifest distance") != null && getManifestDistance().length()>0) {
				sb.append("Manifest distance ");
				sb.append(getManifestDistance());
			}
			if(includeMap.get("Manifest near") != null && getManifestNear().length()>0) {
				sb.append("Manifest near ");
				sb.append(getManifestNear());
			}
			if(includeMap.get("Cycloplegic refraction") != null && this.getCycloplegicRefraction().length()>0) {
				sb.append("Cycloplegic refraction ");
				sb.append(this.getCycloplegicRefraction());
			}
		}
		if(sb.length()>0) {
			sb.insert(0,"VISION MEASUREMENT:\n");
		}
		return sb.toString();
	}
	
	public String getStereoVision(Map<String,Boolean> includeMap){
		StringBuilder sb = new StringBuilder();
		if("eyeform3.2".equals(eyeform)){
			if(includeMap.get("Fly test") != null && this.getFlytest().length()>0) {
				sb.append("Fly test      ");
				sb.append(this.getFlytest());
			}
			if(includeMap.get("Stereo-acuity") != null && this.getStereo_acuity().length()>0) {
				sb.append("\nStereo-acuity ");
				sb.append(this.getStereo_acuity());
			}
		}else{
			if(includeMap.get("Fly test") != null && this.getFlytest().length()>0) {
				sb.append("Fly test ");
				sb.append(this.getFlytest());
			}
			if(includeMap.get("Stereo-acuity") != null && this.getStereo_acuity().length()>0) {
				sb.append("Stereo-acuity ");
				sb.append(this.getStereo_acuity());
			}
		}
		if(sb.length()>0) {
			sb.insert(0,"STEREO VISION:\n");
		}
		return sb.toString();
	}
	
	public String getIntraocularPressure(Map<String,Boolean> includeMap) {
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("NCT") != null && this.getNCT().length()>0) {
			sb.append("NCT ");
			sb.append(this.getNCT());
		}
		if("eyeform3.2".equals(eyeform)){
			if(includeMap.get("Applanation") != null && this.getApplanation().length()>0) {
				sb.append("\nApplanation ");
				sb.append(this.getApplanation());
			}
			if(includeMap.get("Central corneal thickness") != null && this.getCCT().length()>0) {
				sb.append("\nCentral corneal thickness ");
				sb.append(this.getCCT());
			}
		}else{
			if(includeMap.get("Applanation") != null && this.getApplanation().length()>0) {
				sb.append("Applanation ");
				sb.append(this.getApplanation());
			}
			if(includeMap.get("Central corneal thickness") != null && this.getCCT().length()>0) {
				sb.append("Central corneal thickness ");
				sb.append(this.getCCT());
			}
		}
		if(sb.length()>0) {
			sb.insert(0,"INTRAOCULAR PRESSURE:\n");
		}
		return sb.toString();
	}
	
	public String getRactive(Map<String,Boolean> includeMap){
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("Dominance") != null && this.getDominance().length()>0) {
			sb.append("Dominance ");
			sb.append(this.getDominance());
		}
		if("eyeform3.2".equals(eyeform)){
			if(includeMap.get("Mesopic pupil size") != null && this.getMesopicPupilSize().length()>0) {
				sb.append("\nMesopic pupil size ");
				sb.append(this.getMesopicPupilSize());
			}
			if(includeMap.get("Angle Kappa") != null && this.getAngleKappa().length()>0) {
				sb.append("\nAngle Kappa ");
				sb.append(this.getAngleKappa());
			}
		}else{
			if(includeMap.get("Mesopic pupil size") != null && this.getMesopicPupilSize().length()>0) {
				sb.append("Mesopic pupil size ");
				sb.append(this.getMesopicPupilSize());
			}
			if(includeMap.get("Angle Kappa") != null && this.getAngleKappa().length()>0) {
				sb.append("Angle Kappa ");
				sb.append(this.getAngleKappa());
			}
		}
		if(sb.length() > 0){
			sb.insert(0,"REFRACTIVE:\n");
		}
		return sb.toString();
	}
	
	public String getOtherExam(Map<String,Boolean> includeMap) {
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("Colour vision") != null && this.getColourVision().length()>0) {
			sb.append("Colour vision ");
			sb.append(this.getColourVision());
		}
		if("eyeform3.2".equals(eyeform)){
			if(includeMap.get("Pupil") != null && this.getPupil().length()>0) {
				sb.append("\nPupil ");
				sb.append(this.getPupil());
			}
			if(includeMap.get("Amsler grid") != null && this.getAmslerGrid().length()>0) {
				sb.append("\nAmsler grid ");
				sb.append(this.getAmslerGrid());
			}
			if(includeMap.get("Potential acuity meter") != null && this.getPAM().length()>0) {
				sb.append("\nPotential acuity meter ");
				sb.append(this.getPAM());
			}
			if(includeMap.get("Confrontation fields") != null && this.getConfrontation().length()>0) {
				sb.append("\nConfrontation fields ");
				sb.append(this.getConfrontation());
			}
			if(includeMap.get("Maddox rod") != null && this.getMaddoxrod().length()>0) {
				sb.append("\nMaddox rod             ");
				sb.append(this.getMaddoxrod());
			}
			if(includeMap.get("Bagolini test") != null && this.getBagolinitest().length()>0) {
				sb.append("\nBagolini test          ");
				sb.append(this.getBagolinitest());
			}
			if(includeMap.get("Worth 4 Dot (distance)") != null && this.getW4dD().length()>0) {
				sb.append("\nWorth 4 Dot (distance) ");
				sb.append(this.getW4dD());
			}
			if(includeMap.get("Worth 4 Dot (near)") != null && this.getW4dN().length()>0) {
				sb.append("\nWorth 4 Dot (near)     ");
				sb.append(this.getW4dN());
			}
		}else{
			if(includeMap.get("Pupil") != null && this.getPupil().length()>0) {
				sb.append("Pupil ");
				sb.append(this.getPupil());
			}
			if(includeMap.get("Amsler grid") != null && this.getAmslerGrid().length()>0) {
				sb.append("Amsler grid ");
				sb.append(this.getAmslerGrid());
			}
			if(includeMap.get("Potential acuity meter") != null && this.getPAM().length()>0) {
				sb.append("Potential acuity meter ");
				sb.append(this.getPAM());
			}
			if(includeMap.get("Confrontation fields") != null && this.getConfrontation().length()>0) {
				sb.append("Confrontation fields ");
				sb.append(this.getConfrontation());
			}
			if(includeMap.get("Maddox rod") != null && this.getMaddoxrod().length()>0) {
				sb.append("Maddox rod ");
				sb.append(this.getMaddoxrod());
			}
			if(includeMap.get("Bagolini test") != null && this.getBagolinitest().length()>0) {
				sb.append("Bagolini test ");
				sb.append(this.getBagolinitest());
			}
			if(includeMap.get("Worth 4 Dot (distance)") != null && this.getW4dD().length()>0) {
				sb.append("Worth 4 Dot (distance) ");
				sb.append(this.getW4dD());
			}
			if(includeMap.get("Worth 4 Dot (near)") != null && this.getW4dN().length()>0) {
				sb.append("Worth 4 Dot (near) ");
				sb.append(this.getW4dN());
			}
		}
		if(sb.length()>0) {
			sb.insert(0,"OTHER EXAM:\n");
		}
		return sb.toString();
	}

	public String getDuctionTesting(Map<String,Boolean> includeMap){
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("DUCTION/DIPLOPIA TESTING") != null && this.getDuction().length()>0) {
			sb.append("DUCTION/DIPLOPIA TESTING: \n");
			sb.append(this.getDuction());
		}
		return sb.toString();
	}
	
	public String getDeviationMeasurement(Map<String,Boolean> includeMap){ 
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("Primary gaze") != null && this.getPrimarygaze().length()>0) {
			sb.append("Primary gaze:      ");
			sb.append(this.getPrimarygaze());
		}
		if("eyeform3.2".equals(eyeform)){
			if(includeMap.get("Up gaze") != null && this.getUpgaze().length()>0) {
				sb.append("\nUp gaze:           ");
				sb.append(this.getUpgaze());
			}
			if(includeMap.get("Down gaze") != null && this.getDowngaze().length()>0) {
				sb.append("\nDown gaze:         ");
				sb.append(this.getDowngaze());
			}
			if(includeMap.get("Right gaze") != null && this.getRightgaze().length()>0) {
				sb.append("\nRight gaze:        ");
				sb.append(this.getRightgaze());
			}
			if(includeMap.get("Left gaze") != null && this.getLeftgaze().length()>0) {
				sb.append("\nLeft gaze:         ");
				sb.append(this.getLeftgaze());
			}
			if(includeMap.get("Right head tilt") != null && this.getRighthead().length()>0) {
				sb.append("\nRight head tilt:   ");
				sb.append(this.getRighthead());
			}
			if(includeMap.get("Left head tilt") != null && this.getLefthead().length()>0) {
				sb.append("\nLeft head tilt:    ");
				sb.append(this.getLefthead());
			}
			if(includeMap.get("Near") != null && this.getNear().length()>0) {
				sb.append("\nNear:              ");
				sb.append(this.getNear());
			}
			if(includeMap.get("Near with +3D add") != null && this.getNearwith().length()>0) {
				sb.append("\nNear with +3D add: ");
				sb.append(this.getNearwith());
			}
			if(includeMap.get("Far distance") != null && this.getFardistance().length()>0) {
				sb.append("\nFar distance:      ");
				sb.append(this.getFardistance());
			}
		}else{
			if(includeMap.get("Up gaze") != null && this.getUpgaze().length()>0) {
				sb.append("Up gaze: ");
				sb.append(this.getUpgaze());
			}
			if(includeMap.get("Down gaze") != null && this.getDowngaze().length()>0) {
				sb.append("Down gaze: ");
				sb.append(this.getDowngaze());
			}
			if(includeMap.get("Right gaze") != null && this.getRightgaze().length()>0) {
				sb.append("Right gaze: ");
				sb.append(this.getRightgaze());
			}
			if(includeMap.get("Left gaze") != null && this.getLeftgaze().length()>0) {
				sb.append("Left gaze: ");
				sb.append(this.getLeftgaze());
			}
			if(includeMap.get("Right head tilt") != null && this.getRighthead().length()>0) {
				sb.append("Right head tilt: ");
				sb.append(this.getRighthead());
			}
			if(includeMap.get("Left head tilt") != null && this.getLefthead().length()>0) {
				sb.append("Left head tilt: ");
				sb.append(this.getLefthead());
			}
			if(includeMap.get("Near") != null && this.getNear().length()>0) {
				sb.append("Near: ");
				sb.append(this.getNear());
			}
			if(includeMap.get("Near with +3D add") != null && this.getNearwith().length()>0) {
				sb.append("Near with +3D add: ");
				sb.append(this.getNearwith());
			}
			if(includeMap.get("Far distance") != null && this.getFardistance().length()>0) {
				sb.append("Far distance: ");
				sb.append(this.getFardistance());
			}
		}
		if(sb.length()>0) {
			sb.insert(0,"DEVIATION MEASUREMENT:\n");
		}
		return sb.toString();
	}
	
	public String getEOMStereo(Map<String,Boolean> includeMap) {
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("EOM") != null && this.getEomStereo().length()>0) {
			sb.append("EOM/Stereo ");
			sb.append(this.getEomStereo());
		}
				
		return sb.toString();
	}
	
	public String getAnteriorSegment(Map<String,Boolean> includeMap) {
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("Cornea") != null && this.getCornea().length()>0) {
			sb.append("Cornea ");
			sb.append(this.getCornea());
		}
		if("eyeform3.2".equals(eyeform)){
			if(includeMap.get("Conjunctiva/Sclera") != null && this.getConjuctivaSclera().length()>0) {
				sb.append("\nConjunctiva/Sclera ");
				sb.append(this.getConjuctivaSclera());
			}
			if(includeMap.get("Anterior chamber") != null && this.getAnteriorChamber().length()>0) {
				sb.append("\nAnterior chamber ");
				sb.append(this.getAnteriorChamber());
			}
			if(includeMap.get("Angle") != null && this.getAngle().length()>0) {
				sb.append("\nAngle ");
				sb.append(this.getAngle());
			}
			if(includeMap.get("Iris") != null && this.getIris().length()>0) {
				sb.append("\nIris ");
				sb.append(this.getIris());
			}
			if(includeMap.get("Lens") != null && this.getLens().length()>0) {
				sb.append("\nLens ");
				sb.append(this.getLens());
			}
		}else{
			if(includeMap.get("Conjunctiva/Sclera") != null && this.getConjuctivaSclera().length()>0) {
				sb.append("Conjunctiva/Sclera ");
				sb.append(this.getConjuctivaSclera());
			}
			if(includeMap.get("Anterior chamber") != null && this.getAnteriorChamber().length()>0) {
				sb.append("Anterior chamber ");
				sb.append(this.getAnteriorChamber());
			}
			if(includeMap.get("Angle") != null && this.getAngle().length()>0) {
				sb.append("Angle ");
				sb.append(this.getAngle());
			}
			if(includeMap.get("Iris") != null && this.getIris().length()>0) {
				sb.append("Iris ");
				sb.append(this.getIris());
			}
			if(includeMap.get("Lens") != null && this.getLens().length()>0) {
				sb.append("Lens ");
				sb.append(this.getLens());
			}
		}
		if(sb.length()>0) {
			sb.insert(0,"ANTERIOR SEGMENT:\n");
		}
		return sb.toString();
	}
	
	public String getPosteriorSegment(Map<String,Boolean> includeMap) {
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("Optic disc") != null && this.getDisc().length()>0) {
			sb.append("Optic disc ");
			sb.append(this.getDisc());
		}
		if("eyeform3.2".equals(eyeform)){
			if(includeMap.get("C/D ratio") != null && this.getCdRatio().length()>0) {
				sb.append("\nC/D ratio ");
				sb.append(this.getCdRatio());
			}	
			if(includeMap.get("Macula") != null && this.getMacula().length()>0) {
				sb.append("\nMacula ");
				sb.append(this.getMacula());
			}	
			if(includeMap.get("Retina") != null && this.getRetina().length()>0) {
				sb.append("\nRetina ");
				sb.append(this.getRetina());
			}	
			if(includeMap.get("Vitreous") != null && this.getVitreous().length()>0) {
				sb.append("\nVitreous ");
				sb.append(this.getVitreous());
			}
		}else{
			if(includeMap.get("C/D ratio") != null && this.getCdRatio().length()>0) {
				sb.append("C/D ratio ");
				sb.append(this.getCdRatio());
			}	
			if(includeMap.get("Macula") != null && this.getMacula().length()>0) {
				sb.append("Macula ");
				sb.append(this.getMacula());
			}	
			if(includeMap.get("Retina") != null && this.getRetina().length()>0) {
				sb.append("Retina ");
				sb.append(this.getRetina());
			}	
			if(includeMap.get("Vitreous") != null && this.getVitreous().length()>0) {
				sb.append("Vitreous ");
				sb.append(this.getVitreous());
			}
		}
		if(sb.length()>0) {
			sb.insert(0,"POSTERIOR SEGMENT:\n");
		}
		return sb.toString();
	}
	
	public String getExternalOrbit(Map<String,Boolean> includeMap) {
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("Face") != null && this.getFace().length()>0) {
			sb.append("Face ");
			sb.append(this.getFace());
		}
		if((!"eyeform3".equals(eyeform)) && (!"eyeform3.1".equals(eyeform)) && (!"eyeform3.2".equals(eyeform))){
			if(includeMap.get("Upper lid") != null && this.getUpperLid().length()>0) {
				sb.append("Upper lid ");
				sb.append(this.getUpperLid());
			}
			if(includeMap.get("Lower lid") != null && this.getLowerLid().length()>0) {
				sb.append("Lower lid ");
				sb.append(this.getLowerLid());
			}
			if(includeMap.get("Punctum") != null && this.getPunctum().length()>0) {
				sb.append("Punctum ");
				sb.append(this.getPunctum());
			}
			if(includeMap.get("Lacrimal lake") != null && this.getLacrimalLake().length()>0) {
				sb.append("Lacrimal lake ");
				sb.append(this.getLacrimalLake());
			}
		}else if("eyeform3".equals(eyeform)){
			if(includeMap.get("Retropulsion") != null && this.getRetropulsion().length()>0) {
				sb.append("Retropulsion ");
				sb.append(this.getRetropulsion());
			}
			if(includeMap.get("Hertel") != null && this.getHertel().length()>0) {
				sb.append("Hertel ");
				sb.append(this.getHertel());
			}
		}else if("eyeform3.2".equals(eyeform)){
			if(includeMap.get("Retropulsion") != null && this.getRetropulsion().length()>0) {
				sb.append("\nRetropulsion ");
				sb.append(this.getRetropulsion());
			}
			if(includeMap.get("Hertel") != null && this.getHertel().length()>0) {
				sb.append("\nHertel ");
				sb.append(this.getHertel());
			}
		}else{
			if(includeMap.get("Retropulsion") != null && this.getRetropulsion().length()>0) {
				sb.append("Retropulsion ");
				sb.append(this.getRetropulsion());
			}
			if(includeMap.get("Hertel") != null && this.getHertel().length()>0) {
				sb.append("Hertel ");
				sb.append(this.getHertel());
			}
		}
		
		if(sb.length()>0) {
			sb.insert(0,"EXTERNAL/ORBIT:\n");
		}
		return sb.toString();
	}
	
	public String getEyelidDuct(Map<String,Boolean> includeMap){ 
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("Upper lid") != null && this.getUpperLid().length()>0) {
			sb.append("Upper lid ");
			sb.append(this.getUpperLid());
		}
		if("eyeform3.2".equals(eyeform)){
			if(includeMap.get("Lower lid") != null && this.getLowerLid().length()>0) {
				sb.append("\nLower lid ");
				sb.append(this.getLowerLid());
			}
			if(includeMap.get("Lacrimal lake") != null && this.getLacrimalLake().length()>0) {
				sb.append("\nLacrimal lake ");
				sb.append(this.getLacrimalLake());
			}
			if(includeMap.get("Lacrimal irrigation") != null && this.getLacrimalIrrigation().length()>0) {
				sb.append("\nLacrimal irrigation ");
				sb.append(this.getLacrimalIrrigation());
			}
			if(includeMap.get("Punctum") != null && this.getPunctum().length()>0) {
				sb.append("\nPunctum ");
				sb.append(this.getPunctum());
			}
			if(includeMap.get("Nasolacrimal duct") != null && this.getNLD().length()>0) {
				sb.append("\nNasolacrimal duct ");
				sb.append(this.getNLD());
			}
			if(includeMap.get("Dye disappearance") != null && this.getDyeDisappearance().length()>0) {
				sb.append("\nDye disappearance ");
				sb.append(this.getDyeDisappearance());
			}
		}else{
			if(includeMap.get("Lower lid") != null && this.getLowerLid().length()>0) {
				sb.append("Lower lid ");
				sb.append(this.getLowerLid());
			}
			if(includeMap.get("Lacrimal lake") != null && this.getLacrimalLake().length()>0) {
				sb.append("Lacrimal lake ");
				sb.append(this.getLacrimalLake());
			}
			if(includeMap.get("Lacrimal irrigation") != null && this.getLacrimalIrrigation().length()>0) {
				sb.append("Lacrimal irrigation ");
				sb.append(this.getLacrimalIrrigation());
			}
			if(includeMap.get("Punctum") != null && this.getPunctum().length()>0) {
				sb.append("Punctum ");
				sb.append(this.getPunctum());
			}
			if(includeMap.get("Nasolacrimal duct") != null && this.getNLD().length()>0) {
				sb.append("Nasolacrimal duct ");
				sb.append(this.getNLD());
			}
			if(includeMap.get("Dye disappearance") != null && this.getDyeDisappearance().length()>0) {
				sb.append("Dye disappearance ");
				sb.append(this.getDyeDisappearance());
			}
		}
		if(sb.length()>0) {
			sb.insert(0,"EYELID/NASOLACRIMAL DUCT:\n");
		}
		return sb.toString();
	}
	
	public String getNasalacrimalDuct(Map<String,Boolean> includeMap) {
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("Lacrimal irrigation") != null && this.getLacrimalIrrigation().length()>0) {
			sb.append("Lacrimal irrigation ");
			sb.append(this.getLacrimalIrrigation());
		}
		if(includeMap.get("Nasolacrimal duct") != null && this.getNLD().length()>0) {
			sb.append("Nasolacrimal duct ");
			sb.append(this.getNLD());
		}
		if(includeMap.get("Dye disappearance") != null && this.getDyeDisappearance().length()>0) {
			sb.append("Dye disappearance ");
			sb.append(this.getDyeDisappearance());
		}
		
		if(sb.length()>0) {
			sb.insert(0,"NASOLACRIMAL DUCT:");
		}
		return sb.toString();
	}
	
	public String getEyelidMeasurement(Map<String,Boolean> includeMap) {
		StringBuilder sb = new StringBuilder();
		if(includeMap.get("Margin reflex distance") != null && this.getMarginReflexDistance().length()>0) {
			sb.append("Margin reflex distance ");
			sb.append(this.getMarginReflexDistance());
		}
		if("eyeform3.2".equals(eyeform)){
			if(includeMap.get("Levator function") != null && this.getLevatorFunction().length()>0) {
				sb.append("\nLevator function ");
				sb.append(this.getLevatorFunction());
			}
			if(includeMap.get("Inferior scleral show") != null && this.getInferiorScleralShow().length()>0) {
				sb.append("\nInferior scleral show ");
				sb.append(this.getInferiorScleralShow());
			}
			if(includeMap.get("Lagophthalmos") != null && this.getLagophthalmos().length()>0) {
				sb.append("\nLagophthalmos ");
				sb.append(this.getLagophthalmos());
			}
			if(includeMap.get("Blink reflex") != null && this.getBlink().length()>0) {
				sb.append("\nBlink reflex ");
				sb.append(this.getBlink());
			}
			if(includeMap.get("Cranial Nerve VII function") != null && this.getCNVii().length()>0) {
				sb.append("\nCranial nerve VII function ");
				sb.append(this.getCNVii());
			}
		}else{
			if(includeMap.get("Levator function") != null && this.getLevatorFunction().length()>0) {
				sb.append("Levator function ");
				sb.append(this.getLevatorFunction());
			}
			if(includeMap.get("Inferior scleral show") != null && this.getInferiorScleralShow().length()>0) {
				sb.append("Inferior scleral show ");
				sb.append(this.getInferiorScleralShow());
			}
			if(includeMap.get("Lagophthalmos") != null && this.getLagophthalmos().length()>0) {
				sb.append("Lagophthalmos ");
				sb.append(this.getLagophthalmos());
			}
			if(includeMap.get("Blink reflex") != null && this.getBlink().length()>0) {
				sb.append("Blink reflex ");
				sb.append(this.getBlink());
			}
			if(includeMap.get("Cranial Nerve VII function") != null && this.getCNVii().length()>0) {
				sb.append("Cranial nerve VII function ");
				sb.append(this.getCNVii());
			}
		}
		if("eyeform3".equals(eyeform)){
			if(includeMap.get("Bells phenomenon") != null && this.getBells().length()>0) {
				sb.append("Bell's phenomenon ");
				sb.append(this.getBells());
			}
			if(includeMap.get("Schirmer test") != null && this.getSchirmertest().length()>0) {
				sb.append("Schirmer test ");
				sb.append(this.getSchirmertest());
			}
		}else if("eyeform3.2".equals(eyeform)){
			if(includeMap.get("Bells phenomenon") != null && this.getBells().length()>0) {
				sb.append("\nBell's phenomenon ");
				sb.append(this.getBells());
			}
			if(includeMap.get("Schirmer test") != null && this.getSchirmertest().length()>0) {
				sb.append("\nSchirmer test ");
				sb.append(this.getSchirmertest());
			}
		}else{
			if(includeMap.get("Bells phenomenon") != null && this.getBells().length()>0) {
				sb.append("Bell's phenomenon ");
				sb.append(this.getBells());
			}
		}
		if(sb.length()>0) {
			sb.insert(0,"EYELID MEASUREMENT:\n");
		}
		return sb.toString();
	}
	
	public String[] getGlassesRx1(int app_no){
		String str[] = new String[4];
		int count = 0;
		SpecsHistoryDao dao = (SpecsHistoryDao)SpringUtils.getBean("SpecsHistoryDAO");
		List<EyeformSpecsHistory> specs = dao.getByAppointmentNo(app_no);
		for(EyeformSpecsHistory spec:specs){
			StringBuilder sb = new StringBuilder();
//			sb.append("Glasses Rx ");
			StringBuilder sb1 = new StringBuilder();
			if(spec.getOdSph() != null){
				sb1.append(spec.getOdSph());
			}
			if(spec.getOdCyl() != null){
				sb1.append(" " +spec.getOdCyl());
			}
			if(spec.getOdAxis() != null){
				sb1.append("x " + spec.getOdAxis());
			}
			if(spec.getOdAdd() != null){
				sb1.append(" add " + spec.getOdAdd());
			}
			if(spec.getOdPrism() != null){
				sb1.append(" prism " + spec.getOdPrism());
			}
			if(sb1.length() > 0){
				sb1.append("; ");
				sb1.insert(0, "OD ");
				sb.append(sb1);
			}
			
			StringBuilder sb2 = new StringBuilder();
			if(spec.getOsSph() != null){
				sb2.append(" " +spec.getOsSph());
			}
			if(spec.getOsCyl() != null){
				sb2.append(" " +spec.getOsCyl());
			}
			if(spec.getOsAxis() != null){
				sb2.append(" x " + spec.getOsAxis());
			}
			if(spec.getOsAdd() != null){
				sb2.append(" add " + spec.getOsAdd());
			}
			if(spec.getOsPrism() != null){
				sb2.append(" prism " + spec.getOsPrism());
			}
			if(sb2.length() > 0){
				sb2.append("; ");
				if("eyeform3.2".equals(eyeform)){
					sb.append("\n           ");
				}
				sb2.insert(0, "OS ");
				sb.append(sb2);
			}
			
			
			
			if(spec.getDateStr() != null){
				if("eyeform3.2".equals(eyeform)){
					sb.append("\n           ");
				}
				sb.append("date ");
				sb.append(spec.getDateStr());
				
				sb.append("; ");
			}
			if(spec.getNote() != null){
				if("eyeform3.2".equals(eyeform)){
					sb.append("\n           note ");
					sb.append(spec.getNote());
					sb.append(".");
				}else{
					sb.append("note ");
					sb.append(spec.getNote());
					sb.append(".");
				}				
			}
			str[count] = sb.toString();
			count ++;
		}
		return str;
	}
	
	public String getGlassesRx(int app_no){
		StringBuilder sb = new StringBuilder();
		SpecsHistoryDao dao = (SpecsHistoryDao)SpringUtils.getBean("SpecsHistoryDAO");
		List<EyeformSpecsHistory> specs = dao.getByAppointmentNo(app_no);
		for(EyeformSpecsHistory spec:specs){
			sb.append("\nGlasses Rx ");
			StringBuilder sb1 = new StringBuilder();
			if(spec.getOdSph() != null){
				sb1.append(spec.getOdSph());
			}
			if(spec.getOdCyl() != null){
				sb1.append(" " +spec.getOdCyl());
			}
			if(spec.getOdAxis() != null){
				sb1.append("x " + spec.getOdAxis());
			}
			if(spec.getOdAdd() != null){
				sb1.append(" add " + spec.getOdAdd());
			}
			if(spec.getOdPrism() != null){
				sb1.append(" prism " + spec.getOdPrism());
			}
			if(sb1.length() > 0){
				sb1.append("; ");
				sb1.insert(0, "OD ");
				sb.append(sb1);
			}
			
			StringBuilder sb2 = new StringBuilder();
			if(spec.getOsSph() != null){
				sb2.append(" " +spec.getOsSph());
			}
			if(spec.getOsCyl() != null){
				sb2.append(" " +spec.getOsCyl());
			}
			if(spec.getOsAxis() != null){
				sb2.append(" x " + spec.getOsAxis());
			}
			if(spec.getOsAdd() != null){
				sb2.append(" add " + spec.getOsAdd());
			}
			if(spec.getOsPrism() != null){
				sb2.append(" prism " + spec.getOsPrism());
			}
			if(sb2.length() > 0){
				sb2.append("; ");
				if("eyeform3.2".equals(eyeform)){
					sb.append("\n           ");
				}
				sb2.insert(0, "OS ");
				sb.append(sb2);
			}
			
			
			
			if(spec.getDateStr() != null){
				if("eyeform3.2".equals(eyeform)){
					sb.append("\n           ");
				}
				sb.append("date ");
				sb.append(spec.getDateStr());
				
				sb.append("; ");
			}
			if(spec.getNote() != null){
				if("eyeform3.2".equals(eyeform)){
					sb.append("\n           note ");
					sb.append(spec.getNote());
					sb.append(".");
				}else{
					sb.append("note ");
					sb.append(spec.getNote());
					sb.append(".");
				}				
			}
		}
		return sb.toString();
	}
	public String getAutoRefraction(){		
		StringBuilder sb = new StringBuilder();
		if(isPresent("v_rs")) {		
			sb.append(getValue("v_rs"));
		}
		if(isPresent("v_rc")) {
			sb.append(" " +getValue("v_rc"));
		}
		if(isPresent("v_rx")) {
			sb.append(" x " + getValue("v_rx"));
		}
		if(isPresent("v_rar")) {
			sb.append("(" + getValue("v_rar")+ ")");
		}
		if(sb.length() > 0){
			sb.insert(0, "OD ");
			sb.append("; ");
		}
		
		StringBuilder sb1 = new StringBuilder();
		if(isPresent("v_ls")) {			
			sb1.append(" " +getValue("v_ls"));
		}
		if(isPresent("v_ls")) {
			sb1.append(" " +getValue("v_lc"));
		}
		if(isPresent("v_lx")) {
			sb1.append(" x " + getValue("v_lx"));
		}
		if(isPresent("v_ls")) {
			sb1.append("(" + getValue("v_lar")+ ")");
		}
		if("eyeform3.2".equals(eyeform)){
			if(sb1.length() > 0){
				sb1.insert(0, "\n            OS");
				sb1.append(".");
				sb.append(sb1);
			}
		}else{
			if(sb1.length() > 0){
				sb1.insert(0, "OS");
				sb1.append(".");
				sb.append(sb1);
			}
		}
		return sb.toString();
	}
	public String getKeratometry(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("v_rk1")){
			sb.append(getValue("v_rk1"));
		}
		if(isPresent("v_rk2")){
			sb.append(" x " + getValue("v_rk2"));
		}
		if(isPresent("v_rkx")){
			sb.append(" @ " + getValue("v_rkx"));
		}
		if(sb.length() > 0){
			sb.insert(0, "OD ");
			sb.append("; ");
		}
		
		StringBuilder sb1 = new StringBuilder();
		if(isPresent("v_lk1")) {
			
			sb1.append(" " +getValue("v_lk1"));
		}
		if(isPresent("v_lk1")) {
			sb1.append(" x " + getValue("v_lk2"));
		}
		if(isPresent("v_lk1")) {
			sb1.append(" @ " + getValue("v_lkx"));
			
		}
		if("eyeform3.2".equals(eyeform)){
			if(sb1.length() > 0){
				sb1.insert(0, "\n                OS ");
				sb1.append(".");
				sb.append(sb1);
			}
		}else{
			if(sb1.length() > 0){
				sb1.insert(0, "OS ");
				sb1.append(".");
				sb.append(sb1);
			}
		}
		return sb.toString();
	}
	public String getVisionAssessmentAutoRefraction() {
		StringBuilder sb = new StringBuilder();		
		if(isPresent("od_ar_sph")) {
			sb.append("OD ");
			sb.append(getValue("od_ar_sph"));
			sb.append(getValue("od_ar_cyl"));
			if(isPresent("od_ar_axis")) {
				sb.append("x" + getValue("od_ar_axis"));
			}
			sb.append("; ");
		}
		if(isPresent("os_ar_sph")) {
			sb.append("OS ");
			sb.append(getValue("os_ar_sph"));
			sb.append(getValue("os_ar_cyl"));
			if(isPresent("os_ar_axis")) {
				sb.append("x" + getValue("os_ar_axis"));
			}
			sb.append(".");
		}
		return sb.toString();
	
	}
	
	public String getVisionAssessmentKeratometry() {
		StringBuilder sb = new StringBuilder();
		if(isPresent("od_k1")) {
			sb.append("OD ");
			sb.append(getValue("od_k1"));
			sb.append("x" + getValue("od_k2"));
			sb.append("@" + getValue("od_k2_axis"));
			sb.append("; ");
		}
		if(isPresent("os_k1")) {
			sb.append("OS ");
			sb.append(getValue("os_k1"));
			sb.append("x" + getValue("os_k2"));
			sb.append("@" + getValue("os_k2_axis"));
			sb.append(".");
		}
		return sb.toString();
	
	}
	public String getVisionAssessmentDistanceVision_sc(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("v_rdsc")){
			sb.append("OD ");
			sb.append(getValue("v_rdsc"));
			sb.append(";");
		}
		if("eyeform3.2".equals(eyeform)){
			if(isPresent("v_ldsc")){
				sb.append("\n                     OS ");
				sb.append(getValue("v_ldsc"));
				sb.append("; ");
			}
			if(isPresent("v_dsc")){
				sb.append("\n                     OU ");
				sb.append(getValue("v_dsc"));
				sb.append(". ");
			}
		}else{
			if(isPresent("v_ldsc")){
				sb.append("OS ");
				sb.append(getValue("v_ldsc"));
				sb.append("; ");
			}
			if(isPresent("v_dsc")){
				sb.append("OU ");
				sb.append(getValue("v_dsc"));
				sb.append(". ");
			}
		}
		return sb.toString();
	}
	public String getVisionAssessmentDistanceVision_cc(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("v_rdcc")){
			sb.append("OD ");
			sb.append(getValue("v_rdcc"));
			sb.append("; ");
		}
		if("eyeform3.2".equals(eyeform)){
			if(isPresent("v_ldcc")){			
				sb.append("\n                     OS ");
				sb.append(getValue("v_ldcc"));
				sb.append("; ");
			}
			if(isPresent("v_dcc")){
				sb.append("\n                     OU ");
				sb.append(getValue("v_dcc"));
				sb.append(". ");
			}
		}else{
			if(isPresent("v_ldcc")){			
				sb.append("OS ");
				sb.append(getValue("v_ldcc"));
				sb.append("; ");
			}
			if(isPresent("v_dcc")){
				sb.append("OU ");
				sb.append(getValue("v_dcc"));
				sb.append(". ");
			}
		}
		return sb.toString();
	}
	public String getVisionAssessmentDistanceVision_ph(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("v_rph")){
			sb.append("OD ");
			sb.append(getValue("v_rph"));
			sb.append("; ");
		}
		if("eyeform3.2".equals(eyeform)){
			if(isPresent("v_lph")){
				sb.append("\n                     OS ");
				sb.append(getValue("v_lph"));
				sb.append(". ");
			}
		}else{
			if(isPresent("v_lph")){
				sb.append("OS ");
				sb.append(getValue("v_lph"));
				sb.append(". ");
			}
		}
		return sb.toString();
	}
	public String getVisionAssessmentIntermediateVision_sc(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("v_risc")){
			sb.append("OD ");
			sb.append(getValue("v_risc"));
			sb.append("; ");
		}
		if("eyeform3.2".equals(eyeform)){
			if(isPresent("v_lisc")){
				sb.append("\n                         OS ");
				sb.append(getValue("v_lisc"));
				sb.append("; ");
			}
			if(isPresent("v_isc")){
				sb.append("\n                         OU ");
				sb.append(getValue("v_isc"));
				sb.append(". ");
			}
		}else{
			if(isPresent("v_lisc")){
				sb.append("OS ");
				sb.append(getValue("v_lisc"));
				sb.append("; ");
			}
			if(isPresent("v_isc")){
				sb.append("OU ");
				sb.append(getValue("v_isc"));
				sb.append(". ");
			}
		}
		return sb.toString();
	}
	public String getVisionAssessmentIntermediateVision_cc(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("v_ricc")){
			sb.append("OD ");
			sb.append(getValue("v_ricc"));
			sb.append("; ");
		}
		if("eyeform3.2".equals(eyeform)){
			if(isPresent("v_licc")){
				sb.append("\n                         OS ");
				sb.append(getValue("v_licc"));
				sb.append("; ");
			}
			if(isPresent("v_icc")){
				sb.append("\n                         OU ");
				sb.append(getValue("v_icc"));
				sb.append(". ");
			}
		}else{
			if(isPresent("v_licc")){
				sb.append("OS ");
				sb.append(getValue("v_licc"));
				sb.append("; ");
			}
			if(isPresent("v_icc")){
				sb.append("OU ");
				sb.append(getValue("v_icc"));
				sb.append(". ");
			}
		}
		return sb.toString();
	}
	public String getVisionAssessmentNearVision_sc(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("v_rnsc")){
			sb.append("OD ");
			sb.append(getValue("v_rnsc"));
			sb.append("; ");
		}
		if("eyeform3.2".equals(eyeform)){
			if(isPresent("v_lnsc")){
				sb.append("\n                 OS ");
				sb.append(getValue("v_lnsc"));
				sb.append("; ");
			}
			if(isPresent("v_nsc")){
				sb.append("\n                 OU ");
				sb.append(getValue("v_nsc"));
				sb.append(". ");
			}
		}else{
			if(isPresent("v_lnsc")){
				sb.append("OS ");
				sb.append(getValue("v_lnsc"));
				sb.append("; ");
			}
			if(isPresent("v_nsc")){
				sb.append("OU ");
				sb.append(getValue("v_nsc"));
				sb.append(". ");
			}
		}
		return sb.toString();
	}
	public String getVisionAssessmentNearVision_cc(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("v_rncc")){
			sb.append("OD ");
			sb.append(getValue("v_rncc"));
			sb.append("; ");
		}
		if("eyeform3.2".equals(eyeform)){
			if(isPresent("v_lncc")){
				sb.append("\n                 OS ");
				sb.append(getValue("v_lncc"));
				sb.append("; ");
			}
			if(isPresent("v_ncc")){
				sb.append("\n                 OU ");
				sb.append(getValue("v_ncc"));
				sb.append(". ");
			}
		}else{
			if(isPresent("v_lncc")){
				sb.append("OS ");
				sb.append(getValue("v_lncc"));
				sb.append("; ");
			}
			if(isPresent("v_ncc")){
				sb.append("OU ");
				sb.append(getValue("v_ncc"));
				sb.append(". ");
			}
		}
		return sb.toString();
	}

	public String getVisionAssessmentVision(String distNear, String type) {
		StringBuilder sb = new StringBuilder();
		if(isPresent("od_"+type+"_"+ distNear))  {
			sb.append("OD ");
			sb.append(getValue("od_"+type+"_"+ distNear));
			//if(distNear.equals("near"))
			//	sb.append("+");
			sb.append("; ");
		}
		if(isPresent("os_"+type+"_"+ distNear)) {
			sb.append("OS ");
			sb.append(getValue("os_"+type+"_"+distNear));
			//if(distNear.equals("near"))
			//	sb.append("+");
			sb.append(". ");
		}
		return sb.toString();
	}
	
	public String getManifestDistance() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform) || ("eyeform3.1".equals(eyeform))) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("v_rds")) {		
				sb.append(getValue("v_rds"));
			}
			if(isPresent("v_rdc")) {
				sb.append(" " +getValue("v_rdc"));
			}
			if(isPresent("v_rdx")) {
				sb.append(" x " + getValue("v_rdx"));
			}
			if(isPresent("v_rdv")) {
				sb.append(" (" + getValue("v_rdv") + ")");
				//sb.append("; ");
			}
			if(sb.length() > 0){
				sb.append("; ");
				sb.insert(0, "OD ");
			}
			
			StringBuilder sb1 = new StringBuilder();
			if(isPresent("v_lds")) {
				sb1.append(getValue("v_lds"));
			}
			if(isPresent("v_ldc")) {
				sb1.append(" " +getValue("v_ldc"));
			}
			if(isPresent("v_ldx")) {
				sb1.append(" x" + getValue("v_ldx"));
			}
			if(isPresent("v_ldv")) {
				sb1.append(" (" + getValue("v_ldv") + ")");
			}
			if("eyeform3.2".equals(eyeform)){
				if(sb1.length() > 0){
					sb1.insert(0, "\n                  OS ");
					sb1.append("; ");
					sb.append(sb1);
				}
				if(isPresent("v_dv")){
					sb.append("\n                  OU ");
					sb.append(getValue("v_dv"));
					sb.append(". ");
				}
			}else{
				if(sb1.length() > 0){
					sb1.insert(0, "OS ");
					sb1.append("; ");
					sb.append(sb1);
				}
				if(isPresent("v_dv")){
					sb.append("OU ");
					sb.append(getValue("v_dv"));
					sb.append(". ");
				}
			}
		}else{
			if(isPresent("od_manifest_refraction_sph")) {
				sb.append("OD ");		
				sb.append(getValue("od_manifest_refraction_sph"));
				sb.append(getValue("od_manifest_refraction_cyl"));
				if(isPresent("od_manifest_refraction_axis")) {
					sb.append("x" + getValue("od_manifest_refraction_axis"));
				}
				sb.append(" (" + getValue("od_manifest_distance") + ")");
				sb.append("; ");
			}
			if(isPresent("os_manifest_refraction_sph")) {
				sb.append("OS ");
				sb.append(getValue("os_manifest_refraction_sph"));
				sb.append(getValue("os_manifest_refraction_cyl"));
				if(isPresent("os_manifest_refraction_axis")) {
					sb.append("x" + getValue("os_manifest_refraction_axis"));
				}
				sb.append(" (" + getValue("os_manifest_distance") + ")");
				sb.append(".");
			}
		}
		return sb.toString();
	}
	
	public String getManifestNear() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("v_rns")) {					
				sb.append(getValue("v_rns"));
			}
			if(isPresent("v_rnc")) {
				sb.append(" " +getValue("v_rnc"));
			}
			if(isPresent("v_rnx")) {
				sb.append(" x " + getValue("v_rnx"));
			}
			if(isPresent("v_rnv")) {
				sb.append(" (" + getValue("v_rnv") + ")");
			}
			if(sb.length() > 0){
				sb.insert(0, "OD ");
				sb.append("; ");
			}
			
			StringBuilder sb1 = new StringBuilder();
			if(isPresent("v_lns")) {
				sb1.append(getValue("v_lns"));
			}
			if(isPresent("v_lnc")) {
				sb1.append(" " +getValue("v_lnc"));
			}
			if(isPresent("v_lnx")) {
				sb1.append(" x " + getValue("v_lnx"));
			}
			if(isPresent("v_lnv")) {
				sb1.append(" (" + getValue("v_lnv") + ")");
			}
			if("eyeform3.2".equals(eyeform)){
				if(sb1.length() > 0){
					sb1.insert(0, "\n              OS ");
					sb1.append("; ");
					sb.append(sb1);
				}
				if(isPresent("v_nv")){
					sb.append("\n              OU ");
					sb.append(getValue("v_nv"));
					sb.append(". ");
				}
			}else{
				if(sb1.length() > 0){
					sb1.insert(0, "OS ");
					sb1.append("; ");
					sb.append(sb1);
				}
				if(isPresent("v_nv")){
					sb.append("OU ");
					sb.append(getValue("v_nv"));
					sb.append(". ");
				}
			}
		}else{
			if(isPresent("od_manifest_refraction_add")) {
				sb.append("OD ");		
				sb.append("add ");
				sb.append(getValue("od_manifest_refraction_add"));
				sb.append(" ("+getValue("od_manifest_near")+")");
				sb.append("; ");
			}
			if(isPresent("os_manifest_refraction_add")) {
				sb.append("OS ");
				sb.append("add ");
				sb.append(getValue("os_manifest_refraction_add"));
				sb.append(" ("+getValue("os_manifest_near")+")");
				sb.append(".");
			}
		}
		return sb.toString();
	}
	
	//TODO: No ADD HERE?
	public String getCycloplegicRefraction() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("v_rcs")) {				
				sb.append(getValue("v_rcs"));
			}
			if(isPresent("v_rcc")) {
				sb.append(" " +getValue("v_rcc"));
			}
			if(isPresent("v_rcx")) {
				sb.append(" x " + getValue("v_rcx"));
			}
			if(isPresent("v_rcv")) {
				sb.append(" (" + getValue("v_rcv") + ")");
			}
			if(sb.length() > 0){
				sb.insert(0, "OD ");
				sb.append("; ");
			}
			
			StringBuilder sb1 = new StringBuilder();
			if(isPresent("v_lcs")) {
				sb1.append(getValue("v_lcs"));
			}
			if(isPresent("v_lcc")) {
				sb1.append(" " +getValue("v_lcc"));
			}
			if(isPresent("v_lcx")) {
				sb1.append(" x" + getValue("v_lcx"));
			}
			if(isPresent("v_lcv")) {
				sb1.append(" (" + getValue("v_lcv") + ")");
			}
			if("eyeform3.2".equals(eyeform)){
				if(sb1.length() > 0){
					sb1.insert(0, "\n                       OS ");
					sb1.append(". ");
					sb.append(sb1);
				}
			}else{
				if(sb1.length() > 0){
					sb1.insert(0, "OS ");
					sb1.append(". ");
					sb.append(sb1);
				}
			}
		}else{
			if(isPresent("od_cycloplegic_refraction_sph")) {
				sb.append("OD ");		
				sb.append(getValue("od_cycloplegic_refraction_sph"));
				sb.append(getValue("od_cycloplegic_refraction_cyl"));
				if(isPresent("od_cycloplegic_refraction_axis")) {
					sb.append("x" + getValue("od_cycloplegic_refraction_axis"));
				}
				if(isPresent("od_cycloplegic_distance")) {
					sb.append(" (" + getValue("od_cycloplegic_distance") + ")");
				}
				sb.append("; ");
			}
			if(isPresent("os_cycloplegic_refraction_sph")) {
				sb.append("OS ");
				sb.append(getValue("os_cycloplegic_refraction_sph"));
				sb.append(getValue("os_cycloplegic_refraction_cyl"));
				if(isPresent("os_cycloplegic_refraction_axis")) {
					sb.append("x" + getValue("os_cycloplegic_refraction_axis"));
				}
				if(isPresent("os_cycloplegic_distance")) {
					sb.append(" (" + getValue("os_cycloplegic_distance") + ")");
				}
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getFlytest(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("v_fly")){
			sb.append(getValue("v_fly"));
			sb.append(". ");
		}
		return sb.toString();
	}
	public String getStereo_acuity(){
		StringBuilder sb = new StringBuilder();	
		if(isPresent("v_stereo")){
			sb.append(getValue("v_stereo"));
			sb.append(". ");
		}
		return sb.toString();
	}
	
	public String getNCT() {
		StringBuilder sb = new StringBuilder();		
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			Date d1 = null;
			Date d2 = null;
			if(isPresent("iop_rn")){
				sb.append("OD ");
				sb.append(getValue("iop_rn"));
				sb.append("; ");
				d1 = mmap.get("iop_rn").getDateObserved();
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("iop_ln")){
					sb.append("\n    OS ");
					sb.append(getValue("iop_ln"));
					sb.append("; ");
					d2 = mmap.get("iop_ln").getDateObserved();
				}
			}else{
				if(isPresent("iop_ln")){
					sb.append("OS ");
					sb.append(getValue("iop_ln"));
					sb.append("; ");
					d2 = mmap.get("iop_ln").getDateObserved();
				}
			}
			Date d = d2;
			if((d1 != null) &&(d2 != null)){
				if(d1.after(d2))
					d=d1;
			}
			if((d1 != null) &&(d2 == null)){
				d=d1;
			}
			if((d1 == null) &&(d2 != null)){
				d=d2;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			if(d != null){
				sb.append("(" + sdf.format(d)  + ")");
				sb.append(". ");
			}		
		}else{
			if(isPresent("od_iop_nct") && isPresent("os_iop_nct")) {
				sb.append("OD ");		
				sb.append(getValue("od_iop_nct"));
				
				sb.append("; ");
				sb.append("OS ");
				sb.append(getValue("os_iop_nct"));
				sb.append(" ");
				Date d1 = mmap.get("od_iop_nct").getDateObserved();
				Date d2 = mmap.get("os_iop_nct").getDateObserved();
				Date d = d2;
				if(d1.after(d2))
					d=d1;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				sb.append("[" + sdf.format(d)  + "]");
				sb.append(". ");
			} else if (isPresent("od_iop_nct") && !isPresent("os_iop_nct")) {
				sb.append("OD ");		
				sb.append(getValue("od_iop_nct"));
				sb.append(" ");
				Date d = mmap.get("od_iop_nct").getDateObserved();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				sb.append("[" + sdf.format(d)  + "]");
				sb.append(".");
				
			} else if (!isPresent("od_iop_nct") && isPresent("os_iop_nct")) {
				sb.append("OS ");
				sb.append(getValue("os_iop_nct"));
				sb.append(" ");			
				Date d = mmap.get("os_iop_nct").getDateObserved();			
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				sb.append("[" + sdf.format(d)  + "]");
				sb.append(".");
			} 
		}
		return sb.toString();
	}
	
	public String getApplanation() {		
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			Date d1 = null;
			Date d2 = null;
			if(isPresent("iop_ra")){
				sb.append("OD ");
				sb.append(getValue("iop_ra"));
				sb.append("; ");
				d1 = mmap.get("iop_ra").getDateObserved();
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("iop_la")){
					sb.append("\n            OS ");
					sb.append(getValue("iop_la"));
					sb.append("; ");
					d2 = mmap.get("iop_la").getDateObserved();
				}
			}else{
				if(isPresent("iop_la")){
					sb.append("OS ");
					sb.append(getValue("iop_la"));
					sb.append("; ");
					d2 = mmap.get("iop_la").getDateObserved();
				}
			}
			Date d = d2;
			if((d1 != null) &&(d2 != null)){
				if(d1.after(d2))
					d=d1;
			}
			if((d1 != null) &&(d2 == null)){
				d=d1;
			}
			if((d1 == null) &&(d2 != null)){
				d=d2;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			if(d != null){
				sb.append("(" + sdf.format(d)  + ")");
				sb.append(".");
			}
		}else{
			if(isPresent("od_iop_applanation") && isPresent("os_iop_applanation")) {
				sb.append("OD ");		
				sb.append(getValue("od_iop_applanation"));
				
				sb.append("; ");
				sb.append("OS ");
				sb.append(getValue("os_iop_applanation"));
				sb.append(" ");
				Date d1 = mmap.get("od_iop_applanation").getDateObserved();
				Date d2 = mmap.get("os_iop_applanation").getDateObserved();
				Date d = d2;
				if(d1.after(d2))
					d=d1;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				sb.append("[" + sdf.format(d)  + "]");
				sb.append(".");
			} else if (isPresent("od_iop_applanation") && !isPresent("os_iop_applanation")) {
				sb.append("OD ");		
				sb.append(getValue("od_iop_applanation"));			
				sb.append(" ");
				Date d = mmap.get("od_iop_applanation").getDateObserved();			
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				sb.append("[" + sdf.format(d)  + "]");
				sb.append(".");
			} else if (!isPresent("od_iop_applanation") && isPresent("os_iop_applanation")) {			
				sb.append("OS ");
				sb.append(getValue("os_iop_applanation"));
				sb.append(" ");			
				Date d = mmap.get("os_iop_applanation").getDateObserved();			
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				sb.append("[" + sdf.format(d)  + "]");
				sb.append(".");
			} 	
		}
		return sb.toString();
	}
	
	public String getCCT() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("cct_r")) {
				sb.append("OD ");		
				sb.append(getValue("cct_r"));
				sb.append(" microns");
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("cct_l")) {
					sb.append("\n                          OS ");
					sb.append(getValue("cct_l"));
					sb.append(" microns");
					sb.append(".");
				}
			}else{
				if(isPresent("cct_l")) {
					sb.append("OS ");
					sb.append(getValue("cct_l"));
					sb.append(" microns");
					sb.append(".");
				}
			}
		}else{
			if(isPresent("od_cct")) {
				sb.append("OD ");		
				sb.append(getValue("od_cct"));
				sb.append(" microns");
				sb.append("; ");
			}
			if(isPresent("os_cct")) {
				sb.append("OS ");
				sb.append(getValue("os_cct"));
				sb.append(" microns");
				sb.append(".");
			}
		}
		return sb.toString();
	}	
	
	public String getDominance(){
		StringBuilder sb = new StringBuilder();	
		if(isPresent("ref_rdom")){
			sb.append("OD ");		
			sb.append(getValue("ref_rdom"));		
			sb.append("; ");
		}
		if("eyeform3.2".equals(eyeform)){
			if(isPresent("ref_ldom")){
				sb.append("\n          OS ");		
				sb.append(getValue("ref_ldom"));		
				sb.append(". ");
			}
		}else{
			if(isPresent("ref_ldom")){
				sb.append("OS ");		
				sb.append(getValue("ref_ldom"));		
				sb.append(". ");
			}
		}
		return sb.toString();
	}
	
	public String getMesopicPupilSize(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("ref_rpdim")){
			sb.append("OD ");		
			sb.append(getValue("ref_rpdim"));		
			sb.append("; ");
		}
		if("eyeform3.2".equals(eyeform)){
			if(isPresent("ref_lpdim")){
				sb.append("\n                   OS ");		
				sb.append(getValue("ref_lpdim"));		
				sb.append(". ");
			}
		}else{
			if(isPresent("ref_lpdim")){
				sb.append("OS ");		
				sb.append(getValue("ref_lpdim"));		
				sb.append(". ");
			}
		}
		return sb.toString();
	}
	
	public String getAngleKappa(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("ref_rkappa")){
			sb.append("OD ");		
			sb.append(getValue("ref_rkappa"));		
			sb.append("; ");
		}
		if("eyeform3.2".equals(eyeform)){
			if(isPresent("ref_lkappa")){
				sb.append("\n            OS ");		
				sb.append(getValue("ref_lkappa"));		
				sb.append(". ");
			}
		}else{
			if(isPresent("ref_lkappa")){
				sb.append("OS ");		
				sb.append(getValue("ref_lkappa"));		
				sb.append(". ");
			}
		}
		return sb.toString();
	}
	
	public String getColourVision() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("o_rcolour")) {
				sb.append("OD ");		
				sb.append(getValue("o_rcolour"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("o_lcolour")) {
					sb.append("\n              OS ");
					sb.append(getValue("o_lcolour"));	
					sb.append(". ");
				}
			}else{
				if(isPresent("o_lcolour")) {
					sb.append("OS ");
					sb.append(getValue("o_lcolour"));	
					sb.append(". ");
				}
			}
		}else{
			if(isPresent("od_color_vision")) {
				sb.append("OD ");		
				sb.append(getValue("od_color_vision"));		
				sb.append("; ");
			}
			if(isPresent("os_color_vision")) {
				sb.append("OS ");
				sb.append(getValue("os_color_vision"));	
				sb.append(".");
			}
		}
		return sb.toString();
	}
	
	public String getPupil() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("o_rpupil")) {
				sb.append("OD ");		
				sb.append(getValue("o_rpupil"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("o_lpupil")) {
					sb.append("\n      OS ");
					sb.append(getValue("o_lpupil"));	
					sb.append(". ");
				}
			}else{
				if(isPresent("o_lpupil")) {
					sb.append("OS ");
					sb.append(getValue("o_lpupil"));	
					sb.append(". ");
				}
			}
		}else{
			if(isPresent("od_pupil")) {
				sb.append("OD ");		
				sb.append(getValue("od_pupil"));		
				sb.append("; ");
			}
			if(isPresent("os_pupil")) {
				sb.append("OS ");
				sb.append(getValue("os_pupil"));	
				sb.append(".");
			}
		}
		return sb.toString();
	}
	
	public String getAmslerGrid() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("o_ramsler")) {
				sb.append("OD ");		
				sb.append(getValue("o_ramsler"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("o_lamsler")) {
					sb.append("\n            OS ");
					sb.append(getValue("o_lamsler"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("o_lamsler")) {
					sb.append("OS ");
					sb.append(getValue("o_lamsler"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_amsler_grid")) {
				sb.append("OD ");		
				sb.append(getValue("od_amsler_grid"));		
				sb.append("; ");
			}
			if(isPresent("os_amsler_grid")) {
				sb.append("OS ");
				sb.append(getValue("os_amsler_grid"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getPAM() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("o_rpam")) {
				sb.append("OD ");		
				sb.append(getValue("o_rpam"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("o_lpam")) {
					sb.append("\n                       OS ");
					sb.append(getValue("o_lpam"));	
					sb.append(". ");
				}
			}else{
				if(isPresent("o_lpam")) {
					sb.append("OS ");
					sb.append(getValue("o_lpam"));	
					sb.append(". ");
				}
			}
		}else{
			if(isPresent("od_pam")) {
				sb.append("OD ");		
				sb.append(getValue("od_pam"));		
				sb.append("; ");
			}
			if(isPresent("os_pam")) {
				sb.append("OS ");
				sb.append(getValue("os_pam"));	
				sb.append(".");
			}
		}
		return sb.toString();
	}
	
	public String getConfrontation() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("o_rconf")) {
				sb.append("OD ");		
				sb.append(getValue("o_rconf"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("o_lconf")) {
					sb.append("\n                     OS ");
					sb.append(getValue("o_lconf"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("o_lconf")) {
					sb.append("OS ");
					sb.append(getValue("o_lconf"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_confrontation")) {
				sb.append("OD ");		
				sb.append(getValue("od_confrontation"));		
				sb.append("; ");
			}
			if(isPresent("os_confrontation")) {
				sb.append("OS ");
				sb.append(getValue("os_confrontation"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getMaddoxrod(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("o_mad")) {
			sb.append(getValue("o_mad"));	
			sb.append(". ");	
		}
		return sb.toString();
	}
	public String getBagolinitest(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("o_bag")) {
			sb.append(getValue("o_bag"));	
			sb.append(". ");	
		}
		return sb.toString();
	}
	public String getW4dD(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("o_w4dd")) {
			sb.append(getValue("o_w4dd"));	
			sb.append(". ");	
		}
		return sb.toString();
	}
	
	public String getW4dN(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("o_w4dn")) {
			sb.append(getValue("o_w4dn"));	
			sb.append(". ");	
		}
		return sb.toString();
	}
	
	public String[] getDuction1(){
		String str[] = new String[21];
		if(isPresent("duc_rur")) {
			str[0] = getValue("duc_rur");
		}else{
			str[0] = "";
		}
		if(isPresent("duc_rul")) {
			str[1] = getValue("duc_rul");
		}else{
			str[1] = "";
		}
		if(isPresent("duc_rr")) {
			str[2] = getValue("duc_rr");
		}else{
			str[2] = "";
		}
		if(isPresent("duc_rl")) {
			str[3] = getValue("duc_rl");
		}else{
			str[3] = "";
		}
		if(isPresent("duc_rdr")) {
			str[4] = getValue("duc_rdr");
		}else{
			str[4] = "";
		}
		if(isPresent("duc_rdl")) {
			str[5] = getValue("duc_rdl");
		}else{
			str[5] = "";
		}
		
		if(isPresent("duc_lur")) {
			str[6] = getValue("duc_lur");
		}else{
			str[6] = "";
		}
		if(isPresent("duc_lul")) {
			str[7] = getValue("duc_lul");
		}else{
			str[7] = "";
		}
		if(isPresent("duc_lr")) {
			str[8] = getValue("duc_lr");
		}else{
			str[8] = "";
		}
		if(isPresent("duc_ll")) {
			str[9] = getValue("duc_ll");
		}else{
			str[9] = "";
		}
		if(isPresent("duc_ldr")) {
			str[10] = getValue("duc_ldr");
		}else{
			str[10] = "";
		}
		if(isPresent("duc_ldl")) {
			str[11] = getValue("duc_ldl");
		}else{
			str[11] = "";
		}
		
		if(isPresent("dip_ur")) {
			str[12] = getValue("dip_ur");
		}else{
			str[12] = "";
		}
		if(isPresent("dip_u")) {
			str[13] = getValue("dip_u");
		}else{
			str[13] = "";
		}
		if(isPresent("dip_ul")) {
			str[14] = getValue("dip_ul");
		}else{
			str[14] = "";
		}
		if(isPresent("dip_r")) {
			str[15] = getValue("dip_r");
		}else{
			str[15] = "";
		}
		if(isPresent("dip_p")) {
			str[16] = getValue("dip_p");
		}else{
			str[16] = "";
		}
		if(isPresent("dip_l")) {
			str[17] = getValue("dip_l");
		}else{
			str[17] = "";
		}
		if(isPresent("dip_dr")) {
			str[18] = getValue("dip_dr");
		}else{
			str[18] = "";
		}
		if(isPresent("dip_d")) {
			str[19] = getValue("dip_d");
		}else{
			str[19] = "";
		}
		if(isPresent("dip_dl")) {
			str[20] = getValue("dip_dl");
		}else{
			str[20] = "";
		}
		return str;
	}
	
	public String getDuction(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("duc_rur")) {
			sb.append(getValue("duc_rur"));
		}
		if(isPresent("duc_rul")) {
			sb.append(" " +getValue("duc_rul"));	
		}
		if(isPresent("duc_rr")) {
			sb.append(" " +getValue("duc_rr"));	
		}
		if(isPresent("duc_rl")) {
			sb.append(" " +getValue("duc_rl"));
		}
		if(isPresent("duc_rdr")) {
			sb.append(" " +getValue("duc_rdr"));
		}
		if(isPresent("duc_rdl")) {
			sb.append(" " +getValue("duc_rdl"));	
		}
		if(sb.length() > 0){
			sb.insert(0, "OD ");
			sb.append(". ");	
		}
		
		StringBuilder sb1 = new StringBuilder();
		if(isPresent("duc_lur")) {
			sb1.append(getValue("duc_lur"));
		}
		if(isPresent("duc_lul")) {
			sb1.append(" " +getValue("duc_lul"));
		}
		if(isPresent("duc_lr")) {
			sb1.append(" " +getValue("duc_lr"));
		}
		if(isPresent("duc_ll")) {
			sb1.append(" " +getValue("duc_ll"));
		}
		if(isPresent("duc_ldr")) {
			sb1.append(" " +getValue("duc_ldr"));
		}
		if(isPresent("duc_ldl")) {
			sb1.append(" " +getValue("duc_ldl"));
		}
		if("eyeform3.2".equals(eyeform)){
			if(sb1.length() > 0){
				sb1.insert(0, "\nOS ");
				sb1.append(". ");	
				sb.append(sb1);
			}
		}else{
			if(sb1.length() > 0){
				sb1.insert(0, "OS ");
				sb1.append(". ");	
				sb.append(sb1);
			}
		}
		
		StringBuilder sb2 = new StringBuilder();
		if(isPresent("dip_ur")) {
			sb2.append(getValue("dip_ur"));
		}
		if(isPresent("dip_u")) {
			sb2.append(" " +getValue("dip_u"));
		}
		if(isPresent("dip_ul")) {
			sb2.append(" " +getValue("dip_ul"));	
		}
		if(isPresent("dip_r")) {
			sb2.append(" " +getValue("dip_r"));
		}
		if(isPresent("dip_p")) {
			sb2.append(" " +getValue("dip_p"));
		}
		if(isPresent("dip_l")) {
			sb2.append(" " +getValue("dip_l"));	
		}
		if(isPresent("dip_dr")) {
			sb2.append(" " +getValue("dip_dr"));
		}
		if(isPresent("dip_d")) {
			sb2.append(" " +getValue("dip_d"));
		}
		if(isPresent("dip_dl")) {
			sb2.append(" " +getValue("dip_dl"));
		}
		if("eyeform3.2".equals(eyeform)){
			if(sb2.length() > 0){
				sb2.insert(0, "\nOU ");
				sb2.append(". ");	
				sb.append(sb2);
			}
		}else{
			if(sb2.length() > 0){
				sb2.insert(0, "OU ");
				sb2.append(". ");	
				sb.append(sb2);
			}
		}
		return sb.toString();
	}
	
	public String getPrimarygaze(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("dev_p")) {
			sb.append(getValue("dev_p"));
			sb.append(". ");	
		}
		return sb.toString();
	}
	
	public String getUpgaze(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("dev_u")) {
			sb.append(getValue("dev_u"));
			sb.append(". ");	
		}
		return sb.toString();
	}
	
	public String getDowngaze(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("dev_d")) {
			sb.append(getValue("dev_d"));
			sb.append(". ");	
		}
		return sb.toString();
	}
	
	public String getRightgaze(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("dev_r")) {
			sb.append(getValue("dev_r"));
			sb.append(". ");	
		}
		return sb.toString();
	}
	
	public String getLeftgaze(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("dev_l")) {
			sb.append(getValue("dev_l"));
			sb.append(". ");	
		}
		return sb.toString();
	}
	
	public String getRighthead(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("dev_rt")) {
			sb.append(getValue("dev_rt"));
			sb.append(". ");	
		}
		return sb.toString();
	}
	
	public String getLefthead(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("dev_lt")) {
			sb.append(getValue("dev_lt"));
			sb.append(". ");	
		}
		return sb.toString();
	}
	
	public String getNear(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("dev_near")) {
			sb.append(getValue("dev_near"));
			sb.append(". ");	
		}
		return sb.toString();
	}
	
	public String getNearwith(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("dev_plus3")) {
			sb.append(getValue("dev_plus3"));
			sb.append(". ");	
		}
		return sb.toString();
	}
	
	public String getFardistance(){
		StringBuilder sb = new StringBuilder();
		if(isPresent("dev_far")) {
			sb.append(getValue("dev_far"));
			sb.append(". ");	
		}
		return sb.toString();
	}
	
	public String getEomStereo() {
		StringBuilder sb = new StringBuilder();	
		if(isPresent("EOM")) {
			sb.append(getValue("EOM"));				
			sb.append(". ");
		}
		return sb.toString();
	}	
	
	
	public String getCornea() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("a_rk")) {
				sb.append("OD ");		
				sb.append(getValue("a_rk"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("a_lk")) {
					sb.append("\n       OS ");
					sb.append(getValue("a_lk"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("a_lk")) {
					sb.append("OS ");
					sb.append(getValue("a_lk"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_cornea")) {
				sb.append("OD ");		
				sb.append(getValue("od_cornea"));		
				sb.append("; ");
			}
			if(isPresent("os_cornea")) {
				sb.append("OS ");
				sb.append(getValue("os_cornea"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getConjuctivaSclera() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("a_rconj")) {
				sb.append("OD ");		
				sb.append(getValue("a_rconj"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("a_lconj")) {
					sb.append("\n                   OS ");
					sb.append(getValue("a_lconj"));	
					sb.append(". ");
				}
			}else{
				if(isPresent("a_lconj")) {
					sb.append("OS ");
					sb.append(getValue("a_lconj"));	
					sb.append(". ");
				}
			}
		}else{
			if(isPresent("od_conjuctiva_sclera")) {
				sb.append("OD ");		
				sb.append(getValue("od_conjuctiva_sclera"));		
				sb.append("; ");
			}
			if(isPresent("os_conjuctiva_sclera")) {
				sb.append("OS ");
				sb.append(getValue("os_conjuctiva_sclera"));	
				sb.append(".");
			}
		}
		return sb.toString();
	}
	
	public String getAnteriorChamber() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("a_rac")) {
				sb.append("OD ");		
				sb.append(getValue("a_rac"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("a_lac")) {
					sb.append("\n                 OS ");
					sb.append(getValue("a_lac"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("a_lac")) {
					sb.append("OS ");
					sb.append(getValue("a_lac"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_anterior_chamber")) {
				sb.append("OD ");		
				sb.append(getValue("od_anterior_chamber"));		
				sb.append("; ");
			}
			if(isPresent("os_anterior_chamber")) {
				sb.append("OS ");
				sb.append(getValue("os_anterior_chamber"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getAngle() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("a_rangle_1") || isPresent("a_rangle_2") || isPresent("a_rangle_3") || isPresent("a_rangle_4") || isPresent("a_rangle_5")) {			
				sb.append("OD ");
				sb.append(getValue("a_rangle_3"));
				if(isPresent("a_rangle_1") || isPresent("a_rangle_2") || isPresent("a_rangle_4") || isPresent("a_rangle_5")) {
					sb.append(" (");
				}
				boolean flag=false;
				if(isPresent("a_rangle_1")) {
					sb.append("superior "+getValue("a_rangle_1"));
					if(!flag) flag=true;
				}
				if(isPresent("a_rangle_4")) {
					if(flag) {					
						sb.append(", ");
					}
					if(!flag) flag=true;
					sb.append("nasal "+getValue("a_rangle_4"));
				}
				if(isPresent("a_rangle_5")) {
					if(flag) {
						sb.append(", ");
					}
					if(!flag) flag=true;
					sb.append("inferior "+getValue("a_rangle_5"));
				}
				if(isPresent("a_rangle_2")) {
					if(flag) {
						sb.append(", ");
					}
					if(!flag) flag=true;
					sb.append("temporal " + getValue("a_rangle_2"));
				}				
				if(isPresent("a_rangle_1") || isPresent("a_rangle_2") || isPresent("a_rangle_4") || isPresent("a_rangle_5")) {
					sb.append(")");
				}
				sb.append("; ");
			}

			if(isPresent("a_langle_1") || isPresent("a_langle_2") || isPresent("a_langle_3") || isPresent("a_langle_4") || isPresent("a_langle_5")) {			
				if("eyeform3.2".equals(eyeform)){
					sb.append("\n      OS ");
				}else{
					sb.append("OS ");
				}
				sb.append(getValue("a_langle_3"));
				if(isPresent("a_langle_1") || isPresent("a_langle_2") || isPresent("a_langle_4") || isPresent("a_langle_5")) {
					sb.append(" (");
				}
				boolean flag=false;
				if(isPresent("a_langle_1")) {
					sb.append("superior "+getValue("a_langle_1"));
					if(!flag) flag=true;
				}
				if(isPresent("a_langle_4")) {
					if(flag) {
						sb.append(", ");
					}
					if(!flag) flag=true;
					sb.append("nasal " + getValue("a_langle_4"));
				}
				if(isPresent("a_langle_5")) {
					if(flag) {
						sb.append(", ");
					}
					if(!flag) flag=true;
					sb.append("inferior "+getValue("a_langle_5"));
				}
				if(isPresent("a_langle_2")) {
					if(flag) {					
						sb.append(", ");
					}
					if(!flag) flag=true;
					sb.append("temporal"+getValue("a_langle_2"));
				}
				
				if(isPresent("a_langle_1") || isPresent("a_langle_2") || isPresent("a_langle_4") || isPresent("a_langle_5")) {
					sb.append(")");
				}
				sb.append("; ");
			}	
				
		}else{
			if(isPresent("od_angle_middle1") || isPresent("od_angle_up") || isPresent("od_angle_middle2") || isPresent("od_angle_down") || isPresent("od_angle_middle0")) {			
				sb.append("OD ");		
				sb.append(getValue("od_angle_middle1"));
				if(isPresent("od_angle_up") || isPresent("od_angle_middle2") || isPresent("od_angle_down") || isPresent("od_angle_middle0")) {
					sb.append(" (");
				}
				boolean flag=false;
				if(isPresent("od_angle_up")) {
					sb.append("superior "+getValue("od_angle_up"));
					if(!flag) flag=true;
				}
				if(isPresent("od_angle_middle2")) {
					if(flag) {					
						sb.append(", ");
					}
					if(!flag) flag=true;
					sb.append("nasal "+getValue("od_angle_middle2"));
				}
				if(isPresent("od_angle_down")) {
					if(flag) {
						sb.append(", ");
					}
					if(!flag) flag=true;
					sb.append("inferior "+getValue("od_angle_down"));
				}
				if(isPresent("od_angle_middle0")) {
					if(flag) {
						sb.append(", ");
					}
					if(!flag) flag=true;
					sb.append("temporal " + getValue("od_angle_middle0"));
				}
				
				if(isPresent("od_angle_up") || isPresent("od_angle_middle2") || isPresent("od_angle_down") || isPresent("od_angle_middle0")) {
					sb.append(")");
				}
				sb.append("; ");
			}
						
			if(isPresent("os_angle_middle1") || isPresent("os_angle_up") || isPresent("os_angle_middle2") || isPresent("os_angle_down") || isPresent("os_angle_middle0")) {			
				sb.append("OS ");		
				sb.append(getValue("os_angle_middle1"));
				
				if(isPresent("os_angle_up") || isPresent("os_angle_middle2") || isPresent("os_angle_down") || isPresent("os_angle_middle0")) {
					sb.append(" (");
				}
				boolean flag=false;
				if(isPresent("os_angle_up")) {
					sb.append("superior "+getValue("os_angle_up"));
					if(!flag) flag=true;
				}
				if(isPresent("os_angle_middle0")) {
					if(flag) {					
						sb.append(", ");
					}
					if(!flag) flag=true;
					sb.append("nasal "+getValue("os_angle_middle0"));
				}
				if(isPresent("os_angle_down")) {
					if(flag) {
						sb.append(", ");
					}
					if(!flag) flag=true;
					sb.append("inferior "+getValue("os_angle_down"));
				}
				if(isPresent("os_angle_middle2")) {
					if(flag) {
						sb.append(", ");
					}
					if(!flag) flag=true;
					sb.append("temporal " + getValue("os_angle_middle2"));
				}
				
				if(isPresent("os_angle_up") || isPresent("os_angle_middle2") || isPresent("os_angle_down") || isPresent("os_angle_middle0")) {
					sb.append(")");
				}
				sb.append("; ");
			}	
		}
				
		return sb.toString();
	}

	public String getIris() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("a_riris")) {
				sb.append("OD ");		
				sb.append(getValue("a_riris"));		
				sb.append("; ");
			}
			if(isPresent("a_liris")) {
				if("eyeform3.2".equals(eyeform)){
					sb.append("\n     OS ");
				}else{
					sb.append("OS ");
				}
				sb.append(getValue("a_liris"));	
				sb.append(". ");	
			}
		}else{
			if(isPresent("od_iris")) {
				sb.append("OD ");		
				sb.append(getValue("od_iris"));		
				sb.append("; ");
			}
			if(isPresent("os_iris")) {
				sb.append("OS ");
				sb.append(getValue("os_iris"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getLens() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("a_rlens")) {
				sb.append("OD ");		
				sb.append(getValue("a_rlens"));		
				sb.append("; ");
			}
			if(isPresent("a_llens")) {
				if("eyeform3.2".equals(eyeform)){
					sb.append("\n     OS ");
				}else{
					sb.append("OS ");
				}
				sb.append(getValue("a_llens"));	
				sb.append(". ");
			}
		}else{
			if(isPresent("od_lens")) {
				sb.append("OD ");		
				sb.append(getValue("od_lens"));		
				sb.append("; ");
			}
			if(isPresent("os_lens")) {
				sb.append("OS ");
				sb.append(getValue("os_lens"));	
				sb.append(".");
			}
		}
		return sb.toString();
	}
	
	public String getDisc() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("p_rdisc")) {
				sb.append("OD ");		
				sb.append(getValue("p_rdisc") );		
				sb.append("; ");
			}
			if(isPresent("p_ldisc")) {
				if("eyeform3.2".equals(eyeform)){
					sb.append("\n           OS ");
				}else{
					sb.append("OS ");
				}
				sb.append(getValue("p_ldisc"));	
				sb.append(". ");	
			}
		}else{
			if(isPresent("od_disc")) {
				sb.append("OD ");		
				sb.append(getValue("od_disc"));		
				sb.append("; ");
			}
			if(isPresent("os_disc")) {
				sb.append("OS ");
				sb.append(getValue("os_disc"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getCdRatio() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("p_rcd")) {
				sb.append("OD ");		
				sb.append(getValue("p_rcd") );		
				sb.append("; ");
			}
			if(isPresent("p_lcd")) {
				if("eyeform3.2".equals(eyeform)){
					sb.append("\n          OS ");
				}else{
					sb.append("OS ");
				}
				sb.append(getValue("p_lcd"));	
				sb.append(". ");	
			}
		}else{
			if(isPresent("od_cd_ratio_horizontal")) {
				sb.append("OD ");		
				sb.append(getValue("od_cd_ratio_horizontal"));		
				sb.append("; ");
			}
			if(isPresent("os_cd_ratio_horizontal")) {
				sb.append("OS ");
				sb.append(getValue("os_cd_ratio_horizontal"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getMacula() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("p_rmac")) {
				sb.append("OD ");		
				sb.append(getValue("p_rmac"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("p_lmac")) {
					sb.append("\n       OS ");
					sb.append(getValue("p_lmac"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("p_lmac")) {
					sb.append("OS ");
					sb.append(getValue("p_lmac"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_macula")) {
				sb.append("OD ");		
				sb.append(getValue("od_macula"));		
				sb.append("; ");
			}
			if(isPresent("os_macula")) {
				sb.append("OS ");
				sb.append(getValue("os_macula"));	
				sb.append(". ");	
			}
		}
		return sb.toString();
	}
	
	public String getRetina() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("p_rret")) {
				sb.append("OD ");		
				sb.append(getValue("p_rret"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("p_lret")) {
					sb.append("\nOS ");
					sb.append(getValue("p_lret"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("p_lret")) {
					sb.append("OS ");
					sb.append(getValue("p_lret"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_retina")) {
				sb.append("OD ");		
				sb.append(getValue("od_retina"));		
				sb.append("; ");
			}
			if(isPresent("os_retina")) {
				sb.append("OS ");
				sb.append(getValue("os_retina"));	
				sb.append(". ");	
			}
		}
		return sb.toString();
	}
	
	public String getVitreous() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("p_rvit")) {
				sb.append("OD ");		
				sb.append(getValue("p_rvit"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("p_lvit")) {
					sb.append("\n         OS ");
					sb.append(getValue("p_lvit"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("p_lvit")) {
					sb.append("OS ");
					sb.append(getValue("p_lvit"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_vitreous")) {
				sb.append("OD ");		
				sb.append(getValue("od_vitreous"));		
				sb.append("; ");
			}
			if(isPresent("os_vitreous")) {
				sb.append("OS ");
				sb.append(getValue("os_vitreous"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getFace() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("ext_rface")) {
				sb.append("Right side ");		
				sb.append(getValue("ext_rface"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("ext_lface")) {
					sb.append("\n     Left side ");
					sb.append(getValue("ext_lface"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("ext_lface")) {
					sb.append(" Left side ");
					sb.append(getValue("ext_lface"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_face")) {
				sb.append("OD ");		
				sb.append(getValue("od_face"));		
				sb.append("; ");
			}
			if(isPresent("os_face")) {
				sb.append("OS ");
				sb.append(getValue("os_face"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getUpperLid() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("ext_rul")) {
				sb.append("OD ");		
				sb.append(getValue("ext_rul"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("ext_lul")) {
					sb.append("\n          OS ");
					sb.append(getValue("ext_lul"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("ext_lul")) {
					sb.append("OS ");
					sb.append(getValue("ext_lul"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_upper_lid")) {
				sb.append("OD ");		
				sb.append(getValue("od_upper_lid"));		
				sb.append("; ");
			}
			if(isPresent("os_upper_lid")) {
				sb.append("OS ");
				sb.append(getValue("os_upper_lid"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getLowerLid() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("ext_rll")) {
				sb.append("OD ");		
				sb.append(getValue("ext_rll"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("ext_lll")) {
					sb.append("\n          OS ");
					sb.append(getValue("ext_lll"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("ext_lll")) {
					sb.append("OS ");
					sb.append(getValue("ext_lll"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_lower_lid")) {
				sb.append("OD ");		
				sb.append(getValue("od_lower_lid"));		
				sb.append("; ");
			}
			if(isPresent("os_lower_lid")) {
				sb.append("OS ");
				sb.append(getValue("os_lower_lid"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getPunctum() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("ext_rpunc")) {
				sb.append("OD ");		
				sb.append(getValue("ext_rpunc"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("ext_lpunc")) {
					sb.append("\n        OS ");
					sb.append(getValue("ext_lpunc"));	
					sb.append(".");	
				}
			}else{
				if(isPresent("ext_lpunc")) {
					sb.append("OS ");
					sb.append(getValue("ext_lpunc"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_punctum")) {
				sb.append("OD ");		
				sb.append(getValue("od_punctum"));		
				sb.append("; ");
			}
			if(isPresent("os_punctum")) {
				sb.append("OS ");
				sb.append(getValue("os_punctum"));	
				sb.append(". ");	
			}
		}
		return sb.toString();
	}
	
	public String getLacrimalLake() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("ext_rlake")) {
				sb.append("OD ");		
				sb.append(getValue("ext_rlake"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("ext_llake")) {
					sb.append("\n              OS ");
					sb.append(getValue("ext_llake"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("ext_llake")) {
					sb.append("OS ");
					sb.append(getValue("ext_llake"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_lacrimal_lake")) {
				sb.append("OD ");		
				sb.append(getValue("od_lacrimal_lake"));		
				sb.append("; ");
			}
			if(isPresent("os_lacrimal_lake")) {
				sb.append("OS ");
				sb.append(getValue("os_lacrimal_lake"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getLacrimalIrrigation() {
		StringBuilder sb = new StringBuilder();		
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("ext_rirrig")) {
				sb.append("OD ");		
				sb.append(getValue("ext_rirrig"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("ext_lirrig")) {
					sb.append("\n                    OS ");
					sb.append(getValue("ext_lirrig"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("ext_lirrig")) {
					sb.append("OS ");
					sb.append(getValue("ext_lirrig"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_lacrimal_irrigation")) {
				sb.append("OD ");		
				sb.append(getValue("od_lacrimal_irrigation"));		
				sb.append("; ");
			}
			if(isPresent("os_lacrimal_irrigation")) {
				sb.append("OS ");
				sb.append(getValue("os_lacrimal_irrigation"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getNLD() {
		StringBuilder sb = new StringBuilder();		
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("ext_rnld")) {
				sb.append("OD ");		
				sb.append(getValue("ext_rnld"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("ext_lnld")) {
					sb.append("\n                  OS ");
					sb.append(getValue("ext_lnld"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("ext_lnld")) {
					sb.append("OS ");
					sb.append(getValue("ext_lnld"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_nld")) {
				sb.append("OD ");		
				sb.append(getValue("od_nld"));		
				sb.append("; ");
			}
			if(isPresent("os_nld")) {
				sb.append("OS ");
				sb.append(getValue("os_nld"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getDyeDisappearance() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("ext_rdye")) {
				sb.append("OD ");		
				sb.append(getValue("ext_rdye"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("ext_ldye")) {
					sb.append("\n                  OS ");
					sb.append(getValue("ext_ldye"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("ext_ldye")) {
					sb.append("OS ");
					sb.append(getValue("ext_ldye"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_dye_disappearance")) {
				sb.append("OD ");		
				sb.append(getValue("od_dye_disappearance"));		
				sb.append("; ");
			}
			if(isPresent("os_dye_disappearance")) {
				sb.append("OS ");
				sb.append(getValue("os_dye_disappearance"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getMarginReflexDistance() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("lid_rmrd")) {
				sb.append("OD ");		
				sb.append(getValue("lid_rmrd"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("lid_lmrd")) {
					sb.append("\n                       OS ");
					sb.append(getValue("lid_lmrd"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("lid_lmrd")) {
					sb.append("OS ");
					sb.append(getValue("lid_lmrd"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_mrd")) {
				sb.append("OD ");		
				sb.append(getValue("od_mrd"));		
				sb.append("; ");
			}
			if(isPresent("os_mrd")) {
				sb.append("OS ");
				sb.append(getValue("os_mrd"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getLevatorFunction() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("lid_rlev")) {
				sb.append("OD ");		
				sb.append(getValue("lid_rlev"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("lid_llev")) {
					sb.append("\n                 OS ");
					sb.append(getValue("lid_llev"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("lid_llev")) {
					sb.append("OS ");
					sb.append(getValue("lid_llev"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_levator_function")) {
				sb.append("OD ");		
				sb.append(getValue("od_levator_function"));		
				sb.append("; ");
			}
			if(isPresent("os_levator_function")) {
				sb.append("OS ");
				sb.append(getValue("os_levator_function"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getInferiorScleralShow() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("lid_riss")) {
				sb.append("OD ");		
				sb.append(getValue("lid_riss"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("lid_liss")) {
					sb.append("\n                      OS ");
					sb.append(getValue("lid_liss"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("lid_liss")) {
					sb.append("OS ");
					sb.append(getValue("lid_liss"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_inferior_scleral_show")) {
				sb.append("OD ");		
				sb.append(getValue("od_inferior_scleral_show"));		
				sb.append("; ");
			}
			if(isPresent("os_inferior_scleral_show")) {
				sb.append("OS ");
				sb.append(getValue("os_inferior_scleral_show"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getCNVii() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("lid_rcn7")) {
				sb.append("OD ");		
				sb.append(getValue("lid_rcn7"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("lid_lcn7")) {
					sb.append("\n                           OS ");
					sb.append(getValue("lid_lcn7"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("lid_lcn7")) {
					sb.append("OS ");
					sb.append(getValue("lid_lcn7"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_cn_vii")) {
				sb.append("OD ");		
				sb.append(getValue("od_cn_vii"));		
				sb.append("; ");
			}
			if(isPresent("os_cn_vii")) {
				sb.append("OS ");
				sb.append(getValue("os_cn_vii"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getBlink() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("lid_rblink")) {
				sb.append("OD ");		
				sb.append(getValue("lid_rblink"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("lid_lblink")) {
					sb.append("\n             OS ");
					sb.append(getValue("lid_lblink"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("lid_lblink")) {
					sb.append("OS ");
					sb.append(getValue("lid_lblink"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_blink")) {
				sb.append("OD ");		
				sb.append(getValue("od_blink"));		
				sb.append("; ");
			}
			if(isPresent("os_blink")) {
				sb.append("OS ");
				sb.append(getValue("os_blink"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getBells() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("lid_rbell")) {
				sb.append("OD ");		
				sb.append(getValue("lid_rbell"));		
				sb.append("; ");
			}
			if("eyeform3.2".equals(eyeform)){
				if(isPresent("lid_lbell")) {
					sb.append("\n                  OS ");
					sb.append(getValue("lid_lbell"));	
					sb.append(". ");	
				}
			}else{
				if(isPresent("lid_lbell")) {
					sb.append("OS ");
					sb.append(getValue("lid_lbell"));	
					sb.append(". ");	
				}
			}
		}else{
			if(isPresent("od_bells")) {
				sb.append("OD ");		
				sb.append(getValue("od_bells"));		
				sb.append("; ");
			}
			if(isPresent("os_bells")) {
				sb.append("OS ");
				sb.append(getValue("os_bells"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getSchirmertest() {
		StringBuilder sb = new StringBuilder();
		if(isPresent("lid_rschirm")) {
			sb.append("OD ");		
			sb.append(getValue("lid_rschirm"));		
			sb.append("; ");
		}
		if(isPresent("lid_lschirm")) {
			if("eyeform3.2".equals(eyeform)){
				sb.append("\n              OS ");
			}else{
				sb.append("OS ");
			}
			sb.append(getValue("lid_lschirm"));	
			sb.append(". ");	
		}
		return sb.toString();
	}
	
	public String getLagophthalmos() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("lid_rlag")) {
				sb.append("OD ");		
				sb.append(getValue("lid_rlag"));		
				sb.append("; ");
			}
			if(isPresent("lid_llag")) {
				if("eyeform3.2".equals(eyeform)){
					sb.append("\n              OS ");
				}else{
					sb.append("OS ");
				}
				sb.append(getValue("lid_llag"));	
				sb.append(". ");	
			}
		}else{
			if(isPresent("od_lagophthalmos")) {
				sb.append("OD ");		
				sb.append(getValue("od_lagophthalmos"));		
				sb.append("; ");
			}
			if(isPresent("os_lagophthalmos")) {
				sb.append("OS ");
				sb.append(getValue("os_lagophthalmos"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getHertel() {
		StringBuilder sb = new StringBuilder();	
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("ext_rhertel")) {
				sb.append("OD ");		
				sb.append(getValue("ext_rhertel"));		
				sb.append("; ");
			}
			if(isPresent("ext_lhertel")) {
				if("eyeform3.2".equals(eyeform)){
					sb.append("\n       OS ");
				}else{
					sb.append("OS ");
				}
				sb.append(getValue("ext_lhertel"));	
				sb.append(". ");	
			}
		}else{
			if(isPresent("od_hertel")) {
				sb.append("OD ");		
				sb.append(getValue("od_hertel"));		
				sb.append("; ");
			}
			if(isPresent("os_hertel")) {
				sb.append("OS ");
				sb.append(getValue("os_hertel"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	public String getRetropulsion() {
		StringBuilder sb = new StringBuilder();
		if(("eyeform3".equals(eyeform)) || ("eyeform3.1".equals(eyeform)) || ("eyeform3.2".equals(eyeform))){
			if(isPresent("ext_rretro")) {
				sb.append("OD ");		
				sb.append(getValue("ext_rretro"));		
				sb.append("; ");
			}
			if(isPresent("ext_lretro")) {
				if("eyeform3.2".equals(eyeform)){
					sb.append("\n             OS ");
				}else{
					sb.append("OS ");
				}
				sb.append(getValue("ext_lretro"));	
				sb.append(". ");	
			}
		}else{
			if(isPresent("od_retropulsion")) {
				sb.append("OD ");		
				sb.append(getValue("od_retropulsion"));		
				sb.append("; ");
			}
			if(isPresent("os_retropulsion")) {
				sb.append("OS ");
				sb.append(getValue("os_retropulsion"));	
				sb.append(".");	
			}
		}
		return sb.toString();
	}
	
	
	
	
	private String getValue(String key) {
		if(mmap.get(key)!=null)
			return mmap.get(key).getDataField();
		return  "";
	}
	
	private boolean isPresent(String key) {
		if(mmap.get(key)!=null)
			return true;
		return false;
	}
	
	private boolean isNegative(String key) {
		if(mmap.get(key) != null && mmap.get(key).getDataField().startsWith("-"))
			return true;
		return false;
	}
}
