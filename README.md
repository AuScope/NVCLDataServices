# National Virtual Core Library Data Services
The National Virtual Core Library Data Services (NVCLDataServices) is a Java web application to serve NVCL content including images, spectra, data products, packaged TSG files, some basic plotting and other visualisations.  It works directly against a NVCL database schema (DDL in the sql folder) running in either MS SQL Server or Oracle.  It is tested with and therefore recommend to be run inside the tomcat 8 servlet container in production but can be tested using the jetty-maven-plugin.

## Prerequisites

### A NVCL Database

Use the build scripts in the sql folder to create a NVCL database and create a user account with the provided scripts with WebService permissions.

### Active MQ

You will need a running active MQ installation available from http://activemq.apache.org/ . For testing you can use the binary application but for production the Web Application (.war) will likely be easier to control as you can simply include it in your application container parallel to this package.

By Default the NVCLDataServices assume that activeMQ will be running on localhost and using the default activeMQ ports.  If not you will need to override the activeMQ.url setting in the config.properties file described bellow.

### The Spectral Geologist

To use the tsg file packaging component of the NVCLDataServices you will need TSG and a native database client installed.  TSG is available from http://thespectralgeologist.com/ . Simply running the installer is sufficient.  If you change the install location you will need to update the tsg.exepath setting in the config.properties file described bellow.

Install the [Oracle instant client](http://www.oracle.com/technetwork/topics/winsoft-085727.html) or [Microsoft SQL Server CLI driver](http://www.microsoft.com/en-us/download/details.aspx?id=16978).

Note for non-windows servers: WINE will need to be installed and the above will need to performed within it.  You will also need to install Xvfb and change the tsg.exepath setting in the config.properties file to point to a script file similar to this:

    #!/bin/bash
    xvfb-run -a wine C:\\Program\ Files\\The\ Spectral\ Geologist\\tsgeol7.exe $1
    exit

## Configuring

To configure you will need to edit the two properties files config.properties and jdbc.properties to set the runtime properties.  Template files have been provided with details of the expected content.  Copy them and remove the .template extension.

You will also need to download an appropriate jdbc driver depending on the database used (ojdbc*.jar for oracle or sqljdbc*.jar for MS SQL Server) available from the database vendors.  

If you're debugging with jetty-maven-plugin you will need to inject the driver into your maven cache. e.g.

    mvn install:install-file -Dfile=sqljdbc4.jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=3.0 -Dpackaging=JAR

and uncomment the correct project dependency from the pom.xml file.

Alternatively, If you're deploying the package to a servlet container you will need to ensure your driver is on the class path of the container or application.

## Building the package
Maven is used for building. To clean and build the .war file run:

    mvn -Dall clean package 

To create eclipse configuration files:

    mvn -Dall clean package eclipse:clean eclipse:eclipse -DskipTests=true


## Running

For debug purposes you can run the package with jetty-maven-plugin by calling

    mvn jetty:run

## License

This software is licensed under the CSIRO Open Source Software License Agreement (variation of the BSD / MIT License).  See LICENSE.txt.

## More Information

For more information please refer to the full system deployment instructions https://twiki.auscope.org/wiki/CoreLibrary/DeployingNVCLCustodianSystems .