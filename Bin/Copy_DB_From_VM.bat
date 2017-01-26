rem usage:
rem %1 - Guest user
rem %2 - Guest Password
rem %3 - path to the .vmx file of virtual machine
rem %4 - Path of the file to copy inside the VM
rem %5 - Path on the host to copy the file to.
"C:\Program Files (x86)\PuTTY\pscp.exe" -l xoreax -pw xoreax -r  %1:/etc/incredibuild/db %2

rem "C:\Program Files (x86)\VMware\VMware Workstation\vmrun.exe" -gu %1 -gp %2  copyFileFromGuestToHost %3 %4 %5
