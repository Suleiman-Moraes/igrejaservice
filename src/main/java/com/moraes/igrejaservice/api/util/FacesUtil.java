package com.moraes.igrejaservice.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.moraes.igrejaservice.message.Message;

public class FacesUtil {

	private static final Log logger = LogFactory.getLog(FacesUtil.class);
	
	private static final String URL_AGRFISCAL_WEB_SERVICE = "http://localhost:8080/agrfiscalservice";
//	private static final Boolean IS_LOCAL = Boolean.TRUE;
//	private static final Boolean IS_LOCAL = Boolean.FALSE;
	
	public static String getUrlAgrFiscalWebService(String context) {
		try {
			if(context.matches("http://localhost:.*")) {
				return "http://localhost:7771";
			}
			else if(context.matches("http://10.243.4.187:8082.*")) {
				return "http://10.243.4.187:8082/agrfiscalservice";
			}
			else if(context.matches("http://10.243.1.27:8080.*")) {
				return "http://10.243.1.27:8080/agrfiscalservice";
			}
			else if(context.matches("http://187.6.249.66:8080.*")) {
				return "http://localhost:8080/agrfiscalservice";
			}
			else{//context.matches(URL_PORTAL_CADU + ".*")
				return URL_AGRFISCAL_WEB_SERVICE;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return URL_AGRFISCAL_WEB_SERVICE;
		}
	}

	public static Properties propertiesLoader() {
		return properties("sistema_pt.properties");
	}
	
	public static Properties propertiesLoaderURL() {
		return properties("url_pt.properties");
	}

	public static Properties properties(String name) {
		Properties prop = new Properties();
		try {
			String caminho = Message.class.getResource("").getPath().replaceAll("src/main/java", "src/main/resources") + name;
			File file = new File(caminho);
			FileInputStream fileInputStream = new FileInputStream(file);
			prop.load(fileInputStream);
			fileInputStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return prop;
	}
}