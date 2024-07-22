@echo off
set mypath=%~dp0
cd /d "%mypath%"
CALL :RESOLVE "%mypath%" PARENT_ROOT
REM CALL :RESOLVE "%mypath%\..\" PARENT_ROOT

set SM_HOME=%PARENT_ROOT%

cd /d %SM_HOME%

rem Set JAVA_HOME and add the local JVM to the PATH
set JAVA_HOME=%SM_HOME%\app\jdk1.8.0_144
set JRE=%CD%\app\jre1.8.0_144\bin\server\jvm.dll

REM prunsrv.exe //IS//SMediaTest --DisplayName="Solo Media test" --Description="Tache cron solo media " --Startup=auto --Install=%CD%\prunsrv.exe --Jvm=%JRE% --Classpath=SMedia-0.1.jar --StartMode=jvm --StartClass=com.softit.smedia.Bootstrap --StartMethod=start --StartParams=start --StopMode=jvm --StopClass=com.softit.smedia.Bootstrap --StopMethod=stop --StopParams=stop --StdOutput=auto --StdError=auto --LogPath=%SM_HOME%logs --LogLevel=Debug
 prunsrv.exe //IS//SMediaTest --DisplayName="Solo Media" --Description="Tache cron solo media " --Startup=auto --Install=%CD%\prunsrv.exe --Jvm=auto --Classpath=SMedia-0.1.jar --StartMode=jvm --StartClass=com.softit.smedia.Bootstrap --StartMethod=start --StartParams=start --StopMode=jvm --StopClass=com.softit.smedia.Bootstrap --StopMethod=stop --StopParams=stop --StdOutput=auto --StdError=auto --LogPath=logs --LogLevel=Debug