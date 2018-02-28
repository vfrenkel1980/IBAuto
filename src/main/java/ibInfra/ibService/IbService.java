package ibInfra.ibService;

import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.CustomJsonParser;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
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


public class IbService extends TestBase implements IIBService {
    WindowsService runWin = new WindowsService();

    @Override
    public int cleanAndBuild(String command) {
        runWin.runCommandWaitForFinish(String.format(command, ProjectsCommands.CLEAN));
        return runWin.runCommandWaitForFinish(String.format(command, ProjectsCommands.BUILD));
    }

    //choose what version of IB to install. type "Latest" for latest version
    @Override
    public void installIB(String version) {
        String installationFile = getIbConsoleInstallation(version);
        runWin.runCommandWaitForFinish(String.format(WindowsCommands.IB_INSTALL_COMMAND, installationFile));
        loadIbLicense(IbLicenses.VSTESTS_LIC);
    }

    @Override
    public String getIbConsoleInstallation(String version) {
        String path = "\\\\192.168.10.15\\Share\\1-IB_Builds\\" + version;
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
        runWin.runCommandWaitForFinish(String.format(WindowsCommands.LOAD_IB_LICENSE, IbLicenses.VSTESTS_LIC));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        runWin.runCommandWaitForFinish(WindowsCommands.KILL_COORDMON);
    }

    @Override
    public void unloadIbLicense(String license) {
        runWin.runCommandWaitForFinish(WindowsCommands.UNLOAD_IB_LICENSE);
    }

    @Override
    public String getIbVsExtensionVersion(String VsDevenvInstallPath) {
        return CustomJsonParser.getValueFromKey(VsDevenvInstallPath + "Extensions\\IncredibuildExtension\\manifest.json", "version");

/*        Map<String, String> lookFor = new HashMap<String, String>();
        lookFor.put("version", "version");
        try {
            result = Parser.retrieveDataFromFile("C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\\Common7\\IDE\\Extensions\\IncredibuildExtension\\manifest.json", lookFor);
        } catch (IOException e) {
            e.getMessage();
        }
        result = result.substring(0,result.indexOf(","));*/
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
        return runWin.isServiceRunning(WindowsServices.AGENT_SERVICE) && runWin.isServiceRunning(WindowsServices.COORD_SERVICE);
    }

    @Override
    public void uninstallIB(String version) {
        String installationFile = getIbConsoleInstallation(version);
        runWin.runCommandWaitForFinish(String.format(WindowsCommands.IB_UNINSTALL_COMMAND, installationFile));
    }

    @Override
    public String findValueInPacketLog (String keyInLogFile) throws IOException{
        Map<String, String> lookFor = new HashMap<String, String>();
        lookFor.put(keyInLogFile, keyInLogFile);
        String file = runWin.getLatestFilefromDir(Locations.SYSTEM_APPDATA_TEMP_FOLDER, "pkt").getCanonicalPath();
        return Parser.retrieveDataFromFile(file, lookFor);
    }


}
