@echo off
:: ================================================================
::  Finance Dashboard — Windows Startup Script
::  Ensures MySQL is running before launching the Spring Boot app.
:: ================================================================

echo 🔍 Checking MySQL status...

:: Check if the MySQL service is running
net start | findstr /i "MySQL" > nul
if %errorlevel% == 0 (
    echo ✅ MySQL service is already running.
) else (
    echo ⚡ MySQL is not running. Attempting to start...
    :: Needs Administrator privileges to start services
    net start MySQL
    if %errorlevel% neq 0 (
        echo ❌ Could not start MySQL automatically. 
        echo    Please start it manually via 'Services' or run this script as Admin.
    ) else (
        echo ✅ MySQL started successfully.
    )
)

echo.
echo 🚀 Starting Finance Dashboard...
echo    URL: http://localhost:8080
echo.

:: Run the Spring Boot application using the windows maven wrapper
call mvnw.cmd spring-boot:run
pause
