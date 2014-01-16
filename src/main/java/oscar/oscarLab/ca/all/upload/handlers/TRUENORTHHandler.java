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
package oscar.oscarLab.ca.all.upload.handlers;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.oscarehr.common.dao.Hl7TextInfoDao;
import org.oscarehr.common.model.Hl7TextInfo;
import org.oscarehr.util.SpringUtils;
import oscar.oscarLab.ca.all.parsers.Factory;
import oscar.oscarLab.ca.all.upload.MessageUploader;
import oscar.oscarLab.ca.all.util.Utilities;

public class TRUENORTHHandler
  implements MessageHandler
{
  Logger logger = Logger.getLogger(TRUENORTHHandler.class);

  public TRUENORTHHandler() {
    this.logger.info("NEW TRUENORTHHandler UPLOAD HANDLER instance just instantiated. ");
  }

  public String parse(String serviceName, String fileName, int fileId) {
    this.logger.info("ABOUT TO PARSE!");
    int i = 0;
    try {
      ArrayList messages = Utilities.separateMessages(fileName);

      for (i = 0; i < messages.size(); i++) {
        String msg = (String)messages.get(i);
        MessageUploader.routeReport(serviceName, "TRUENORTH", msg, fileId);
      }

      updateLabStatus(messages.size());
      this.logger.info("Parsed OK");
    } catch (Exception e) {
      MessageUploader.clean(fileId);
      this.logger.error("Could not upload message", e);
      return null;
    }
    return "success";
  }

  private void updateLabStatus(int n)
  {
    Hl7TextInfoDao hl7TextInfoDao = (Hl7TextInfoDao)SpringUtils.getBean("hl7TextInfoDao");
    List<Hl7TextInfo> labList = hl7TextInfoDao.getAllLabsByLabNumberResultStatus();

    for (Hl7TextInfo hinfo : labList)
      while (n > 0) {
        if (hinfo.getResultStatus().equals("A")) {
          Integer labNum = Integer.valueOf(hinfo.getLabNumber());
          oscar.oscarLab.ca.all.parsers.MessageHandler h = Factory.getHandler(labNum.toString());

          int i = 0;
          int j = 0;
          String resultStatus = "";
          while ((resultStatus.equals("")) && (i < h.getOBRCount())) {
            j = 0;
            while ((resultStatus.equals("")) && (j < h.getOBXCount(i))) {
              this.logger.info("obr(" + i + ") obx(" + j + ") abnormal ? : " + h.getOBXAbnormalFlag(i, j));
              if (h.isOBXAbnormal(i, j)) {
                resultStatus = "A";
                hl7TextInfoDao.updateResultStatusByLabId("A", hinfo.getLabNumber());
              }

              j++;
            }
            i++;
          }
        }
        n--;
      }
  }
}
