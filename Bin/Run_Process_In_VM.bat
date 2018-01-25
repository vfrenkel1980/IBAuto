rem usage:
rem %1 - Guest user
rem %2 - Guest Password
rem %3 - path to the .vmx file of virtual machine
rem %4 - The process commandline to run in the VM (must contain full path to the executable)

"C:\Program Files (x86)\VMware\VMware Workstation\vmrun.exe" -gu %1 -gp %2  runProgramInGuest %3 %4

