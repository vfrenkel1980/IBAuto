package ibInfra.ibService;

import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.windowscl.WindowsService;

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
        runWin.runCommandWaitForFinish(String.format(command, StaticDataProvider.ProjectsCommands.CLEAN));
        return runWin.runCommandWaitForFinish(String.format(command, StaticDataProvider.ProjectsCommands.BUILD));
    }


    @Override
    public void installIB() {
        String installationFile = getIbConsoleInstallation();
        runWin.runCommandWaitForFinish(String.format(StaticDataProvider.WindowsCommands.IB_INSTALL_COMMAND, installationFile));
        loadIbLicense();
    }

    @Override
    public String getIbConsoleInstallation() {
        String path = "c:\\bld";
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
        runWin.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.APPLY_IB_LICENSE);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        runWin.runCommandWaitForFinish(StaticDataProvider.WindowsCommands.KILL_COORDMON);
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
}
