<#
Prerequisite:
- Set RDP with Save Creds On the VM/Host
- Set Path to the RDP Loctaion: Example: C:\Users\Admin\Desktop\performance.rdp
#>
Invoke-Item C:\Users\Admin\Desktop\performance.rdp
Start-Sleep -Seconds 120
Get-Process | Where-Object {$_.ProcessName -eq "mstsc"} | stop-process