@echo off
echo [INFO] Use maven install local jar to maven repo.

cd %~dp0
cd ../lib

set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m
call mvn install:install-file -Dfile=sqljdbc4.jar -Dpackaging=jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=4.0

cd bin
pause