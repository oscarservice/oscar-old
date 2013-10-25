package oscar.oscarBilling.ca.on.pageUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.billing.CA.dao.BillingPaymentTypeDao;
import org.oscarehr.billing.CA.model.BillingPaymentType;
import org.oscarehr.util.MiscUtils;

public class PaymentTypeAction extends DispatchAction {
	private BillingPaymentTypeDao billingPaymentTypeDao;

	public BillingPaymentTypeDao getBillingPaymentTypeDao() {
		return billingPaymentTypeDao;
	}

	public void setBillingPaymentTypeDao(BillingPaymentTypeDao billingPaymentTypeDao) {
		this.billingPaymentTypeDao = billingPaymentTypeDao;
	}
	
	public ActionForward unspecified(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest request, HttpServletResponse servletResponse) {
		List<BillingPaymentType> paymentTypeList = billingPaymentTypeDao.list();
		request.setAttribute("paymentTypeList", paymentTypeList);
		return actionMapping.findForward("success");
	}
	
	public ActionForward listAllType(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest request, HttpServletResponse servletResponse) {
		
		return unspecified(actionMapping, actionForm, request, servletResponse);
	}
	
	public ActionForward createType(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest request, HttpServletResponse servletResponse) {
		
		String paymentType = (String) request.getParameter("paymentType");
		if (null != paymentType && !paymentType.isEmpty()) {
			BillingPaymentType billingPaymentType = null;
			Map<String, String> retMap = new HashMap<String, String>();
			JSONObject json = null;
			try {
				billingPaymentType = billingPaymentTypeDao.getPaymentTypeByName(paymentType);
			
				if (billingPaymentType != null) {
					retMap.put("ret", "1");
					retMap.put("reason", "Payment type: " + paymentType + " already exists!");
				} else {
					billingPaymentType = new BillingPaymentType();
					billingPaymentType.setPaymentType(paymentType);
					billingPaymentTypeDao.persist(billingPaymentType);
					retMap.put("ret", "0");
					//return actionMapping.findForward("success");
				}
			} catch (Exception e) {
				retMap.put("ret", "1");
				retMap.put("reason", e.toString());
			}
			
			try {
				json = JSONObject.fromObject(retMap);
				servletResponse.getWriter().write(json.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				MiscUtils.getLogger().info(e.toString());
			}
		}
		
		return actionMapping.findForward("null");
	}
	
	public ActionForward editType(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest request, HttpServletResponse servletResponse) {
		String oldPaymentType = (String) request.getParameter("oldPaymentType");
		String paymentType = (String) request.getParameter("paymentType");
		if (oldPaymentType != null && !oldPaymentType.isEmpty() && null != paymentType && !paymentType.isEmpty()) {
			BillingPaymentType old = null;
			Map<String, String> retMap = new HashMap<String, String>();
			JSONObject json = null;
			try {
				old = billingPaymentTypeDao.getPaymentTypeByName(oldPaymentType);
				if (old == null) {
					retMap.put("ret", "1");
					retMap.put("reason", "Old payment type: " + oldPaymentType + " doesn't exist!");
				} else {
					BillingPaymentType newType = billingPaymentTypeDao.getPaymentTypeByName(paymentType);
					if (newType != null) {
						retMap.put("ret", "1");
						retMap.put("reason", "Payment type: " + paymentType + " already exists!");
					} else {
						old.setPaymentType(paymentType);
						billingPaymentTypeDao.merge(old);
						retMap.put("ret", "0");
						//return actionMapping.findForward("success");
					}
				}
			} catch (Exception e) {
				retMap.put("ret", "1");
				retMap.put("reason", e.toString());
			}
			
			try {
				json = JSONObject.fromObject(retMap);
				servletResponse.getWriter().write(json.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				MiscUtils.getLogger().info(e.toString());
			}
		}
		
		return actionMapping.findForward("null");
	}
}
