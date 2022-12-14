package ibInfra.vs;

import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;

import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class VSCommands implements IVSCommands {

    private WindowsService winService = new WindowsService();
    private IbService ibService = new IbService();


    @Override
    public void installVSWithIB(String installer) {
        winService.runCommandWaitForFinish(String.format(WindowsCommands.INSTALL_VS_WITH_IB, installer));
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
        changeFirstActivationRegistry();
    }

    @Override
    public void installVSWithoutIB(String installer) {
        winService.runCommandWaitForFinish(String.format(WindowsCommands.INSTALL_VS_WO_IB, installer));
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void upgradeVSWithIB(String installer) {
        upgradeVS(installer);
        winService.runCommandWaitForFinish(String.format(WindowsCommands.MODIFY_ADD_INCREDIBUILD, installer));
        winService.waitForProcessToFinish("vs_professional.exe");
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
        ibService.loadIbLicense(IbLicenses.VSTESTS_LIC);
        changeFirstActivationRegistry();
    }

    @Override
    public void upgradeVS(String installer) {
        winService.runCommandWaitForFinish(String.format(WindowsCommands.UPDATE_VS, installer));
        winService.waitForProcessToStart("vs_bootstrapper.exe");
        winService.waitForProcessToFinish("vs_bootstrapper.exe");
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void uninstallIbExtension() {

    }
    //TODO: remove this section when latest version hist VS
    private void changeFirstActivationRegistry(){
        int version = IIBService.getIbVersion();
        if (version > 2456)
            RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, "Software\\WOW6432Node\\Xoreax\\IncrediBuild\\Builder", RegistryKeys.VS_FIRST_ACTIVATION, "0");
        else
            RegistryService.setRegistryKey(HKEY_CURRENT_USER, "Software\\Xoreax\\IncrediBuild\\Builder", RegistryKeys.VS_FIRST_ACTIVATION, "0");

    }
}
