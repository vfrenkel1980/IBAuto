@echo off
rem usage: 
rem %1 - build caption (i.e. Kernel)
rem %2 - path of report db 	(incredibuildBuildReport.db) 
rem %3 - path of results db (auto_tests_results_new.db) 

rem cd C:\QA\Simulation_Results\%1rem make an empty results db
copy C:\QA\Simulation_Results\linux_automation\auto_tests_results - empty.db %3

rem run python script 
C:\Python26\python.exe C:\QA\Simulation_Results\linux_automation\auto_qa_db.py after %1 %2 %3 


rem C:\Python26\python.exe C:\QA\Simulation_Results\linux_automation\auto_qa_db.py after Kernel 
rem C:\QA\Simulation_Results\%1\incredibuildBuildReport.db C:\QA\Simulation_Results\%1\auto_tests_results_new.db


