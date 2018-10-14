About Crawler
===============

This is a basic web crawler which provides a mapping between a particular url  and the pages they refer to under the same domain.

This crawler would parse evry web page under s given domain and provide links of other pages it links to.

Basic Requirements
====================

1) Java 8
2) Maven 3.5 or higher


Setup
======

1) Checkout the repository from below mentioned git URL : 
	https://github.com/AnkitAtBrillio/Crawler.git

2) Check the version of java on your system using command "java -version" and make sure JDK version is 8 or higher.

3) Check the maven version using command "mvn -version" and make sure its the required version or higher.

4) Open the "application.properties" file from location "Crawler\src\main\resources".

5) Update the property "crawl.domain.url" with the URL for which the mapping is required.

6) Run the project using command "mvn exec:java -Dexec.mainClass=\src\main\java\com\crawler\main\Crawler.java" from root directory.

7) Check the ouput file "mapping.txt" in root directory for the ouput.


Trade Offs
===========

1) Application runs bit slow for urls which have huge numbers of link to other pages or static content.

2) Provided more time the time complexity of the application could be imporoved.

3) The simple text output could be presented in graphical form using neo4j with appropriate time.

4) Test coverage which is currently a over 93% percent could be increased.