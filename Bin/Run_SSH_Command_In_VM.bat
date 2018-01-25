@echo off
rem usage:
rem %1 - Guest Password
rem %2 - Guest user
rem %3 - Ip of the remote VM
rem %4 - The process commandline to run in the VM (must contain full path to the executable)

rem "C:\Program Files (x86)\PuTTY\plink.exe" -pw %1 %2@%3 %4


rem echo %0 >> c:\QA\output1
echo "c:\Program Files (x86)\PuTTY\plink.exe" -i C:\QA\Simulation\keys\1.ppk -pw %1 %2@%3 %4  >> c:\QA\output1
echo ------------------------------------- >> c:\QA\output1

"c:\Program Files (x86)\PuTTY\plink.exe" -t -i C:\QA\Simulation\keys\1.ppk -pw %1 %2@%3 %4

rem PLINK.EXE -ssh machinename -l user -pw password bash -l -c 'echo $PATH'