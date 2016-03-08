@echo off
chcp 65001>NUL


MD "%WinDir%\System32\test_permissions" 2>NUL||(Echo 请使用右键管理员身份运行&&Pause >NUL&&Exit)
RD "%WinDir%\System32\test_permissions" 2>NUL


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
echo 初始化前台开发环境 2>&1 |mtee /d/t %LOG_FILE%


echo 1. 安装Grunt CLI 2>&1 |mtee /d/t /+ %LOG_FILE%
echo npm install -g grunt-cli 2>&1 |mtee /d/t /+ %LOG_FILE%
npm install -g grunt-cli 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%


echo 2. 安装Grunt 2>&1 |mtee /d/t /+ %LOG_FILE%
echo npm install grunt --save-dev 2>&1 |mtee /d/t /+ %LOG_FILE%
npm install grunt --save-dev 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%


echo 3. 安装JSHint 2>&1 |mtee /d/t /+ %LOG_FILE%
echo npm install grunt-contrib-jshint --save-dev 2>&1 |mtee /d/t /+ %LOG_FILE%
npm install grunt-contrib-jshint --save-dev 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%


echo 4. 安装Testem 2>&1 |mtee /d/t /+ %LOG_FILE%
echo npm install grunt-contrib-testem --save-dev 2>&1 |mtee /d/t /+ %LOG_FILE%
npm install grunt-contrib-testem --save-dev 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%


echo 5. 安装Sinon 2>&1 |mtee /d/t /+ %LOG_FILE%
echo npm install sinon --save-dev 2>&1 |mtee /d/t /+ %LOG_FILE%
npm install sinon --save-dev 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%


echo 6. 安装PhantomJS browser 2>&1 |mtee /d/t /+ %LOG_FILE%
echo npm install -g phantomjs 2>&1 |mtee /d/t /+ %LOG_FILE%
npm install -g phantomjs 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%
echo. 2>&1 |mtee /d/t /+ %LOG_FILE%


start %LOG_FILE%
cd /d %PROJECT_DIR%


pause
