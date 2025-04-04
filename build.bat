@echo off
setlocal

:: Define your project directories
set PROJECT_DIR=%~dp0
set SRC_DIR=%PROJECT_DIR%src
set BIN_DIR=%PROJECT_DIR%bin
set LIB_DIR=%PROJECT_DIR%lib
set TMP_DIR=%PROJECT_DIR%tmp
set PROJECT_NAME="ember-mvc-1.0.0.jar"

:: Create bin and tmp directories if they don't exist
if not exist "%BIN_DIR%" mkdir "%BIN_DIR%"
if not exist "%TMP_DIR%" mkdir "%TMP_DIR%"

:: Copy only .java files from src to tmp
for /R "%SRC_DIR%" %%f in (*.java) do (
    xcopy "%%f" "%TMP_DIR%" /I
)

:: Compile the code
cd "%TMP_DIR%"
javac -d "%BIN_DIR%" -cp "%LIB_DIR%/*" "*.java" -parameters

:: Create the jar
cd "%BIN_DIR%"
jar cvf "%PROJECT_NAME%" * -C "%LIB_DIR%" . -C "%SRC_DIR%" .
move "%PROJECT_NAME%" "%PROJECT_DIR%"

:: Clean up tmp directory
rd /S /Q "%TMP_DIR%"    

endlocal


