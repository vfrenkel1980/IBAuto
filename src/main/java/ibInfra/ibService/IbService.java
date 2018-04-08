package ibInfra.ibService;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.CustomJsonParser;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.windowscl.WindowsService;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;


public class IbService extends TestBase implements IIBService {

    private WindowsService winService = new WindowsService();

    @Override
    public int cleanAndBuild(String command) {
        winService.runCommandWaitForFinish(String.format(command, ProjectsCommands.CLEAN));
        return winService.runCommandWaitForFinish(String.format(command + " /out=" + Locations.OUTPUT_LOG_FILE + " /showagent /showcmd /showtime", ProjectsCommands.BUILD));
    }

    @Override
    public void cleanAndBuildDontWaitTermination(String command) {
        winService.runCommandDontWaitForTermination(String.format(command, ProjectsCommands.CLEAN));
        winService.runCommandDontWaitForTermination(String.format(command + " /out=" + Locations.OUTPUT_LOG_FILE + " /showagent /showcmd /showtime", ProjectsCommands.BUILD));
    }

    //choose what version of IB to install. type "Latest" for latest version
    @Override
    public void installIB(String version) {
        String installationFile = getIbConsoleInstallation(version);
        winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_INSTALL_COMMAND, installationFile));
        loadIbLicense(IbLicenses.VSTESTS_LIC);
    }

    @Override
    public void installIBnoLoadedLicense(String version) {
        String installationFile = getIbConsoleInstallation(version);
        winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_INSTALL_COMMAND, installationFile));
    }

    @Override
    public void updateIB(String version) {
        String installationFile = getIbConsoleInstallation(version);
        winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_UPDATE_COMMAND, installationFile));
    }

    @Override
    public String getIbConsoleInstallation(String version) {
        String path = Locations.NETWORK_IB_INSTALLATIONS + version;
        String postFix = "console.exe";
        String installerName = "";
        try (DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(Paths.get(path), "*" + postFix)) {
            for (final Path newDirectoryStreamItem : newDirectoryStream) {
                installerName = String.valueOf(newDirectoryStreamItem);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return installerName;
    }

    @Override
    public void loadIbLicense(String license) {
        winService.runCommandWaitForFinish(String.format(WindowsCommands.LOAD_IB_LICENSE, IbLicenses.VSTESTS_LIC));
        SystemActions.sleep(3);
        winService.runCommandWaitForFinish(WindowsCommands.KILL_COORDMON);
    }

    @Override
    public void unloadIbLicense() {
        winService.runCommandWaitForFinish(WindowsCommands.UNLOAD_IB_LICENSE);
    }

    @Override
    public String getIbVsExtensionVersion(String VsDevenvInstallPath) {
        return CustomJsonParser.getValueFromKey(VsDevenvInstallPath + "Extensions\\IncredibuildExtension\\manifest.json", "version");
    }

    @Override
    public String getExpectedIbVsExtensionVersion() {
        try {
            ZipFile zipFile = new ZipFile(Locations.IB_ROOT + "\\IncredibuildMenu.vsix");
            zipFile.extractAll(Locations.QA_ROOT + "Extracted");
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return CustomJsonParser.getValueFromKey(Locations.QA_ROOT + "\\Extracted\\manifest.json", "version");
    }


    @Override
    public boolean verifyIbInstallation(int ibVersion) {
        if (ibVersion != 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean verifyExtensionUpgrade(String oldVersion, String newVersion) {
        return oldVersion.equals(newVersion);
    }

    @Override
    public boolean verifyIbUpgrade(int oldVersion, int newVersion) {
        return oldVersion == newVersion;
    }

    @Override
    public boolean verifyExtensionInstalled(String extensionVersion) {
        return extensionVersion.equals("");
    }

    @Override
    public boolean verifyIbServicesRunning() {
        return winService.isServiceRunning(WindowsServices.AGENT_SERVICE) && winService.isServiceRunning(WindowsServices.COORD_SERVICE);
    }

    @Override
    public void uninstallIB(String version) {
        String installationFile = getIbConsoleInstallation(version);
        winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_UNINSTALL_COMMAND, installationFile));
    }

    //disable IB monitor in visual studio
    @Override
    public void disableVsMonitor() {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\AddIn", "DockAutoOpen", "0");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\AddIn", "DockAutoOpenFloating", "0");
    }

    @Override
    public String getVSVersionFromOutputLog(String logPath) {
        Map<String, String> lookFor = new HashMap<String, String>();
        lookFor.put("Command Prompt", "Command Prompt");
        String result = "";

        try {
            result = Parser.retrieveDataFromFile(logPath, lookFor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        test.log(Status.INFO, "-----------VS VERSION:  " + result + "-----------");
        return result;
    }

    @Override
    public String findValueInPacketLog (String keyInLogFile) throws IOException{
        Map<String, String> lookFor = new HashMap<String, String>();
        lookFor.put(keyInLogFile, keyInLogFile);
        String file = winService.getLatestFileFromDir(Locations.SYSTEM_APPDATA_TEMP_FOLDER, "pkt").getCanonicalPath();
        return Parser.retrieveDataFromFile(file, lookFor);
    }


}
