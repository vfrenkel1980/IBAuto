rem usage:
rem %1 - The path to the .db file (no quotes allowed)
rem %2 - a query wrapped in double quotes - e.g -  "select * from report_custom_queries"


"C:\QA\Simulation\Bin\sqlite3.exe" %1 %2  
