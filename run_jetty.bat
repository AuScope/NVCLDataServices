@ECHO OFF
 
IF "%1" == "" GOTO BUILD_RUN
IF "%1" == "-b" GOTO BUILD
IF "%1" == "-r" GOTO RUN
GOTO END
 
:BUILD
call mvn -Dall clean package eclipse:clean eclipse:eclipse -DskipTests=false
GOTO END
 
:RUN
mvn jetty:run
GOTO END
 
:BUILD_RUN
call mvn -Dall clean package eclipse:clean eclipse:eclipse -DskipTests=true jetty:run
:END
