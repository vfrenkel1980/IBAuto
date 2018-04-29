package ibInfra.vs;

import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;

import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;

public class VSCommands implements IVSCommands {

    private WindowsService winService = new WindowsService();
    private IbService ibService = new IbService();


    @Override
    public void installVSWithIB() {
        winService.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.INSTALL_VS_WITH_IB);
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
        RegistryService.setRegistryKey(HKEY_CURRENT_USER, "Software\\Xoreax\\IncrediBuild\\Builder", StaticDataProvider.RegistryKeys.VS_FIRST_ACTIVATION, "0");
    }

    @Override
    public void installVSWithoutIB() {
        winService.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.INSTALL_VS_WO_IB);
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void upgradeVSWithIB() {
        upgradeVS();
        winService.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.MODIFY_ADD_INCREDIBUILD);
        winService.waitForProcessToFinish("vs_professional.exe");
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
        ibService.loadIbLicense(StaticDataProvider.IbLicenses.VSTESTS_LIC);
        RegistryService.setRegistryKey(HKEY_CURRENT_USER, "Software\\Xoreax\\IncrediBuild\\Builder", StaticDataProvider.RegistryKeys.VS_FIRST_ACTIVATION, "0");
    }

    @Override
    public void upgradeVS() {
        winService.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.UPDATE_VS_WITH_IB);
        winService.waitForProcessToStart("vs_bootstrapper.exe");
        winService.waitForProcessToFinish("vs_bootstrapper.exe");
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void installVSPreviewWithIB() {
        winService.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.INSTALL_VSPREVIEW_WITH_IB);
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
        RegistryService.setRegistryKey(HKEY_CURRENT_USER, "Software\\Xoreax\\IncrediBuild\\Builder", StaticDataProvider.RegistryKeys.VS_FIRST_ACTIVATION, "0");
    }

    @Override
    public void installVSPreviewWithoutIB() {
        winService.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.INSTALL_VSPREVIEW_WO_IB);
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void upgradeVSPreviewWithIB() {
        upgradeVSPreview();
        winService.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.MODIFY_PREVIEW_ADD_INCREDIBUILD);
        winService.waitForProcessToFinish("vs_professional_preview.exe");
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
        ibService.loadIbLicense(StaticDataProvider.IbLicenses.VSTESTS_LIC);
        RegistryService.setRegistryKey(HKEY_CURRENT_USER, "Software\\Xoreax\\IncrediBuild\\Builder", StaticDataProvider.RegistryKeys.VS_FIRST_ACTIVATION, "0");
    }

    @Override
    public void upgradeVSPreview() {
        winService.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.UPDATE_VSPREVIEW);
        winService.waitForProcessToStart("vs_bootstrapper.exe");
        winService.waitForProcessToFinish("vs_bootstrapper.exe");
        winService.waitForProcessToStart("vs_installer.exe");
        winService.waitForProcessToFinish("vs_installer.exe");
    }

    @Override
    public void uninstallIbExtension() {

    }
}
