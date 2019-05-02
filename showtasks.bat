call runcrud
if "%ERRORLEVEL%" == 0 go to startchrome
echo.
echo runcrud.bat has errors - braking work
go to fail

:startchrome
echo starting Chrome
start chrome http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == 0 go to end
go to fail

:fail
echo There were errors

:end
echo Work is finished