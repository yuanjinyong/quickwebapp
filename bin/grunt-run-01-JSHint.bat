@echo off
chcp 65001>NUL


cd /d %~dp0/..
set PROJECT_DIR=%cd%
set WEBAPP_DIR=%PROJECT_DIR%\src\main\webapp
set LOG_FILE=%~fn0.log

cd /d %WEBAPP_DIR%
echo 检查javascript代码错误和问题 2>&1 |mtee /d/t %LOG_FILE%
echo grunt --no-color jshint 2>&1 |mtee /d/t %LOG_FILE%
grunt --no-color jshint 2>&1 |mtee /d/t /+ %LOG_FILE%
cd /d %PROJECT_DIR%

pause
