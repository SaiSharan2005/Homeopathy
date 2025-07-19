@echo off
echo ========================================
echo Hospital Management System - Test Runner
echo ========================================

:menu
echo.
echo Select test option:
echo 1. Run all tests
echo 2. Run unit tests only
echo 3. Run integration tests only
echo 4. Run controller tests only
echo 5. Run tests with coverage report
echo 6. Run tests with debug logging
echo 7. Clean and run all tests
echo 8. Exit
echo.
set /p choice="Enter your choice (1-8): "

if "%choice%"=="1" goto runAllTests
if "%choice%"=="2" goto runUnitTests
if "%choice%"=="3" goto runIntegrationTests
if "%choice%"=="4" goto runControllerTests
if "%choice%"=="5" goto runTestsWithCoverage
if "%choice%"=="6" goto runTestsWithDebug
if "%choice%"=="7" goto cleanAndRunTests
if "%choice%"=="8" goto exit
echo Invalid choice. Please try again.
goto menu

:runAllTests
echo.
echo Running all tests...
mvn test
goto end

:runUnitTests
echo.
echo Running unit tests only...
mvn test -Dtest="*Test" -DfailIfNoTests=false
goto end

:runIntegrationTests
echo.
echo Running integration tests only...
mvn test -Dtest="*IntegrationTest" -DfailIfNoTests=false
goto end

:runControllerTests
echo.
echo Running controller tests only...
mvn test -Dtest="*ControllerTest" -DfailIfNoTests=false
goto end

:runTestsWithCoverage
echo.
echo Running tests with coverage report...
mvn clean test jacoco:report
echo Coverage report generated in target/site/jacoco/
goto end

:runTestsWithDebug
echo.
echo Running tests with debug logging...
mvn test -Dlogging.level.com.G19.hospital=DEBUG
goto end

:cleanAndRunTests
echo.
echo Cleaning and running all tests...
mvn clean test
goto end

:end
echo.
echo Test execution completed!
echo Check the output above for results.
echo.
pause
goto menu

:exit
echo.
echo Goodbye!
exit /b 0 