/***
 * Utility for smart fax.
 * 
 * @author Rohit Prajapati (rohitprajapati54@gmail.com)
 * 
 */
package oscar.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import oscar.OscarProperties;

public class SmartFaxUtil
{
	private static final String MODEM_KEY_PREFIX = "smartfax_modem_";
	
	/***
     * checking if provider is using default fax. if so return the default fax key in oscar.properties
     * @param providerFaxNo
     * @return
     */
    public static String getDefaultFaxModem(String providerFaxNo)
    {
    	String defaultFaxModem = "";

    	if(providerFaxNo!=null && providerFaxNo.trim().length()>0)
    	{
    		providerFaxNo = providerFaxNo.replaceAll("\\D", "");
    		Map<String, String> smartFaxProperties = getDefaultSmartFaxModemProperties();
    		
    		if(smartFaxProperties.get(providerFaxNo)!=null && smartFaxProperties.get(providerFaxNo).trim().length()>0)
    		{
    			defaultFaxModem = smartFaxProperties.get(providerFaxNo).trim();
    			defaultFaxModem = defaultFaxModem.replace(MODEM_KEY_PREFIX, "");
    		}
    		
    	}
    	
    	return defaultFaxModem;
    }
    
    /**
     * get smartfax modem properties from oscar.properties
     * returns map - key = fax no.(only digits), val = property key
     * @return
     */
    private static Map<String, String> smartFaxProperties = null;
    public static Map<String, String> getDefaultSmartFaxModemProperties()
    {
    	smartFaxProperties = null;
    	if(smartFaxProperties!=null)
    	{
    		return smartFaxProperties;
    	}
    	else
    	{
    		smartFaxProperties = new HashMap<String, String>();
    	}
    	
    	OscarProperties oscarProperties = OscarProperties.getInstance();
    	Set<Object> keySet = oscarProperties.keySet();
    	Iterator<Object> iter = keySet.iterator();
    	
    	Object key = null;
    	String keyStr = "";
    	String val = "";
    	while(iter.hasNext())
    	{
    		key = iter.next();
    		if(key!=null && key.toString().startsWith("smartfax_modem"))
    		{
    			keyStr = key.toString().trim();
    			val = oscarProperties.getProperty(keyStr).trim();
    			val = val.replaceAll("\\D", "");
    			smartFaxProperties.put(val, keyStr);
    		}
    	}
    	
    	return smartFaxProperties;
    }
}
