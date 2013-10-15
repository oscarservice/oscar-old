package com.oscar.middleware;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.oscarehr.common.model.AbstractModel;
@Entity
@Table(name = "zeiss_oru_result")
public class ZeissOruBean extends AbstractModel<Integer> implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 71271152936093326L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int pid;
	private String placer_order_number;
	private String instrument;
	private Date study_date;
	private Date content_date;
	private String doc_title;
	private String img_type;
	private String laterality;
	private String study_uid;
	private String instance_uid;
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public String getPlacer_order_number() {
		return placer_order_number;
	}
	public void setPlacer_order_number(String placer_order_number) {
		this.placer_order_number = placer_order_number;
	}
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	public Date getStudy_date() {
		return study_date;
	}
	public void setStudy_date(Date studyDate) {
		study_date = studyDate;
	}
	public Date getContent_date() {
		return content_date;
	}
	public void setContent_date(Date contentDate) {
		content_date = contentDate;
	}
	public String getDoc_title() {
		return doc_title;
	}
	public void setDoc_title(String docTitle) {
		doc_title = docTitle;
	}
	public String getImg_type() {
		return img_type;
	}
	public void setImg_type(String imgType) {
		img_type = imgType;
	}
	public String getLaterality() {
		return laterality;
	}
	public void setLaterality(String laterality) {
		this.laterality = laterality;
	}
	public String getStudy_uid() {
		return study_uid;
	}
	public void setStudy_uid(String studyUid) {
		study_uid = studyUid;
	}
	public String getInstance_uid() {
		return instance_uid;
	}
	public void setInstance_uid(String instanceUid) {
		instance_uid = instanceUid;
	}
	
	

}
