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
		if (null != paymentType && paymentType.isEmpty()) {
			BillingPaymentType billingPaymentType = billingPaymentTypeDao.getPaymentTypeByName(paymentType);
			Map<String, String> retMap = new HashMap<String, String>();
			JSONObject json = null;
			if (billingPaymentType != null) {
				retMap.put("ret", "1");
				retMap.put("reason", "Payment type: " + billingPaymentType + " already exists!");
				json = JSONObject.fromObject(retMap);
			} else {
				billingPaymentType = new BillingPaymentType();
				billingPaymentType.setPaymentType(paymentType);
				billingPaymentTypeDao.persist(billingPaymentType);
				retMap.put("ret", "0");
				json = JSONObject.fromObject(retMap);
			}
			
			try {
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
			BillingPaymentType old = billingPaymentTypeDao.getPaymentTypeByName(oldPaymentType);
			Map<String, String> retMap = new HashMap<String, String>();
			JSONObject json = null;
			if (old == null) {
				retMap.put("ret", "1");
				retMap.put("reason", "Old payment type: " + oldPaymentType + " doesn't exist!");
				json = JSONObject.fromObject(retMap);
			} else {
				BillingPaymentType newType = billingPaymentTypeDao.getPaymentTypeByName(paymentType);
				if (newType != null) {
					retMap.put("ret", "1");
					retMap.put("reason", "Payment type: " + paymentType + " already exists!");
					json = JSONObject.fromObject(retMap);
				} else {
					old.setPaymentType(paymentType);
					billingPaymentTypeDao.merge(old);
					retMap.put("ret", "0");
					json = JSONObject.fromObject(retMap);
				}
				
				try {
					servletResponse.getWriter().write(json.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					MiscUtils.getLogger().info(e.toString());
				}
			}
		}
		
		return actionMapping.findForward("null");
	}
}
