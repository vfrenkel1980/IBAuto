package ibInfra.ibService;

import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public interface IIBService {

    int cleanAndBuild(String command);

    void installIB(String version);

    static int getIbVersion(){
        String regVersion = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\builder", StaticDataProvider.RegistryKeys.VERSION);
        int version = Integer.parseInt(regVersion);
        version -= 1001000;
        return version;
    }

    String getIbConsoleInstallation(String version);

    void loadIbLicense();

    String getIbVsExtensionVersion();

    String getExpectedIbVsExtensionVersion();

    boolean verifyIbInstallation(int ibVersion);

    boolean verifyExtensionUpgrade(String oldVersion, String newVersion);

    boolean verifyIbUpgrade(int oldVersion, int newVersion);

    boolean verifyExtensionInstalled(String extensionVersion);

    boolean verifyIbServicesRunning();

    void uninstallIB(String version);
}
