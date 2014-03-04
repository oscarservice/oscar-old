package oscar.oscarEncounter.pageUtil;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.MessageResources;
import org.oscarehr.common.dao.DemographicDao;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.util.SpringUtils;

import oscar.util.StringUtils;

import com.oscar.middleware.ZeissOruBean;
import com.oscar.middleware.ZeissOruBeanDao;

public class EctDisplayMiddlewareAction extends EctDisplayAction {

	private static final String cmd = "middleware";
	private static DemographicDao demographicDao = (DemographicDao) SpringUtils.getBean("demographicDao");

	public boolean getInfo(EctSessionBean bean, HttpServletRequest request,
			NavBarDisplayDAO Dao, MessageResources messages) {

		if (!oscar.OscarProperties.getInstance().getBooleanProperty("FORUMVIEWER_LINK_ENABLE", "true")) {
			return true;
		}
		
		Dao.setLeftHeading(messages.getMessage(request.getLocale(), "global.middleware"));
		Dao.setRightHeadingID(cmd);
		ZeissOruBeanDao zeissOruBeanDao = SpringUtils.getBean(ZeissOruBeanDao.class);
		List<ZeissOruBean> zeissOruBean = zeissOruBeanDao.list(Integer.parseInt(bean.demographicNo));

		for (ZeissOruBean zeissOruBeans : zeissOruBean) {
			NavBarDisplayDAO.Item item = NavBarDisplayDAO.Item();
			String itemHeader = StringUtils.maxLenString(
					zeissOruBeans.getPlacer_order_number(), MAX_LEN_TITLE,
					CROP_LEN_TITLE, ELLIPSES);
			item.setLinkTitle(itemHeader);
			item.setTitle(itemHeader);
			item.setDate(zeissOruBeans.getStudy_date());
			
			String url = "";
			Demographic patient = demographicDao.getDemographic(bean.demographicNo);
			if (patient != null && !patient.getHin().trim().isEmpty()) {
				url = "openzeisswin('"
						+ patient.getHin()
						+ "','"
						+ new SimpleDateFormat("yyyy-MM-dd").format(zeissOruBeans
								.getStudy_date()) + "'); return false;";
			} else {
				url = "openzeisswin('"
						+ bean.demographicNo
						+ "','"
						+ new SimpleDateFormat("yyyy-MM-dd").format(zeissOruBeans
								.getStudy_date()) + "'); return false;";
			}
			
			item.setURL(url);
			Dao.addItem(item);
		}

		return true;
	}

	public String getCmd() {
		return cmd;
	}
}
