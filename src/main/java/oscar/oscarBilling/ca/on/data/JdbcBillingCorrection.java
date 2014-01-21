/**
 * Copyright (c) 2006-. OSCARservice, OpenSoft System. All Rights Reserved.
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
 */

package oscar.oscarBilling.ca.on.data;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class JdbcBillingCorrection {
	private static final Logger _logger = Logger.getLogger(JdbcBillingCorrection.class);
	BillingONDataHelp dbObj = new BillingONDataHelp();
	JdbcBillingLog dbLog = new JdbcBillingLog();

	public boolean updateBillingClaimHeader(BillingClaimHeader1Data ch1Obj) {
		boolean retval = false;
		String sql = "update billing_on_cheader1 set transc_id='" + ch1Obj.getTransc_id() + "'," + " rec_id='"
				+ ch1Obj.getRec_id() + "'," + " hin='" + ch1Obj.getHin() + "'," + " ver='" + ch1Obj.getVer() + "',"
				+ " dob='" + ch1Obj.getDob() + "'," + " pay_program='" + ch1Obj.getPay_program() + "'," + " payee='"
				+ ch1Obj.getPayee() + "'," + " ref_num='" + ch1Obj.getRef_num() + "'," + " facilty_num='"
				+ ch1Obj.getFacilty_num() + "'," + " admission_date='" + ch1Obj.getAdmission_date() + "',"
				+ " ref_lab_num='" + ch1Obj.getRef_lab_num() + "'," + " man_review='" + ch1Obj.getMan_review() + "',"
				+ " location='" + ch1Obj.getLocation()

				+ "'," + " demographic_no='" + ch1Obj.getDemographic_no() + "'," + " provider_no='"
				+ ch1Obj.getProviderNo() + "'," + " appointment_no='" + ch1Obj.getAppointment_no() + "',"
				+ " demographic_name='" + StringEscapeUtils.escapeSql(ch1Obj.getDemographic_name()) + "'," + " sex='"
				+ ch1Obj.getSex() + "'," + " province='" + ch1Obj.getProvince() + "'," + " billing_date='"
				+ ch1Obj.getBilling_date() + "'," + " billing_time='" + ch1Obj.getBilling_time() + "'," + " total='"
				+ ch1Obj.getTotal() + "'," + " paid='" + ch1Obj.getPaid() + "'," + " status='" + ch1Obj.getStatus()
				+ "'," + " comment1='" + ch1Obj.getComment() + "'," + " visittype='" + ch1Obj.getVisittype() + "',"
				+ " provider_ohip_no='" + ch1Obj.getProvider_ohip_no() + "'," + " provider_rma_no='"
				+ ch1Obj.getProvider_rma_no() + "'," + " apptProvider_no='" + ch1Obj.getApptProvider_no() + "',"
				+ " asstProvider_no='" + ch1Obj.getAsstProvider_no() + "'," + " creator='" + ch1Obj.getCreator()

				+ "', clinic=" + (ch1Obj.getClinic()==null?"null":"'"+ch1Obj.getClinic()+"'")

				+ " where id=" + ch1Obj.getId();
		_logger.info("updateBillingClaimHeader(sql = " + sql + ")");

		retval = dbObj.updateDBRecord(sql);

		if (!retval) {
			_logger.error("updateBillingClaimHeader(sql = " + sql + ")");
		}
		return retval;
	}
	
	public boolean updateBillingClaimHeaderTrans(BillingClaimHeader1Data ch1Obj) {
		boolean retval = false;
		String sql = "update billing_on_transaction set  pay_program='" + ch1Obj.getPay_program() + "'," + " ref_num='" + ch1Obj.getRef_num() + "'," + " facility_num='"
				+ ch1Obj.getFacilty_num() + "'," + " admission_date='" + ch1Obj.getAdmission_date() + "',"
				 + " man_review='" + ch1Obj.getMan_review() + "',"
				+ " sli_code='" + ch1Obj.getLocation()

				+ "'," + " demographic_no='" + ch1Obj.getDemographic_no() + "'," + " update_provider_no='"
				+ ch1Obj.getProviderNo() + "'," 
			    + " province='" + ch1Obj.getProvince() + "'," + " billing_date='"
				+ ch1Obj.getBilling_date() + "'," + " status='" + ch1Obj.getStatus()
				+ "',"  + " visittype='" + ch1Obj.getVisittype() + "',"
				+ " creator='" + ch1Obj.getCreator()+ "',"
				+ " action_type='" + "U"+ "',"
                + " update_datetime=CURRENT_TIMESTAMP"
				+ ", clinic=" + (ch1Obj.getClinic()==null?"null":"'"+ch1Obj.getClinic()+"'")

				+ " where ch1_id=" + ch1Obj.getId();
		_logger.info("updateBillingClaimHeaderTrans(sql = " + sql + ")");

		retval = dbObj.updateDBRecord(sql);

		if (!retval) {
			_logger.error("updateBillingClaimHeader(sql = " + sql + ")");
		}
		return retval;
	}
	
	public boolean updatedeleteBillingClaimHeaderTrans(BillingClaimHeader1Data ch1Obj) {
		boolean retval = false;
		String sql = "update billing_on_transaction set  pay_program='" + ch1Obj.getPay_program() + "'," + " ref_num='" + ch1Obj.getRef_num() + "'," + " facility_num='"
				+ ch1Obj.getFacilty_num() + "'," + " admission_date='" + ch1Obj.getAdmission_date() + "',"
				 + " man_review='" + ch1Obj.getMan_review() + "',"
				+ " sli_code='" + ch1Obj.getLocation()

				+ "'," + " demographic_no='" + ch1Obj.getDemographic_no() + "'," + " update_provider_no='"
				+ ch1Obj.getProviderNo() + "'," 
			    + " province='" + ch1Obj.getProvince() + "'," + " billing_date='"
				+ ch1Obj.getBilling_date() + "'," + " status='" + ch1Obj.getStatus()
				+ "',"  + " visittype='" + ch1Obj.getVisittype() + "',"
				+ " creator='" + ch1Obj.getCreator()+ "',"
				+ " action_type='" + "D"+ "',"
                + " update_datetime=CURRENT_TIMESTAMP"
				+ ", clinic=" + (ch1Obj.getClinic()==null?"null":"'"+ch1Obj.getClinic()+"'")

				+ " where ch1_id=" + ch1Obj.getId();
		_logger.info("updateBillingClaimHeaderTrans(sql = " + sql + ")");

		retval = dbObj.updateDBRecord(sql);

		if (!retval) {
			_logger.error("updateBillingClaimHeader(sql = " + sql + ")");
		}
		return retval;
	}

	public int addRepoClaimHeader(BillingClaimHeader1Data val) {
		int retval = 0;
		String sql = "insert into billing_on_repo values(\\N, " + " " + val.id + " ," + "'billing_on_cheader1'," + "'"
				+ val.transc_id + "|" + val.rec_id + "|" + val.hin + "|" + val.ver + "|" + val.dob + "|"
				+ val.pay_program + "|" + val.payee + "|" + val.ref_num + "|" + val.facilty_num + "|"
				+ val.admission_date + "|" + val.ref_lab_num + "|" + val.man_review + "|" + val.location + "|"
				+ val.demographic_no + "|" + val.provider_no + "|" + val.appointment_no + "|"
				+ StringEscapeUtils.escapeSql(val.demographic_name) + "|" + val.sex + "|" + val.province + "|"
				+ val.billing_date + "|" + val.billing_time + "|" + val.total + "|" + val.paid + "|" + val.status + "|"
				+ val.comment + "|" + val.visittype + "|" + val.provider_ohip_no + "|" + val.apptProvider_no + "|"
				+ val.asstProvider_no + "|" + val.creator + "|" + val.clinic + "', '" + val.update_datetime + "')";
		_logger.info("addRepoBatchHeader(sql = " + sql + ")");
		retval = dbObj.saveBillingRecord(sql);

		if (retval > 0) {
		} else {
			_logger.error("addRepoClaimHeader(sql = " + sql + ")");
			retval = 0;
		}
		return retval;
	}

	public boolean updateBillingOneItem(BillingItemData val) {
		boolean retval = false;
		String sql = "update billing_on_item set transc_id='" + val.transc_id + "', rec_id='" + val.rec_id
				+ "', service_code='" + val.service_code + "', fee='" + val.fee + "', ser_num='" + val.ser_num
				+ "', service_date='" + val.service_date + "', dx='" + val.dx + "', dx1='" + val.dx1 + "', dx2='"
				+ val.dx2 + "', status='" + val.status + "' where id=" + val.id;
		_logger.info("updateBillingOneItem(sql = " + sql + ")");
		retval = dbObj.updateDBRecord(sql);
		if (!retval) {
			_logger.error("updateBillingOneItem(sql = " + sql + ")");
			return retval;
		}
		return retval;
	}
	
	public boolean updateBillingOneItemPayment(BillingItemData val) {
		boolean retval = false;
		String sql = "update billing_on_item_payment set paid='" + val.paid + "', refund='" + val.refund + "', discount='" + val.discount + "' where billing_on_item_id=" + val.id;
		_logger.info("updateBillingOneItempayment(sql = " + sql + ")");
		retval = dbObj.updateDBRecord(sql);
		if (!retval) {
			_logger.error("updateBillingOneItempayment(sql = " + sql + ")");
			return retval;
		}
		return retval;
	}
	

	
	public int addRepoOneItem(BillingItemData val) {
		int retval = 0;
		String sql = "insert into billing_on_repo values(\\N, " + " " + val.id + " ," + "'billing_on_item'," + "'"
				+ val.transc_id + "|" + val.rec_id + "|" + val.service_code + "|" + val.fee + "|" + val.ser_num + "|"
				+ val.service_date + "|" + val.dx + "|" + val.dx1 + "|" + val.dx2 + "|" + val.status + "', '"
				+ val.timestamp + "')";
		_logger.info("addRepoOneItem(sql = " + sql + ")");
		retval = dbObj.saveBillingRecord(sql);

		if (retval > 0) {
		} else {
			_logger.error("addRepoOneItem(sql = " + sql + ")");
			retval = 0;
		}
		return retval;
	}

	public boolean updateBillingStatus(String id, String status, String providerNo) {
		boolean retval = false;
		String sql = "update billing_on_cheader1 set status='" + status + "' where id=" + id;
		_logger.info("updateBillingStatus(sql = " + sql + ")");
		retval = dbObj.updateDBRecord(sql);
		retval = dbLog.addBillingLog(providerNo, "updateBillingStatus", sql, id);
		if (retval) {
			_logger.error("updateBillingStatus(sql = " + sql + ")");
		}

		sql = "update billing_on_item set status='" + status + "' where ch1_id=" + id;
		_logger.info("updateBillingStatus(sql = " + sql + ") by " + providerNo);
		retval = dbObj.updateDBRecord(sql);
		retval = dbLog.addBillingLog(providerNo, "updateBillingStatus", sql, id);
		if (!retval) {
			_logger.error("updateBillingStatus(sql = " + sql + ")");
		}
		
		sql = "update billing_on_transaction set status='" + status + "',action_type='" + status + "' where ch1_id=" + id;
		_logger.info("updateBillingStatus(sql = " + sql + ") by " + providerNo);
		retval = dbObj.updateDBRecord(sql);
		retval = dbLog.addBillingLog(providerNo, "updateBillingStatus", sql, id);
		if (!retval) {
			_logger.error("updateBillingStatus(sql = " + sql + ")");
		}
		return retval;
	}

	public boolean updateBillingItemStatus(String id, String status) {
		boolean retval = false;
		String sql = "update ', \\N )";
		_logger.info("updateBillingItem(sql = " + sql + ")");
		retval = dbObj.updateDBRecord(sql);

		if (!retval) {
			_logger.error("updateBillingItemStatus(sql = " + sql + ")");
		}
		return retval;
	}

	public List getBillingCH1NoStatusByAppt(String id) {
		List obj = new Vector();
		String sql = "select id,status from billing_on_cheader1 where appointment_no=" + id;
		ResultSet rs = dbObj.searchDBRecord(sql);

		try {
			while (rs.next()) {
				obj.add(rs.getString("id"));
				obj.add(rs.getString("status"));
			}
		} catch (SQLException e) {
			_logger.error("getBillingCH1NoStatusByAppt(sql = " + sql + ")");
			obj = null;
		}
		return obj;
	}

	
	public List getBillingCH1NoStatusByBillNo(String id) {
		List obj = new Vector();
		String sql = "select id,status from billing_on_cheader1 where id=" + id;
		ResultSet rs = dbObj.searchDBRecord(sql);
		
		try {
		       while (rs.next()) {
		       obj.add(rs.getString("id"));
		       obj.add(rs.getString("status"));
		       }
		    } catch (SQLException e) {
		       _logger.error("getBillingCH1NoStatusByAppt(sql = " + sql + ")");
		       obj = null;
		    }
		    return obj;
	}
	
	@SuppressWarnings("rawtypes")
	public List getPayprogramByBillNo(String id){
		List obj = new Vector();
		String sql = "select pay_program from billing_on_cheader1 where id=" + id;
		ResultSet rs = dbObj.searchDBRecord(sql);
		try {
			while(rs.next()){
				obj.add(rs.getString("pay_program"));
			}
		} catch (SQLException e) {
			 _logger.error("getBillingCH1NoStatusByAppt(sql = " + sql + ")");
		       obj = null;
		}
		return obj;
	}
	// 0-cheader1 obj, 1 - item1obj, 2 - item2obj, ...
	public List getBillingRecordObj(String id) {
		List obj = new Vector();
		BillingClaimHeader1Data ch1Obj = null;
		BillingItemData itemObj = null;

		String sql = "select * from billing_on_cheader1 where id=" + id;
		ResultSet rs = dbObj.searchDBRecord(sql);

		try {
			while (rs.next()) {
				ch1Obj = new BillingClaimHeader1Data();
				ch1Obj.setId("" + rs.getInt("id"));
				ch1Obj.setTransc_id(rs.getString("transc_id"));
				ch1Obj.setRec_id(rs.getString("rec_id"));
				ch1Obj.setHin(rs.getString("hin"));
				ch1Obj.setVer(rs.getString("ver"));
				ch1Obj.setDob(rs.getString("dob"));

				ch1Obj.setPay_program(rs.getString("pay_program"));
				ch1Obj.setPayee(rs.getString("payee"));
				ch1Obj.setRef_num(rs.getString("ref_num"));
				ch1Obj.setFacilty_num(rs.getString("facilty_num"));
				ch1Obj.setAdmission_date(rs.getString("admission_date"));
				ch1Obj.setRef_lab_num(rs.getString("ref_lab_num"));
				ch1Obj.setMan_review(rs.getString("man_review"));
				ch1Obj.setLocation(rs.getString("location"));
				ch1Obj.setClinic(rs.getString("clinic"));

				ch1Obj.setDemographic_no(rs.getString("demographic_no"));
				ch1Obj.setProviderNo(rs.getString("provider_no"));
				ch1Obj.setAppointment_no(rs.getString("appointment_no"));
				ch1Obj.setDemographic_name(rs.getString("demographic_name"));
				ch1Obj.setSex(rs.getString("sex"));
				ch1Obj.setProvince(rs.getString("province"));

				ch1Obj.setBilling_date(rs.getString("billing_date"));
				ch1Obj.setBilling_time(rs.getString("billing_time"));
				ch1Obj.setTotal(rs.getString("total"));
				ch1Obj.setPaid(rs.getString("paid"));
				ch1Obj.setStatus(rs.getString("status"));
				ch1Obj.setComment(rs.getString("comment1"));
				ch1Obj.setVisittype(rs.getString("visittype"));
				ch1Obj.setProvider_ohip_no(rs.getString("provider_ohip_no"));
				ch1Obj.setProvider_rma_no(rs.getString("provider_rma_no"));
				ch1Obj.setApptProvider_no(rs.getString("apptProvider_no"));
				ch1Obj.setAsstProvider_no(rs.getString("asstProvider_no"));
				ch1Obj.setCreator(rs.getString("creator"));			
				ch1Obj.setUpdate_datetime(rs.getString("timestamp1"));

				ch1Obj.setClinic(rs.getString("clinic"));
								
				obj.add(ch1Obj);
			}

			sql = "select boi.*,boip.paid,boip.refund,boip.discount from billing_on_item boi left join billing_on_item_payment boip on boi.id = boip.billing_on_item_id where boi.ch1_id=" + id + " and boi.status!='D'";
			_logger.info("getBillingRecordObj(sql = " + sql + ")");
			ResultSet rs2 = dbObj.searchDBRecord(sql);
			while (rs2.next()) {
				itemObj = new BillingItemData();
				itemObj.setId("" + rs2.getInt("id"));
				itemObj.setCh1_id("" + rs2.getInt("ch1_id"));
				itemObj.setTransc_id(rs2.getString("transc_id"));
				itemObj.setRec_id(rs2.getString("rec_id"));
				itemObj.setService_code(rs2.getString("service_code"));
				itemObj.setFee(rs2.getString("fee"));
				itemObj.setSer_num(rs2.getString("ser_num"));
				itemObj.setService_date(rs2.getString("service_date"));
				String diagcode = rs2.getString("dx");
				diagcode = ":::".equals(diagcode) ? "   " : diagcode;
				itemObj.setDx(diagcode);
				itemObj.setDx1(rs2.getString("dx1"));
				itemObj.setDx2(rs2.getString("dx2"));
				itemObj.setStatus(rs2.getString("status"));
				itemObj.setTimestamp(rs2.getString("timestamp"));
				
				try {
					itemObj.setPaid(rs2.getString("paid"));
				} catch (Exception e) {
					itemObj.setPaid("0.00");
				}
				try {
					itemObj.setRefund(rs2.getString("refund"));
				} catch (Exception e) {
					itemObj.setRefund("0.00");
				}
				try {
					itemObj.setDiscount(rs2.getString("discount"));
				} catch (Exception e) {
					itemObj.setDiscount("0.00");
				}
				
				obj.add(itemObj);
			}
		} catch (SQLException e) {
			_logger.error("getBillingRecordObj(sql = " + sql + ")");
			obj = null;
		}

		return obj;
	}

	public List getBillingRejectList(String id) {
		List obj = new Vector();
		String sql = "select claim_error, code_error from billing_on_eareport where billing_no=" + id
				+ " order by process_date desc";
		ResultSet rs = dbObj.searchDBRecord(sql);
		try {
			while (rs.next()) {
				String error = rs.getString("claim_error").trim();
				if (error.length() > 2) {
					String temp[] = error.split("\\s");
					for (int i = 0; i < temp.length; i++) {
						obj.add(temp[i]);
					}
				}
				error = rs.getString("code_error").trim();
				if (error.length() > 1) {
					String temp[] = error.split("\\s");
					for (int i = 0; i < temp.length; i++) {
						obj.add(temp[i]);
					}
				}
			}
		} catch (SQLException e) {
			_logger.error("getBillingRejectList(sql = " + sql + ")");
			obj = null;
		}
		return obj;
	}

	public List getBillingExplanatoryList(String id) {
		List obj = new Vector();
		String sql = "select error_code, raheader_no from radetail where billing_no=" + id
				+ " order by raheader_no desc, radetail_no";
		ResultSet rs = dbObj.searchDBRecord(sql);
		String tHeaderNo = "";
		try {
			while (rs.next()) {
				if ("".equals(tHeaderNo)) {
					tHeaderNo = rs.getString("raheader_no");
				} else if (!tHeaderNo.equals(rs.getString("raheader_no"))) {
					break;
				}
				obj.add(rs.getString("error_code"));
			}
		} catch (SQLException e) {
			_logger.error("getBillingExplanatoryList(sql = " + sql + ")");
			obj = null;
		}
		return obj;
	}

	public String getBillingTotal(String id) {
		String ret = "";
		String sql = "select total from billing_on_cheader1 where id=" + id;
		ResultSet rs = dbObj.searchDBRecord(sql);
		try {
			while (rs.next()) {
				ret = rs.getString("total");
			}
		} catch (SQLException e) {
			_logger.error("getBillingTotal(sql = " + sql + ")");
			ret = null;
		}
		return ret;
	}
	
	
	public String getBillingPaid(String id) {
		String ret = "";
		String sql = "select paid from billing_on_cheader1 where id=" + id;
		ResultSet rs = dbObj.searchDBRecord(sql);
		try {
			while (rs.next()) {
				ret = rs.getString("paid");
			}
		} catch (SQLException e) {
			_logger.error("getBillingPaid(sql = " + sql + ")");
			ret = null;
		}
		return ret;
	}

	public boolean updateBillingTotal(String fee, String id) {
		boolean ret = false;
		String sql = "update billing_on_cheader1 set total ='" + fee + "' where id=" + id;
		ret = dbObj.updateDBRecord(sql);
		if (!ret) {
			_logger.error("updateBillingTotal(sql = " + sql + ")");
		}
		return ret;
	}
	
	public boolean updateBillingPaid(String fee, String id) {
		boolean ret = false;
		String sql = "update billing_on_cheader1 set paid ='" + fee + "' where id=" + id;
		ret = dbObj.updateDBRecord(sql);
		if (!ret) {
			_logger.error("updateBillingPaid(sql = " + sql + ")");
		}
		return ret;
	}
	
	public void addBillingTransaction(BillingItemData obj,String payProgram){
		String sql = "insert into billing_on_transaction(ch1_id,payment_id,pay_program,payment_date,service_code,service_code_num,service_code_invoiced,service_code_paid,service_code_refund,service_code_discount,status) values( " + obj.ch1_id + ", '" + 0 + "', '" +payProgram 
				+ "', \\N, '" + obj.service_code + "', '" + obj.ser_num + "', '" + obj.fee
				+ "', '" + obj.paid + "', '" + obj.refund + "', '" + obj.discount+"','"+obj.status+"','"+1+"')";
		dbObj.saveBillingRecord(sql);
	}
	
	public void addInsertOneBillItemTrans(BillingClaimHeader1Data billHeader, BillingItemData billItem, String updateProviderNo,int id,int paymentId) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("insert into billing_on_transaction values");
		sqlBuf.append("(\\N,");
		sqlBuf.append(billItem.getCh1_id() + ","); // cheader1_id
		sqlBuf.append("'" + paymentId + "',"); // paymentId
		sqlBuf.append(id + ","); // billing_on_item_id
		sqlBuf.append(billHeader.getDemographic_no() + ","); // demographic_no
		sqlBuf.append("'" + updateProviderNo + "',"); // update_provider_no
		sqlBuf.append("CURRENT_TIMESTAMP,"); // update_datetime
		sqlBuf.append("null,"); // payment_date
		sqlBuf.append("'" + billHeader.getRef_num() + "',"); // ref_num
		sqlBuf.append("'" + billHeader.getProvince() + "',"); // province
		sqlBuf.append("'" + billHeader.getMan_review() + "',"); // man_review
		sqlBuf.append("'" + billItem.getService_date() + "',"); // billing_date
		sqlBuf.append("'" + billItem.getStatus() + "',"); // status
		sqlBuf.append("'" + billHeader.getPay_program() + "',"); // pay_program
		//sqlBuf.append("'" + billHeader.getPayee() + "',"); // paymentType
		sqlBuf.append("'" + billHeader.getFacilty_num() + "',"); // facility_num
		sqlBuf.append("'" + billHeader.getClinic() + "',"); // clinic
		sqlBuf.append("'" + billHeader.getProviderNo() + "',"); // provider_no
		sqlBuf.append("'" + updateProviderNo + "',"); // creator
		sqlBuf.append("'" + billHeader.getVisittype() + "',"); // visittype
		sqlBuf.append("'" + billHeader.getAdmission_date() + "',"); // admission_date
		sqlBuf.append("'" + billHeader.getLocation() + "',"); // sli_code
		sqlBuf.append("'" + billItem.getService_code() + "',"); // service_code
		sqlBuf.append("'" + billItem.getSer_num() + "',"); // service_code_num
		sqlBuf.append("'" + billItem.getFee() + "',"); // service_code_invoiced
		sqlBuf.append("'',"); // service_code_paid
		sqlBuf.append("'',"); // service_code_refund
		sqlBuf.append("'',"); // service_code_discount
		sqlBuf.append("'" + billItem.getDx() + "',"); // dx_code
		sqlBuf.append("'" + billHeader.getComment() + "',"); // billing_notes
		sqlBuf.append("'" + BillingDataHlp.ACTION_TYPE.C.name() + "',"); // action_type
		sqlBuf.append("'" + 1 + "'");//paymenttypeId
		sqlBuf.append(")");
		dbObj.saveBillingRecord(sqlBuf.toString());
	}
	
	public void addUpdateOneBillItemTrans(BillingClaimHeader1Data billHeader, BillingItemData billItem, String updateProviderNo) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("insert into billing_on_transaction values");
		sqlBuf.append("(\\N,");
		sqlBuf.append(billItem.getCh1_id() + ","); // cheader1_id
		sqlBuf.append("'',"); // paymentId
		sqlBuf.append(0 + ","); // billing_on_item_id
		sqlBuf.append(billHeader.getDemographic_no() + ","); // demographic_no
		sqlBuf.append("'" + updateProviderNo + "',"); // update_provider_no
		sqlBuf.append("CURRENT_TIMESTAMP,"); // update_datetime
		sqlBuf.append("null,"); // payment_date
		sqlBuf.append("'" + billHeader.getRef_num() + "',"); // ref_num
		sqlBuf.append("'" + billHeader.getProvince() + "',"); // province
		sqlBuf.append("'" + billHeader.getMan_review() + "',"); // man_review
		sqlBuf.append("'" + billItem.getService_date() + "',"); // billing_date
		sqlBuf.append("'" + billItem.getStatus() + "',"); // status
		sqlBuf.append("'" + billHeader.getPay_program() + "',"); // pay_program
		//sqlBuf.append("'" + billHeader.getPayee() + "',"); // paymentType
		sqlBuf.append("'" + billHeader.getFacilty_num() + "',"); // facility_num
		sqlBuf.append("'" + billHeader.getClinic() + "',"); // clinic
		sqlBuf.append("'" + billHeader.getProviderNo() + "',"); // provider_no
		sqlBuf.append("'" + billHeader.getCreator() + "',"); // creator
		sqlBuf.append("'" + billHeader.getVisittype() + "',"); // visittype
		sqlBuf.append("'" + billHeader.getAdmission_date() + "',"); // admission_date
		sqlBuf.append("'" + billHeader.getLocation() + "',"); // sli_code
		sqlBuf.append("'" + billItem.getService_code() + "',"); // service_code
		sqlBuf.append("'" + billItem.getSer_num() + "',"); // service_code_num
		sqlBuf.append("'" + billItem.getFee() + "',"); // service_code_invoiced
		sqlBuf.append("'',"); // service_code_paid
		sqlBuf.append("'',"); // service_code_refund
		sqlBuf.append("'',"); // service_code_discount
		sqlBuf.append("'" + billItem.getDx() + "',"); // dx_code
		sqlBuf.append("'" + billHeader.getComment() + "',"); // billing_notes
		sqlBuf.append("'" + BillingDataHlp.ACTION_TYPE.U.name() + "',"); // action_type
		sqlBuf.append("'" + 1 + "'");//paymenttypeId
		sqlBuf.append(")");
		dbObj.saveBillingRecord(sqlBuf.toString());
	}
	
	public void addUpdateOneBillClaimHeaderTrans(BillingClaimHeader1Data billHeader) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("insert into billing_on_transaction values");
		sqlBuf.append("(\\N,");
		sqlBuf.append("'" + billHeader.getId()+ "',"); // cheader1_id
		sqlBuf.append("'',"); // paymentId
		sqlBuf.append(0 + ","); // billing_on_item_id
		sqlBuf.append(billHeader.getDemographic_no() + ","); // demographic_no
		sqlBuf.append("'" + billHeader.getProviderNo() + "',"); // update_provider_no
		sqlBuf.append("CURRENT_TIMESTAMP,"); // update_datetime
		sqlBuf.append("null,"); // payment_date
		sqlBuf.append("'" + billHeader.getRef_num() + "',"); // ref_num
		sqlBuf.append("'" + billHeader.getProvince() + "',"); // province
		sqlBuf.append("'" + billHeader.getMan_review() + "',"); // man_review
		sqlBuf.append("'" + "" + "',"); // billing_date
		sqlBuf.append("'" + "" + "',"); // status
		sqlBuf.append("'" + billHeader.getPay_program() + "',"); // pay_program
		//sqlBuf.append("'" + billHeader.getPayee() + "',"); // paymentType
		sqlBuf.append("'" + billHeader.getFacilty_num() + "',"); // facility_num
		sqlBuf.append("'" + billHeader.getClinic() + "',"); // clinic
		sqlBuf.append("'" + billHeader.getProviderNo() + "',"); // provider_no
		sqlBuf.append("'" + billHeader.getCreator() + "',"); // creator
		sqlBuf.append("'" + billHeader.getVisittype() + "',"); // visittype
		sqlBuf.append("'" + billHeader.getAdmission_date() + "',"); // admission_date
		sqlBuf.append("'" + billHeader.getLocation() + "',"); // sli_code
		sqlBuf.append("'" + "" + "',"); // service_code
		sqlBuf.append("'" + "" + "',"); // service_code_num
		sqlBuf.append("'" + "" + "',"); // service_code_invoiced
		sqlBuf.append("'',"); // service_code_paid
		sqlBuf.append("'',"); // service_code_refund
		sqlBuf.append("'',"); // service_code_discount
		sqlBuf.append("'" + "" + "',"); // dx_code
		sqlBuf.append("'" + billHeader.getComment() + "',"); // billing_notes
		sqlBuf.append("'" + BillingDataHlp.ACTION_TYPE.U.name() + "',"); // action_type
		sqlBuf.append("'" + 1 + "'");//paymenttypeId
		sqlBuf.append(")");
		dbObj.saveBillingRecord(sqlBuf.toString());
	}
	
	public void addDeleteOneBillClaimHeaderTrans(BillingClaimHeader1Data billHeader) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("insert into billing_on_transaction values");
		sqlBuf.append("(\\N,");
		sqlBuf.append("'" + billHeader.getId()+ "',"); // cheader1_id
		sqlBuf.append("'',"); // paymentId
		sqlBuf.append(0 + ","); // billing_on_item_id
		sqlBuf.append(billHeader.getDemographic_no() + ","); // demographic_no
		sqlBuf.append("'" + billHeader.getProviderNo() + "',"); // update_provider_no
		sqlBuf.append("CURRENT_TIMESTAMP,"); // update_datetime
		sqlBuf.append("null,"); // payment_date
		sqlBuf.append("'" + billHeader.getRef_num() + "',"); // ref_num
		sqlBuf.append("'" + billHeader.getProvince() + "',"); // province
		sqlBuf.append("'" + billHeader.getMan_review() + "',"); // man_review
		sqlBuf.append("'" + "" + "',"); // billing_date
		sqlBuf.append("'" + "" + "',"); // status
		sqlBuf.append("'" + billHeader.getPay_program() + "',"); // pay_program
		//sqlBuf.append("'" + billHeader.getPayee() + "',"); // paymentType
		sqlBuf.append("'" + billHeader.getFacilty_num() + "',"); // facility_num
		sqlBuf.append("'" + billHeader.getClinic() + "',"); // clinic
		sqlBuf.append("'" + billHeader.getProviderNo() + "',"); // provider_no
		sqlBuf.append("'" + billHeader.getCreator() + "',"); // creator
		sqlBuf.append("'" + billHeader.getVisittype() + "',"); // visittype
		sqlBuf.append("'" + billHeader.getAdmission_date() + "',"); // admission_date
		sqlBuf.append("'" + billHeader.getLocation() + "',"); // sli_code
		sqlBuf.append("'" + "" + "',"); // service_code
		sqlBuf.append("'" + "" + "',"); // service_code_num
		sqlBuf.append("'" + "" + "',"); // service_code_invoiced
		sqlBuf.append("'',"); // service_code_paid
		sqlBuf.append("'',"); // service_code_refund
		sqlBuf.append("'',"); // service_code_discount
		sqlBuf.append("'" + "" + "',"); // dx_code
		sqlBuf.append("'" + billHeader.getComment() + "',"); // billing_notes
		sqlBuf.append("'" + BillingDataHlp.ACTION_TYPE.D.name() + "',"); // action_type
		sqlBuf.append("'" + 1 + "'");//paymenttypeId
		sqlBuf.append(")");
		dbObj.saveBillingRecord(sqlBuf.toString());
	}
	
	public void addDeleteOneBillItemTrans(BillingClaimHeader1Data billHeader, BillingItemData billItem, String updateProviderNo) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("insert into billing_on_transaction values");
		sqlBuf.append("(\\N,");
		sqlBuf.append(billItem.getCh1_id() + ","); // cheader1_id
		sqlBuf.append("'',"); // paymentId
		sqlBuf.append(0 + ","); // billing_on_item_id
		sqlBuf.append(billHeader.getDemographic_no() + ","); // demographic_no
		sqlBuf.append("'" + updateProviderNo + "',"); // update_provider_no
		sqlBuf.append("CURRENT_TIMESTAMP,"); // update_datetime
		sqlBuf.append("CURRENT_DATE,"); // payment_date
		sqlBuf.append("'" + billHeader.getRef_num() + "',"); // ref_num
		sqlBuf.append("'" + billHeader.getProvince() + "',"); // province
		sqlBuf.append("'" + billHeader.getMan_review() + "',"); // man_review
		sqlBuf.append("'" + billHeader.getBilling_date() + "',"); // billing_date
		sqlBuf.append("'" + BillingDataHlp.BILLINGFILE_STATUS_DELETED + "',"); // status
		sqlBuf.append("'" + billHeader.getPay_program() + "',"); // pay_program
		//sqlBuf.append("'" + billHeader.getPayee() + "',"); // paymentType
		sqlBuf.append("'" + billHeader.getFacilty_num() + "',"); // facility_num
		sqlBuf.append("'" + billHeader.getClinic() + "',"); // clinic
		sqlBuf.append("'" + billHeader.getProviderNo() + "',"); // provider_no
		sqlBuf.append("'" + billHeader.getCreator() + "',"); // creator
		sqlBuf.append("'" + billHeader.getVisittype() + "',"); // visittype
		sqlBuf.append("'" + billHeader.getAdmission_date() + "',"); // admission_date
		sqlBuf.append("'" + billHeader.getLocation() + "',"); // sli_code
		sqlBuf.append("'" + billItem.getService_code() + "',"); // service_code
		sqlBuf.append("'" + billItem.getSer_num() + "',"); // service_code_num
		sqlBuf.append("'" + billItem.getFee() + "',"); // service_code_invoiced
		sqlBuf.append("'" + billItem.getPaid() + "',"); // service_code_paid
		sqlBuf.append("'" + billItem.getRefund() + "',"); // service_code_refund
		sqlBuf.append("'" + billItem.getDiscount() + "',"); // service_code_discount
		sqlBuf.append("'" + billItem.getDx() + "',"); // dx_code
		sqlBuf.append("'" + billHeader.getComment() + "',"); // billing_notes
		sqlBuf.append("'" + BillingDataHlp.ACTION_TYPE.D.name() + "',"); // action_type
		sqlBuf.append("'" + 1 + "'");//paymenttypeId
		sqlBuf.append(")");
		dbObj.saveBillingRecord(sqlBuf.toString());
	}
}
