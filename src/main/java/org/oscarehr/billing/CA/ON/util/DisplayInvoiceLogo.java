package org.oscarehr.billing.CA.ON.util;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.oscarehr.util.MiscUtils;

import oscar.OscarProperties;

public class DisplayInvoiceLogo extends DownloadAction{

	@Override
	protected StreamInfo getStreamInfo(ActionMapping arg0, ActionForm arg1,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		OscarProperties oscarProp = OscarProperties.getInstance();
		if (!oscarProp.getBooleanProperty("invoice_head_logo_enable", "true")) {
			throw new Exception("invoice_head_logo_enable switch is not opened!!");
		}
		String fileName = oscarProp.getProperty("invoice_head_logo_image");
        String document_dir = OscarProperties.getInstance().getProperty("DOCUMENT_DIR");

        response.setHeader("Content-disposition","inline; filename=" + fileName);

        File file = null;
        try{
           File directory = new File(document_dir);
           if(!directory.exists()){
              throw new Exception("Directory:  " + document_dir+ " does not exist");
           }
           file = new File(directory, fileName);

           if (!directory.equals(file.getParentFile())) {
               MiscUtils.getLogger().debug("SECURITY WARNING: Illegal file path detected, client attempted to navigate away from the file directory");
               throw new Exception("Could not open file " + fileName + ".  Check the file path");
           }
        }catch(Exception e){
            MiscUtils.getLogger().error("Error", e);
            throw new Exception("Could not open file "+document_dir+fileName +" does "+document_dir+ " exist ?",e);
        }
        //gets content type from image extension
        String contentType = new MimetypesFileTypeMap().getContentType(file);
        /**
         * For encoding file types not included in the mimetypes file
         * You need to look at mimetypes file to check if the file type you are using is included
         *
         */
         try{
                if(extension(file.getName()).equalsIgnoreCase("png")){ // for PNG
                    contentType = "image/png";
                }else if(extension(file.getName()).equalsIgnoreCase("jpeg")||
                        extension(file.getName()).equalsIgnoreCase("jpe")||
                        extension(file.getName()).equalsIgnoreCase("jpg")){ //for JPEG,JPG,JPE
                    contentType = "image/jpeg";
                }else if(extension(file.getName()).equalsIgnoreCase("bmp")){ // for BMP
                    contentType = "image/bmp";
                }else if(extension(file.getName()).equalsIgnoreCase("cod")){ // for COD
                    contentType = "image/cis-cod";
                }else if(extension(file.getName()).equalsIgnoreCase("ief")){ // for IEF
                    contentType = "image/ief";
                }else if(extension(file.getName()).equalsIgnoreCase("jfif")){ // for JFIF
                    contentType = "image/pipeg";
                }else if(extension(file.getName()).equalsIgnoreCase("svg")){ // for SVG
                    contentType = "image/svg+xml";
                }else if(extension(file.getName()).equalsIgnoreCase("tiff")||
                         extension(file.getName()).equalsIgnoreCase("tif")){ // for TIFF or TIF
                    contentType = "image/tiff";
                }else if(extension(file.getName()).equalsIgnoreCase("pbm")){ // for PBM
                    contentType = "image/x-portable-bitmap";
                }else if(extension(file.getName()).equalsIgnoreCase("pnm")){ // for PNM
                    contentType = "image/x-portable-anymap";
                }else if(extension(file.getName()).equalsIgnoreCase("pgm")){ // for PGM
                    contentType = "image/x-portable-greymap";
                }else if(extension(file.getName()).equalsIgnoreCase("ppm")){ // for PPM
                    contentType = "image/x-portable-pixmap";
                }else if(extension(file.getName()).equalsIgnoreCase("xbm")){ // for XBM
                    contentType = "image/x-xbitmap";
                }else if(extension(file.getName()).equalsIgnoreCase("xpm")){ // for XPM
                    contentType = "image/x-xpixmap";
                }else if(extension(file.getName()).equalsIgnoreCase("xwd")){ // for XWD
                    contentType = "image/x-xwindowdump";
                }else if(extension(file.getName()).equalsIgnoreCase("rgb")){ // for RGB
                    contentType = "image/x-rgb";
                }else if(extension(file.getName()).equalsIgnoreCase("ico")){ // for ICO
                    contentType = "image/x-icon";
                }else if(extension(file.getName()).equalsIgnoreCase("cmx")){ // for CMX
                    contentType = "image/x-cmx";
                }else if(extension(file.getName()).equalsIgnoreCase("ras")){ // for RAS
                    contentType = "image/x-cmu-raster";
                }else if(extension(file.getName()).equalsIgnoreCase("gif")){ // for GIF
                    contentType = "image/gif";
                }else if(extension(file.getName()).equalsIgnoreCase("js")){ // for GIF
                    contentType = "text/javascript";
                }else if(extension(file.getName()).equalsIgnoreCase("css")){ // for GIF
                    contentType = "text/css";
                }else if(extension(file.getName()).equalsIgnoreCase("rtl") || extension(file.getName()).equalsIgnoreCase("html") || extension(file.getName()).equalsIgnoreCase("htm")){ // for HTML
                    contentType = "text/html";
                }else{
                    throw new Exception("please check the file type or update mimetypes.default file to include the "+"."+extension(file.getName()));
                }
            }catch(Exception e){MiscUtils.getLogger().error("Error", e);
                throw new Exception("Could not open file "+file.getName()+" wrong file extension, ",e);
            }
        

        return new FileStreamInfo(contentType, file);
	}

	public String extension(String f) {
		int dot = f.lastIndexOf(".");
		return f.substring(dot + 1);
	}
}
