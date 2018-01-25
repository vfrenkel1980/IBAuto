
rem %1 - path (path of incredibuildBuildReport.db , output will be inserted here in Result.db) 
rem %2 - build caption (i.e. Kernel)

rem RunQueriesMan.bat C:\QA\Simulation_Results\Simulation_479\ubuntu12-n20\db Samba

cd %1
del Result.db
copy C:\QA\Simulation_Results\linux_automation\auto_tests_results_empty.db %1\Result.db
C:\Python26\python.exe C:\QA\Simulation_Results\linux_automation\auto_qa_db.py after %2 %1\incredibuildBuildReport.db %1\Result.db



