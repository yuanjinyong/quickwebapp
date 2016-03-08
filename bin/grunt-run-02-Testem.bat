@echo off
chcp 65001>NUL


cd /d %~dp0/..
set PROJECT_DIR=%cd%
set WEBAPP_DIR=%PROJECT_DIR%\src\main\webapp
set LOG_FILE=%~fn0.log

cd /d %WEBAPP_DIR%
echo grunt testem:run:unit 2>&1 |mtee /d/t %LOG_FILE%
grunt testem:run:unit 2>&1 |mtee /d/t /+ %LOG_FILE%
cd /d %PROJECT_DIR%

pause
