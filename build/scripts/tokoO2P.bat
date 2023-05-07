@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  tokoO2P startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and TOKO_O2_P_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\tokoO2P-1.0-SNAPSHOT.jar;%APP_HOME%\lib\javafx-swing-18-win.jar;%APP_HOME%\lib\javafx-swing-18.jar;%APP_HOME%\lib\controlsfx-11.0.0.jar;%APP_HOME%\lib\javafx-web-18-win.jar;%APP_HOME%\lib\javafx-web-18.jar;%APP_HOME%\lib\javafx-media-18-win.jar;%APP_HOME%\lib\javafx-media-18.jar;%APP_HOME%\lib\javafx-fxml-18-win.jar;%APP_HOME%\lib\javafx-controls-18-win.jar;%APP_HOME%\lib\javafx-controls-18.jar;%APP_HOME%\lib\javafx-graphics-18-win.jar;%APP_HOME%\lib\javafx-graphics-18.jar;%APP_HOME%\lib\javafx-base-18-win.jar;%APP_HOME%\lib\javafx-base-18.jar;%APP_HOME%\lib\junit-jupiter-params-5.8.1.jar;%APP_HOME%\lib\junit-jupiter-engine-5.8.1.jar;%APP_HOME%\lib\junit-jupiter-api-5.8.1.jar;%APP_HOME%\lib\junit-platform-engine-1.8.1.jar;%APP_HOME%\lib\junit-platform-commons-1.8.1.jar;%APP_HOME%\lib\junit-jupiter-5.8.1.jar;%APP_HOME%\lib\testng-7.1.0.jar;%APP_HOME%\lib\json-simple-1.1.1.jar;%APP_HOME%\lib\jaxp-api-1.4.5.jar;%APP_HOME%\lib\jackson-dataformat-xml-2.13.0.jar;%APP_HOME%\lib\jackson-core-2.13.0.jar;%APP_HOME%\lib\jackson-annotations-2.13.0.jar;%APP_HOME%\lib\jackson-databind-2.13.0.jar;%APP_HOME%\lib\xercesImpl-2.12.1.jar;%APP_HOME%\lib\jaxb-api-2.3.1.jar;%APP_HOME%\lib\jaxb-runtime-2.3.3.jar;%APP_HOME%\lib\fontawesomefx-fontawesome-4.7.0-9.1.2.jar;%APP_HOME%\lib\fontawesomefx-commons-9.1.2.jar;%APP_HOME%\lib\pdfbox-tools-2.0.24.jar;%APP_HOME%\lib\pdfbox-debugger-2.0.24.jar;%APP_HOME%\lib\pdfbox-2.0.24.jar;%APP_HOME%\lib\itextpdf-5.5.13.jar;%APP_HOME%\lib\jfreechart-1.5.3.jar;%APP_HOME%\lib\jcommander-1.72.jar;%APP_HOME%\lib\guice-4.1.0-no_aop.jar;%APP_HOME%\lib\snakeyaml-1.21.jar;%APP_HOME%\lib\junit-4.10.jar;%APP_HOME%\lib\xml-apis-1.4.01.jar;%APP_HOME%\lib\woodstox-core-6.2.6.jar;%APP_HOME%\lib\stax2-api-4.2.1.jar;%APP_HOME%\lib\javax.activation-api-1.2.0.jar;%APP_HOME%\lib\jakarta.xml.bind-api-2.3.3.jar;%APP_HOME%\lib\txw2-2.3.3.jar;%APP_HOME%\lib\istack-commons-runtime-3.0.11.jar;%APP_HOME%\lib\jakarta.activation-1.2.2.jar;%APP_HOME%\lib\fontbox-2.0.24.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\opentest4j-1.2.0.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\aopalliance-1.0.jar;%APP_HOME%\lib\guava-19.0.jar;%APP_HOME%\lib\hamcrest-core-1.1.jar


@rem Execute tokoO2P
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %TOKO_O2_P_OPTS%  -classpath "%CLASSPATH%" com.o2pjualan.Main %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable TOKO_O2_P_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%TOKO_O2_P_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
