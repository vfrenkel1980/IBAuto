package ibInfra.ibService;

import com.aventstack.extentreports.Status;
import com.sun.jna.platform.unix.solaris.LibKstat;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;

import java.io.IOException;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

public interface IIBService {
    
    String getIBinstallFolder();

    int cleanAndBuild(String command);

    void cleanAndBuildDontWaitTermination(String command);

    void installIB(String version);

    void installIBnoLoadedLicense(String version);

    void updateIB(String version);

    static int getIbVersion(){
        String regVersion = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.VERSION);
        int version = Integer.parseInt(regVersion);
        version -= 1001000;
        test.log(Status.INFO, "Running version of IB ----> " + version);
        return version;
    }

    String getIbConsoleInstallation(String version);

    void loadIbLicense(String license);

    void unloadIbLicense();

    String getIbVsExtensionVersion(String VsDevenvInstallPath);

    String getExpectedIbVsExtensionVersion();

    boolean verifyIbInstallation(int ibVersion);

    boolean verifyExtensionUpgrade(String oldVersion, String newVersion);

    boolean verifyIbUpgrade(int oldVersion, int newVersion);

    boolean verifyExtensionInstalled(String extensionVersion);

    boolean verifyIbServicesRunning();

    String findValueInPacketLog (String keyInLogFile)  throws IOException;

    void uninstallIB(String version);

    void disableVsMonitor();

    String getVSVersionFromOutputLog(String logPath);
}
