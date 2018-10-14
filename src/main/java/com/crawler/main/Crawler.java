package com.crawler.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.crawler.constants.ApplicationConstants;
import com.crawler.io.ApplicationProperties;
import com.crawler.process.ProcessPages;

public class Crawler {

	final static Logger logger = Logger.getLogger(Crawler.class);
	
	public static void main(String[] args) {
				
		ApplicationProperties applicationProperties = new ApplicationProperties();
		ProcessPages processPages = new ProcessPages();
		applicationProperties.readProperties();
		if(null == ApplicationProperties.getProperties()) {
			logger.error("Not able to load properties file. Exiting application");
			return;
		}
		
		try {
		Map<String,List<String>> urlMapping = processPages.getUrlMapping();
		if(urlMapping.isEmpty()) {
			logger.error("No url mapping is available");
			return;
		}
			
		boolean outputWritten = writeOutput(urlMapping);
		if(outputWritten) {
			logger.info("Otuput is available in file : " + ApplicationProperties.getProperties(ApplicationConstants.PROPERTY_OUTPUT_FILE_NAME)); 
		}
		}catch(IOException | URISyntaxException exception) {
			logger.error(exception.getMessage());
			logger.info("Exiting application with error");
		}
		

	}
	
	
	public static boolean writeOutput(Map<String, List<String>> urlMapping) throws IOException {
		
		
		File outputFile = new File(ApplicationProperties.getProperties(ApplicationConstants.PROPERTY_OUTPUT_FILE_NAME));
		try(FileWriter fstream = new FileWriter(outputFile);
				BufferedWriter out =  new BufferedWriter(fstream);	){
			
		for(Map.Entry<String, List<String>> mapping : urlMapping.entrySet()) {
			out.write("Parent Url : " + mapping.getKey() + System.getProperty("line.separator"));
			List<String> childUrls = mapping.getValue();
			out.write("Linked Urls : " + System.getProperty("line.separator"));
			for(String childUrl : childUrls) {
				out.write(childUrl);
				out.write(System.getProperty("line.separator"));
			}
			
			out.write("====================================================================================");
			out.write(System.getProperty("line.separator"));
		}
		}catch(IOException e) {
			logger.error("error occured while updating output file : " + e.getMessage());
		}
		return true;
	}

}
