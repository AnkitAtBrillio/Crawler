package com.cawler.main;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.crawler.constants.ApplicationConstants;
import com.crawler.io.ApplicationProperties;
import com.crawler.main.Crawler;
import com.crawler.process.ProcessPages;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class, ApplicationProperties.class, Crawler.class, ProcessPages.class})
public class Test {
	
	public static final String PARENT_URL = "http://www.test.com";
	
	public static final String CHILD_URL = "http://www.childUrl.com";

	private static final String TEST_OUTPUT_FILE = "testOutput.txt";
	
	
	
	@org.junit.Test
	public void testCrawler() throws Exception {
		
		//Initializing mocks
		Document document = PowerMockito.mock(Document.class);
		Elements elements = PowerMockito.mock(Elements.class);
		Element element = PowerMockito.mock(Element.class);
		List<Element> realElements = new ArrayList<>();
		realElements.add(element);
		Connection connection = PowerMockito.mock(Connection.class);
		Properties properties = PowerMockito.mock(Properties.class);
		ApplicationProperties applicationProperties = PowerMockito.mock(ApplicationProperties.class);
		PowerMockito.when(elements.iterator()).thenReturn(realElements.iterator());		
    	PowerMockito.whenNew(ApplicationProperties.class).withNoArguments().thenReturn(applicationProperties);
		PowerMockito.mockStatic(Jsoup.class);
		PowerMockito.mockStatic(ApplicationProperties.class);
		PowerMockito.when(Jsoup.connect(Mockito.anyString())).thenReturn(connection);
		PowerMockito.when(ApplicationProperties.getProperties(ApplicationConstants.PROPERTY_DOMAIN_URL)).thenReturn(PARENT_URL);
		PowerMockito.when(ApplicationProperties.getProperties(ApplicationConstants.PROPERTY_OUTPUT_FILE_NAME)).thenReturn(TEST_OUTPUT_FILE);
		PowerMockito.when(ApplicationProperties.getProperties()).thenReturn(properties);
		PowerMockito.when(connection.get()).thenReturn(document);
		PowerMockito.when(document.select(Mockito.anyString())).thenReturn(elements);
		PowerMockito.when(element.attr(Mockito.anyString())).thenReturn(CHILD_URL);
		PowerMockito.when(applicationProperties.readProperties()).thenReturn(ApplicationConstants.CONSTANT_BOOLEAN_TRUE);
		String[] args = new String[0];
		Crawler.main(args);	
		
		
		File file = new File(TEST_OUTPUT_FILE);
		assertTrue(file.exists());
	}
	
}
