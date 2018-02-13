package ibInfra.ibService;

import frameworkInfra.testbases.TestBase;
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
        loadIbLicense();
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
    public void loadIbLicense() {
        runWin.runCommandWaitForFinish(WindowsCommands.APPLY_IB_LICENSE);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        runWin.runCommandWaitForFinish(WindowsCommands.KILL_COORDMON);
    }

    @Override
    public String getIbVsExtensionVersion() {
        String result = "";
        Map<String, String> lookFor = new HashMap<String, String>();
        lookFor.put("version", "version");
        try {
            result = Parser.retrieveDataFromFile("C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\\Common7\\IDE\\Extensions\\IncredibuildExtension\\manifest.json", lookFor);
        } catch (IOException e) {
            e.getMessage();
        }
        result = result.substring(0,result.indexOf(","));
        return result;
    }

    @Override
    public String getExpectedIbVsExtensionVersion() {
        try {
            ZipFile zipFile = new ZipFile("C:\\Program Files (x86)\\Xoreax\\IncrediBuild\\IncredibuildMenu.vsix");
            zipFile.extractAll(Locations.QA_ROOT + "Extracted");
        } catch (ZipException e) {
            e.printStackTrace();
        }

        String result = "";
        Map<String, String> lookFor = new HashMap<String, String>();
        lookFor.put("version", "version");
        try {
            result = Parser.retrieveDataFromFile(Locations.QA_ROOT + "Extracted\\manifest.json", lookFor);
        } catch (IOException e) {
            e.getMessage();
        }
        result = result.substring(0,result.indexOf(","));
        return result;
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
        if (oldVersion.equals(newVersion))
            return false;
        else
            return true;
    }

    @Override
    public boolean verifyIbUpgrade(int oldVersion, int newVersion) {
        if (oldVersion == newVersion)
            return false;
        else
            return true;
    }

    @Override
    public boolean verifyExtensionInstalled(String extensionVersion) {
        if (extensionVersion.equals(""))
            return false;
        else
            return true;
    }

    @Override
    public boolean verifyIbServicesRunning() {
        if (runWin.isServiceRunning(WindowsServices.AGENT_SERVICE) && runWin.isServiceRunning(WindowsServices.COORD_SERVICE))
            return true;
        else
            return false;

    }


}
