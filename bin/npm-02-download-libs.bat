@echo off


chcp 65001>NUL
set PYTHON=F:\Project\tools\python\python-2.7.11\python.exe
if exist T:\python\python-2.7.11\python.exe (
    set PYTHON=T:\python\python-2.7.11\python.exe
)


cd /d %~dp0/..
set PROJECT_DIR=%cd%
set WEBAPP_DIR=%PROJECT_DIR%\src\main\webapp
set LOG_FILE=%~fn0.log
echo 日志文件：%LOG_FILE%
echo.
echo.



cd /d %WEBAPP_DIR%
echo 下载前台依赖的库 2>&1 |mtee /d/t %LOG_FILE%


echo 1. 下载Lo-Dash 2>&1 |mtee /d/t /+ %LOG_FILE%
echo npm install lodash --save 2>&1 |mtee /d/t /+ %LOG_FILE%
npm install lodash --save 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%


echo 2. 下载jQuery 2>&1 |mtee /d/t /+ %LOG_FILE%
echo npm jquery lodash --save 2>&1 |mtee /d/t /+ %LOG_FILE%
npm jquery lodash --save 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%


start %LOG_FILE%
cd /d %PROJECT_DIR%


pause
