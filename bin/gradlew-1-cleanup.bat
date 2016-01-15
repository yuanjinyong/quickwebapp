@echo off


cd /d %~dp0/..
set PROJECT_DIR=%cd%
set LOG_FILE=%~fn0.log
set GRADLEW_BAT=%PROJECT_DIR%\gradlew.bat
set GRADLEW_OPTS=--info -s

echo call %GRADLEW_BAT% %GRADLEW_OPTS% clean cleanEclipse 2^>^&1 ^|mtee /d/t %LOG_FILE%
call %GRADLEW_BAT% %GRADLEW_OPTS% clean cleanEclipse 2>&1 |mtee /d/t %LOG_FILE%


pause
