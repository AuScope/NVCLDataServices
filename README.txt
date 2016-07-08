# National Virtual Core Library Data Services
The National Virtual Core Library Data Services (NVCLDataServices) is a Java web application to serve NVCL content including images, spectra, data products, packaged TSG files, some basic plotting and other visualisations.  It works directly against a NVCL database schema (DDL in the sql folder) running in either MS SQL Server or Oracle.  It is tested with and therefor recommend to be run inside the tomcat 8 servlet container in production but can be tested using the jetty-maven-plugin.

## Creating the NVCL Database

Use the build scripts in the sql folder to create a NVCL database

## Building the package
Maven is used for building. To clean and build the .war file run:

    mvn -Dall clean package 

To create eclipse configuration files:

    mvn -Dall clean package eclipse:clean eclipse:eclipse -DskipTests=false

## Configuring

Once built you will need to edit the two properties files config.properties and jdbc.properties to set the runtime properties.  Template files have been provided with details of the expected content.

You will also need to download an appropriate jdbc driver depending on the database used (ojdbc*.jar for oracle or sqljdbc*.jar for MS SQL Server) available from the database vendors.  

If you're debugging with jetty-maven-plugin you will need to inject the driver into your maven cache. e.g.

mvn install:install-file -Dfile=sqljdbc4.jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=3.0 -Dpackaging=JAR

and uncomment the correct project dependency from the pom.xml file.

Alternatively, If you're deploying the package to a servlet container you will need to ensure your driver is on the class path of the container or application.

## Running

For debug purposes you can run the package with jetty-maven-plugin by calling

    mvn jetty:run

## License

This software is licensed under the CSIRO Open Source Software License Agreement (variation of the BSD / MIT License).  See LICENSE.txt.

## More Information

For more information please refer to the full system deployment instructions https://twiki.auscope.org/wiki/CoreLibrary/DeployingNVCLCustodianSystems .