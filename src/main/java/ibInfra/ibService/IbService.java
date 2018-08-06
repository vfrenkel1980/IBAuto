package ibInfra.ibService;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.*;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.parsers.CustomJsonParser;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.windowscl.WindowsService;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;


public class IbService extends TestBase implements IIBService {

    private WindowsService winService = new WindowsService();

    @Override
    public String getIBInstallFolder() {
        return RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.FOLDER);
    }

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
    public void installIB(String version, String license) {
        String installationFile = getIbConsoleInstallation(version);
        winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_INSTALL_COMMAND, installationFile));
        IbLocations.IB_ROOT = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "Folder");
        Processes.XLICPROC = "\"" + IbLocations.IB_ROOT + "\\xlicproc" + "\" " + "/LicenseFile=";
        WindowsCommands.LOAD_IB_LICENSE = Processes.XLICPROC + "\"" + Locations.QA_ROOT + "\\License\\%s\"";
        loadIbLicense(license);
    }

    @Override
    public int installIB(String version) {
        String installationFile = getIbConsoleInstallation(version);
        return winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_INSTALL_COMMAND, installationFile));
    }

    @Override
    public void updateIB(String version) {
        String installationFile = getIbConsoleInstallation(version);
        winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_UPDATE_COMMAND, installationFile));
    }
    @Override
    public int installEnt() {
        String installationFile = getIbConsoleInstallationForEnt();
        return winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_UPDATE_COMMAND, installationFile));
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
            e.getMessage();
        }
        return installerName;
    }

    @Override
    public String getIbConsoleInstallationForEnt() {
        String path = Locations.ENT_INSTALLER_PATH;
        String postFix = "console.exe";
        String installerName = "";
        try (DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(Paths.get(path), "*" + postFix)) {
            for (final Path newDirectoryStreamItem : newDirectoryStream) {
                installerName = String.valueOf(newDirectoryStreamItem);
            }
        } catch (final Exception e) {
            e.getMessage();
        }
        return installerName;
    }

    @Override
    public void loadIbLicense(String license) {
        winService.runCommandDontWaitForTermination(String.format(WindowsCommands.LOAD_IB_LICENSE, license));
        SystemActions.sleep(5);
        SystemActions.killProcess("XLicProc.exe");
        SystemActions.killProcess("CoordMonitor.exe");
        isLicenseLoaded();
    }

    @Override
    public void unloadIbLicense() {
        winService.runCommandWaitForFinish(WindowsCommands.UNLOAD_IB_LICENSE);
    }

    @Override
    public String getIbVsExtensionVersion(String VsDevenvInstallPath) {
        return CustomJsonParser.getValueFromKey(VsDevenvInstallPath + "\\Extensions\\IncredibuildExtension\\manifest.json", "version");
    }

    @Override
    public String getExpectedIbVsExtensionVersion() {
        String version;
        try {
            ZipFile zipFile = new ZipFile(IbLocations.IB_ROOT + "\\IncredibuildMenu.vsix");
            zipFile.extractAll(Locations.QA_ROOT + "\\Extracted");
        } catch (ZipException e) {
            e.getMessage();
        }
        version =  CustomJsonParser.getValueFromKey(Locations.QA_ROOT + "\\Extracted\\manifest.json", "version");
        test.log(Status.INFO, "installed IB extension version ------> " + version);
        return version;
    }


    @Override
    public boolean verifyIbInstallation(int ibVersion) {
        return ibVersion != 0;
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
    public boolean verifyIbServicesRunning(boolean agent, boolean coord) {
        if (agent && !coord)
            return winService.isServiceRunning(WindowsServices.AGENT_SERVICE);
        else if (!agent && coord)
            return winService.isServiceRunning(WindowsServices.COORD_SERVICE);
        else if (agent && coord)
            return winService.isServiceRunning(WindowsServices.AGENT_SERVICE) && winService.isServiceRunning(WindowsServices.COORD_SERVICE);
        else
            return false;
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
    public void customPackAllocationOn() {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", "LicenseAllocationOption", "IbQaMode");
        SystemActions.copyFilesByExtension("C:\\LicenseTests_projects\\CustomAllocation\\", getIBInstallFolder(), "dat", false);
    }

    @Override
    public String findValueInPacketLog (String keyInLogFile) throws IOException{
        Map<String, String> lookFor = new HashMap<String, String>();
        lookFor.put(keyInLogFile, keyInLogFile);
        String file = winService.getLatestFileFromDir(Locations.SYSTEM_APPDATA_TEMP_FOLDER, "pkt").getCanonicalPath();
        return Parser.retrieveDataFromFile(file, lookFor);
    }

    @Override
    public boolean isLicenseLoaded() {
        File file = new File(IbLocations.IB_ROOT + "\\CoordLicense.dat");
        if(file.exists()) {
            test.log(Status.INFO, "License is loaded");
            return true;
        }
        else{
            test.log(Status.WARNING, "License is NOT loaded. CoordLicense.dat does not exist in + " + IbLocations.IB_ROOT);
            return false;
        }
    }

    @Override
    public String getCoordinator() {
        return RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\BuildService", RegistryKeys.COORDINATOR_HOST);
    }

    @Override
    public boolean verifyAvoidLocal(String filePath){
        File file = new File(filePath);
        String currentLine = "";
        String previousLine = "";
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                previousLine = currentLine;
                currentLine = scanner.nextLine();
                if (currentLine.contains(StaticDataProvider.LogOutput.LOCAL)) {
                    if (!currentLine.contains("LNK") && !previousLine.contains("PreBuild") && !previousLine.contains("PostBuild"))
                        return false;
                }
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        }finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return true;
    }


}
