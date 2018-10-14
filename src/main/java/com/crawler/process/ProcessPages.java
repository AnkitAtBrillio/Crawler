package com.crawler.process;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.constants.ApplicationConstants;
import com.crawler.io.ApplicationProperties;

public class ProcessPages {

	final static Logger logger = Logger.getLogger(ApplicationProperties.class);
	private Map<String, List<String>> urlMapping = new LinkedHashMap<>();
	private List<String> visitedUrls = new ArrayList<>();
	
	
	public Map<String, List<String>> getUrlMapping() throws IOException, URISyntaxException {
		String rootUrl = ApplicationProperties.getProperties(ApplicationConstants.PROPERTY_DOMAIN_URL);
		processPage(rootUrl);
		return urlMapping;
	}

	private void processPage(String url) throws IOException, URISyntaxException {

		if (visitedUrls.indexOf(url) >= 0) {
			return;
		}
		visitedUrls.add(url);
		List<String> childUrls = new ArrayList<>();
		if (checkForSameDomain(url)) {
			Connection connection = Jsoup.connect(ApplicationProperties.getProperties(ApplicationConstants.PROPERTY_DOMAIN_URL));
			connection.timeout(0);
			connection.ignoreContentType(ApplicationConstants.CONSTANT_BOOLEAN_TRUE);
			Document page = connection.get();
			Elements hrefs = page.select(ApplicationConstants.CONSTANT_HREF_TAG);
			if (null != hrefs) {
				for (Element href : hrefs) {
					String childUrl = href.attr(ApplicationConstants.CONSTANT_HREF_VALUE);
					if (childUrls.indexOf(childUrl) < 0) {
						childUrls.add(childUrl);
					}
				}

				urlMapping.put(url, childUrls);
			} else {
				logger.info("No same domain child urls for  " + url);
				urlMapping.put(url, childUrls);
			}

			if (childUrls.size() > 0) {
				for (String childUrl : childUrls) {
					try {
					processPage(childUrl);
					}catch(Exception e) {
						logger.error("Error orccured while processing url : " + childUrl + ". and the error is :" + e.getMessage());
					}
				}
			}

		}
	}

	public boolean checkForSameDomain(String url) throws URISyntaxException {

		if (null == url) {
			return false;
		}
		URI currentUrl = new URI(url);
		String currentDomain = currentUrl.getHost();
		URI requiredUrl = new URI(ApplicationProperties.getProperties(ApplicationConstants.PROPERTY_DOMAIN_URL));
		String requiredDomain = requiredUrl.getHost();
		if(!requiredDomain.equals(currentDomain)) {
			return false;
		}
		
		return true;
	}

}
