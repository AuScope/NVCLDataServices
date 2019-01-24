# National Virtual Core Library Data Services
The National Virtual Core Library Data Services (NVCLDataServices) is a Java web application to serve NVCL content including images, spectra, data products, packaged TSG files, some basic plotting and other visualisations.  It works directly against a NVCL database schema (DDL in the sql folder) running in either MS SQL Server or Oracle.  It is tested with and therefore recommend to be run inside the tomcat 8 servlet container in production but can be tested using the jetty-maven-plugin.

## Prerequisites

### A NVCL Database

Use the build scripts in the sql folder to create a NVCL database and create a user account with the provided scripts with WebService permissions.

### Active MQ

An external Active MQ broker is no longer required as the embeded broker is now used by default.  You can override it by specifying a broker url in the application.properties file if you like.

### The Spectral Geologist

To use the tsg file packaging component of the NVCLDataServices you will need TSG and a native database client installed.  TSG is available from http://thespectralgeologist.com/ . Simply running the installer is sufficient.  If you change the install location you will need to update the tsg.exepath setting in the config.properties file described bellow.

Install the [Oracle instant client](http://www.oracle.com/technetwork/topics/winsoft-085727.html) or [Microsoft SQL Server CLI driver](http://www.microsoft.com/en-us/download/details.aspx?id=16978).

Note for non-windows servers: WINE will need to be installed and the above will need to performed within it.  You will also need to install Xvfb and change the tsg.exepath setting in the config.properties file to point to a script file similar to this:

    #!/bin/bash
    xvfb-run -a wine C:\\Program\ Files\\The\ Spectral\ Geologist\\tsgeol7.exe $1
    exit

## Configuring

To configure you will need to edit the properties file application.properties to set the runtime properties.  A template file has been provided with details of the expected content.

If you want to use an Oracle database as your data source you will need to register with Oracle's OTN and place your credentials in gradle.properties file to download the jdbc driver from Oracles secure repository.  


## Building the package
Gradle is used for building. To clean and build the .jar file run:

    gradlew build 

## Running

For debug purposes you can call the "Debug NVCLDataServices" launch configuration from VSCode.  Or directly call:

    java -jar ../build/libs/NVCLDataServices-2.1.0.jar

## License

This software is licensed under the CSIRO Open Source Software License Agreement (variation of the BSD / MIT License).  See LICENSE.txt.

## More Information

For more information please refer to the full system deployment instructions https://confluence.csiro.au/display/AusGRID/Deploying+NVCL+Data+Custodian+Systems.