@echo off
:: ================================================================
::  Finance Dashboard — Windows Startup Script
::  Ensures MySQL is running before launching the Spring Boot app.
:: ================================================================

echo 🔍 Checking MySQL connectivity on port 3306...

:: Use PowerShell to check port 3306
powershell -Command "try { $c = New-Object System.Net.Sockets.TcpClient('localhost', 3306); if ($c.Connected) { exit 0 } } catch { exit 1 }"

if %errorlevel% == 0 (
    echo ✅ MySQL is reachable.
) else (
    echo ⏳ MySQL is not reachable on port 3306.
    echo    Please ensure MySQL is running and the 'finance_db' database is created.
    echo    (Trying to start anyway...)
)

echo.
echo 🚀 Starting Finance Dashboard...
echo    URL: http://localhost:8080
echo.

:: Run the Spring Boot application using the windows maven wrapper
call mvnw.cmd spring-boot:run
pause
