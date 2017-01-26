@echo off 

Rem ***********************
Rem flag for ssh commands (--f, -d1 etc.)
Rem ***********************

SET IB_flag1=--f
SET IB_flag3=--f
SET IB_flag4=--f

Rem ***********************
Rem IP Adresses resolution
Rem ***********************
REM VM-U14Coor-22 on Host-22
SET COORDINATOR=192.168.10.106

REM ubuntu12-n22 on Host-22
SET INITIATOR1=192.168.10.46

REM VM-Sim-Cent65 on Host-22 ???
rem SET INITIATOR2=192.168.10.74

REM Ubu1204-22 on Host-22 (Android initiator)
SET INITIATOR3=192.168.10.154

REM VM-U14In-22 on Host-22 (open-office and Android initiator)
SET INITIATOR4=192.168.10.66

rem=======================================
REM VM-U14-Gcc on Host-22 (gcc initiator)
SET INITIATOR5=192.168.10.

REM VM-U14-NDK on Host-22 (adnroid-NDK initiator)
SET INITIATOR6=192.168.10.

REM VM-centos_7.1 on Host-22 (oracle initiator)
SET INITIATOR7=192.168.10.

REM VM-SteamOS on Host-22 (kernel initiator)
SET INITIATOR8=192.168.10.

rem=======================================

REM Centos6-V on Host-7
SET HELPER1=192.168.10.162

REM VM-U14Hlp3 On HOST-7
SET HELPER2=192.168.10.163

REM CentOS_7-5 on Host srv
SET HELPER3=192.168.10.164

REM VM-U14Hlp3 on Host srv
SET HELPER4=192.168.10.171

REM Initiator/Coordinator Host-22
SET SIMHOST1=192.168.10.146

REM Helper machines () HOST-7
SET SIMHOST2=192.168.10.85

REM Helper machines () SRV
SET SIMHOST3=192.168.10.15


Rem ******************
Rem VMX FIle Locationauthorized_keys
Rem ******************

SET COORDINATOR-VMXFILE=C:\VMs\VM-U14Coor-22\VM-U14-Template2.vmx

REM ubuntu12-n22
SET INITIATOR1-VMXFILE=C:\VMs\ubuntu12-n22\Ubuntu_12_50Gb.vmx

REM VM-sim-Cent6.8
rem SET INITIATOR2-VMXFILE=E:\VMs\VMt-centOS6.8\VMt-centOS6.6.vmx

REM Ubu1204-22 on Host-22 (Android initiator)
SET INITIATOR3-VMXFILE=C:\VMs\Ubu1204-22\Ubu-1204.vmx

REM VM-U14In-22 on Host-22 (open-office and Android initiator)
SET INITIATOR4-VMXFILE=E:\VMs\VM-U14In-22\VM-U14In-22.vmx

REM VM-U14Hlp3
SET HELPER1-VMXFILE=VM-U14Hlp3\VM-U14-Template2.vmx

REM CentOS6-v
SET HELPER2-VMXFILE=CentOS6-v\CentOS6-v.vmx

REM CentOS_7-5
SET HELPER3-VMXFILE=CentOS_7-5\CentOS_7-5.vmx


REM VM-U14Hlp3
SET HELPER4-VMXFILE=VM-U14Hlp3\VM-U14-Template2.vmx


start "C:\Program Files (x86)\Automise 3\Automise.exe" C:\qa\simulation\Bin\SImulation_Linux_Host22.atp3