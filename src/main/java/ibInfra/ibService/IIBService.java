package ibInfra.ibService;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.Locations;
import frameworkInfra.utils.StaticDataProvider.RegistryKeys;
import org.testng.ITestContext;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

public interface IIBService {

    String getIBInstallFolder();

    int build(String command);

    int cleanAndBuild(String command);

    void cleanAndBuildDontWaitTermination(String command);

    void installIB(String version, String license);

    int installSingleUseIB(String version);

    int downgradeEntToPro(String version);

    int installIB(String version);

    void updateIB(String version);

    int upgradeToEnt();

    static int getIbVersion() {
        int version = 0;
        if (RegistryService.doesKeyExist(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT)) {
            String regVersion = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", RegistryKeys.VERSION);
            version = Integer.parseInt(regVersion);
            version -= 1001000;
            if (test != null)
                test.log(Status.INFO, "Installed IB version -----> " + version);
        }
        return version;
    }

    String getIbConsoleInstallation(String version);

    String getIbConsoleInstallationForEnt();

    void loadIbLicense(String license);

    void unloadIbLicense();

    String getIbVsExtensionVersion(String VsDevenvInstallPath);

    String getExpectedIbVsExtensionVersion();

    boolean verifyIbServicesRunning(boolean agent, boolean coord);

    String findValueInPacketLog(String keyInLogFile) throws IOException;

    void uninstallIB(String version);

    void disableVsMonitor();

    String getVSVersionFromOutputLog(String logPath);

    void  customPackAllocationOn();

    boolean isLicenseLoaded();

    String getCoordinator();

    boolean verifyAvoidLocal(String filePath);

    void decryptSQLiteDB(String revision);

    void openBuildMonitor();

    void generateCustomReport(ITestContext context);

    void agentServiceStart();

    void coordServiceStart();

    void agentServiceStop();

    void unsubscribeAllMachines(String coord) throws IOException, SAXException, ParserConfigurationException;

    int getNumberOfMachinesParticipateInBuild(String initiator);

    Document generateStatusReport() throws IOException, SAXException, ParserConfigurationException;



}
