rem %1 - Guest user
rem %2 - Guest Password
rem %3 - path to the .vmx file of virtual machine (must be running!)

"C:\Program Files (x86)\VMware\VMware Workstation\vmrun.exe" -gu %1 -gp %1  listProcessesInGuest %3
