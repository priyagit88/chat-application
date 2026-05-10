@echo off
set "JAVA_HOME=%~dp0jdk_inst\jdk-17.0.18+8"
set "PATH=%JAVA_HOME%\bin;%PATH%"
java ChatServer
