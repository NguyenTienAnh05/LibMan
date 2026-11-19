@echo off
REM Khoi dong Tomcat 9 voi che do debug (cong 8000)
REM De VSCode co the attach debugger

set "JAVA_HOME=C:\Program Files\Microsoft\jdk-25.0.5.21-hotspot"
set "JRE_HOME=C:\Program Files\Microsoft\jdk-25.0.5.21-hotspot"
set "CATALINA_HOME=C:\Tools\apache-tomcat-9.0.124"
set "CATALINA_BASE=C:\Tools\apache-tomcat-9.0.124"

set "JAVA_OPTS=%JAVA_OPTS% -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"

call "%CATALINA_HOME%\bin\startup.bat"
echo.
echo Tomcat dang chay. Truy cap: http://localhost:8080/LibMan_final/
echo Debug port: 8000
echo.
