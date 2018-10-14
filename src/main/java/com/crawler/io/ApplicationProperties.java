package com.crawler.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.crawler.constants.ApplicationConstants;

public class ApplicationProperties {
	
	final static Logger logger = Logger.getLogger(ApplicationProperties.class);
	private static Properties properties;
	
	/*Method to read properties from properties file
	 * no parameters
	 * return void
	 */
	public boolean readProperties() {
		
		properties = new Properties();
		try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(ApplicationConstants.PROPERTIES_FILE_NAME)) {
			properties.load(inputStream);
		}catch (IOException exception) {
			logger.error("Properties file not found!!!");
		}
		return true;
	}

	public static  String getProperties(String propertyName) {
		return properties.get(propertyName).toString();
	}
	
	public static Properties getProperties() {
		return properties;
	}

}
