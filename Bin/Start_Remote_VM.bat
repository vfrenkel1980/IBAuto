@echo off
rem usage:
rem %1 - IP of the remote Host machine
rem %2 - path to the remote .vmx file (note that the path is case sensetive), e.g. 	VM-Sim-ubu1404\Ubuntu_14.vmx

rem=======================================================================================================
rem echo %0 >> c:\QA\output1
rem echo "C:\Program Files (x86)\VMware\VMware Workstation\vmrun.exe" -T ws-shared -h https://%1:443/sdk -u administrator -p 4illumination start  "[ha-datacenter/standard] %2" gui  >> c:\QA\output1
rem echo ------------------------------------- >> c:\QA\output1
rem=======================================================================================================
"C:\Program Files (x86)\VMware\VMware Workstation\vmrun.exe" -T ws-shared -h https://%1:443/sdk -u administrator -p 4illumination start  "[ha-datacenter/standard] %2" gui 