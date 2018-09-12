package ibInfra.ibService;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.*;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.parsers.CustomJsonParser;
import frameworkInfra.utils.parsers.HtmlParser;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.windowscl.WindowsService;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.testng.ITestContext;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;


public class IbService implements IIBService {

    private WindowsService winService = new WindowsService();

    @Override
    public String getIBInstallFolder() {
        return RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.FOLDER);
    }

    /**
     * perform ib clean and build
     * @param command the command we run
     * @return exit code of the command
     */
    @Override
    public int cleanAndBuild(String command) {
        winService.runCommandWaitForFinish(String.format(command, ProjectsCommands.CLEAN));
        return winService.runCommandWaitForFinish(String.format(command + " /out=" + Locations.OUTPUT_LOG_FILE + " /showagent /showcmd /showtime", ProjectsCommands.BUILD));
    }

    /**
     * perform ib clean and build but dont wait for exit code
     * @param command the command we run
     */
    @Override
    public void cleanAndBuildDontWaitTermination(String command) {
        winService.runCommandDontWaitForTermination(String.format(command, ProjectsCommands.CLEAN));
        winService.runCommandDontWaitForTermination(String.format(command + " /out=" + Locations.OUTPUT_LOG_FILE + " /showagent /showcmd /showtime", ProjectsCommands.BUILD));
    }

    /**
     * Install incredibuild
     * @param version version that we would like to install, use "Latest" for latest release
     * @param license license file to use
     */
    @Override
    public void installIB(String version, String license) {
        String installationFile = getIbConsoleInstallation(version);
        winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_INSTALL_COMMAND, installationFile));
        IbLocations.IB_ROOT = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "Folder");
        Processes.XLICPROC = "\"" + IbLocations.IB_ROOT + "\\xlicproc" + "\" " + "/LicenseFile=";
        WindowsCommands.LOAD_IB_LICENSE = Processes.XLICPROC + "\"" + Locations.QA_ROOT + "\\License\\%s\"";
        loadIbLicense(license);
    }

    /**
     * perform IB installation without license in order to get the installation exit code
     * @param version version to install
     * @return installation command exit code
     */
    @Override
    public int installIB(String version) {
        String installationFile = getIbConsoleInstallation(version);
        return winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_INSTALL_COMMAND, installationFile));
    }

    /**
     * downgrade enterprise edition to pro
     * @param version enterprise version installed
     * @return exit code of the downgrade procedure
     */
    @Override
    public int downgradeEntToPro(String version) {
        String installationFile = getIbConsoleInstallation(version);
        return winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_DOWNGRADE_COMMAND, installationFile));
    }

    /**
     * update IB
     * @param version version to update to
     */
    @Override
    public void updateIB(String version) {
        String installationFile = getIbConsoleInstallation(version);
        winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_UPDATE_COMMAND, installationFile));
    }

    /**
     * this method is for upgrading to ent using the zip file and the command line
     * @return exit code of the upgrade cpmmand
     */
    @Override
    public int upgradeToEnt() {
        String installationFile = getIbConsoleInstallationForEnt();
        return winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_UPDATE_COMMAND, installationFile));
    }

    /**
     * get the console installation exe from directory
     * @param version the version that we want to get
     * @return full path to file
     */
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

    /**
     * get the console installer name from the ent installer path
     * @return path to console exe
     */
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

    /**
     * load IB license file using xlicproc
     * @param license path to license
     */
    @Override
    public void loadIbLicense(String license) {
        winService.runCommandDontWaitForTermination(String.format(WindowsCommands.LOAD_IB_LICENSE, license));
        SystemActions.sleep(5);
        SystemActions.killProcess("XLicProc.exe");
        SystemActions.killProcess("CoordMonitor.exe");
        isLicenseLoaded();
    }

    /**
     * unload IB license file using xlicproc
     */
    @Override
    public void unloadIbLicense() {
        winService.runCommandDontWaitForTermination(WindowsCommands.UNLOAD_IB_LICENSE);
        SystemActions.sleep(5);
        SystemActions.killProcess("XLicProc.exe");
    }

    /**
     * get the ib vs extension version from manifest
     * @param VsDevenvInstallPath VS manifest path
     * @return version
     */
    @Override
    public String getIbVsExtensionVersion(String VsDevenvInstallPath) {
        return CustomJsonParser.getValueFromKey(VsDevenvInstallPath + "\\Extensions\\IncredibuildExtension\\manifest.json", "version");
    }

    /**
     * get the ib vs extension version from manifest in IB installation path
     * @return version
     */
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

    /**
     * verify if incredibuild services are running
     * @param agent include agent service in check
     * @param coord include coordinator service in check
     * @return boolean true/false
     */
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

    /**
     * Uninstall IB using command line
     * @param version version number to grab the installation frile from
     */
    @Override
    public void uninstallIB(String version) {
        String installationFile = getIbConsoleInstallation(version);
        winService.runCommandWaitForFinish(String.format(WindowsCommands.IB_UNINSTALL_COMMAND, installationFile));
    }

    /**
     * disable IB monitor in visual studio
     */
    @Override
    public void disableVsMonitor() {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\AddIn", "DockAutoOpen", "0");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\AddIn", "DockAutoOpenFloating", "0");
    }

    /**
     * grab VS version from IB output log
     * @param logPath path to log
     * @return VS version
     */
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

    /**
     * find value of requried key in packet log
     * @param keyInLogFile key to search for
     * @return value of the required key
     * @throws IOException read from file
     */
    @Override
    public String findValueInPacketLog (String keyInLogFile) throws IOException{
        Map<String, String> lookFor = new HashMap<String, String>();
        lookFor.put(keyInLogFile, keyInLogFile);
        String file = winService.getLatestFileFromDir(Locations.SYSTEM_APPDATA_TEMP_FOLDER, "pkt").getCanonicalPath();
        return Parser.retrieveDataFromFile(file, lookFor);
    }

    /**
     * is the ib license loaded to the machine
     * @return true/false
     */
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

    /**
     * get the coordinator of the current ib agent
     * @return String - coordinator name
     */
    @Override
    public String getCoordinator() {
        return RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\BuildService", RegistryKeys.COORDINATOR_HOST);
    }

    /**
     * verify ig avoid local exections was turned on on the machine by looking in hte log file
     * @param filePath path to output log
     * @return true/false
     */
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

    /**
     * Decrypt the IB SQLite DB
     */
    @Override
    public void decryptSQLiteDB() {
        winService.restartService(WindowsServices.COORD_SERVICE);
        winService.runCommandWaitForFinish(Processes.SQLITE_CONVERTION_TOOL + " \"" + IbLocations.IB_ROOT + "\" " + "decrypted_db.db");
    }

    /**
     * Open build monitor exe
     */
    @Override
    public void openBuildMonitor() {
        do {
            winService.runCommandDontWaitForTermination(IbLocations.BUILDMONITOR);
        }while (winService.getNumberOfProcessInstances(Processes.BUILDMONITOR) == 0);
    }


    /**
     * Generates a custom HTML report derived from the full extent report
     * and saves the result in network automation\reports folder
     * @param context used to get the suite name
     */
    public void generateCustomReport(ITestContext context){
        String version = getVersionFromInstaller("Latest");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
        String file = winService.getLatestFileFromDir(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/" , "TestOutput").getAbsolutePath();
        String suite = context.getCurrentXmlTest().getSuite().getName();
        String suiteId = CustomJsonParser.getValueFromKey(System.getProperty("user.dir") + "/src/main/resources/Configuration/SuiteId.json", suite);
        String destFile = Locations.NETWORK_REPORTS_FOLDER + "TestResultReport" + suite + ".html";
        SystemActions.copyFile(file, Locations.NETWORK_REPORTS_FOLDER + suite + "\\" + suite + "_" + formatter.format(calendar.getTime()) + "_" + version + ".html");
        SystemActions.deleteFile(destFile);
        filterOlderReports(suite);
        String addVersionNumber = "exceptionsGrandChild: 0,\n" +
                "\t\t\t\tversionNumber: " + suiteId;
        String orgScript = "<script src='https://cdn.rawgit.com/anshooarora/extentreports-java/fca20fb7653aade98810546ab96a2a4360e3e712/dist/js/extent.js' type='text/javascript'></script>";
        String desiredScript= "<script src='../static/js/jquery_bundle.js' type='text/javascript'></script>\n" +
                "\t\t\t<script src='../static/js/extent.js' type='text/javascript'></script>";
        int numberOfLines = HtmlParser.countLinesInFile(file);
        int desiredLine = Parser.getFirstLineForString(file, "<div id='test-view-charts' class='subview-full'>");
        HtmlParser.copyLinesToNewFile(file,destFile,0,23);
        HtmlParser.copyLinesToNewFile(file,destFile,desiredLine - 1,desiredLine + 33);
        HtmlParser.copyLinesToNewFile(file,destFile,numberOfLines - 37,numberOfLines);
        HtmlParser.replaceStringInFile(destFile, "<body class='extent standard default hide-overflow '>", "<body>");
        HtmlParser.replaceStringInFile(destFile, "parent-analysis", "parent-analysis" + suiteId);
        HtmlParser.replaceStringInFile(destFile, "child-analysis", "child-analysis" + suiteId);
        HtmlParser.replaceStringInFile(destFile, "exceptionsGrandChild: 0,", addVersionNumber);
        HtmlParser.replaceStringInFile(destFile, orgScript, desiredScript);
    }

    /**
     * Keep 10 latest files inside a folder
     * @param suite the name of the folder inside the automation folder in Y:
     */
    private void filterOlderReports(String suite){
        File directory = new File(StaticDataProvider.Locations.NETWORK_REPORTS_FOLDER + suite);
        File[] files = directory.listFiles((FileFilter) FileFileFilter.FILE);
        int numberOfFiles = 0;
        if (files != null) {
            numberOfFiles = files.length;
        }
        if (numberOfFiles > 10) {
            Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
            SystemActions.deleteFile(files[0].getPath());
        }

    }

    /**
     * Get the version number of the installer in order to present in report
     * @param version what folder to look in (currently set to always "Latest")
     * @return version number
     */
    private String getVersionFromInstaller(String version){
        String installer = getIbConsoleInstallation(version);
        installer = installer.substring(installer.lastIndexOf("\\"));
        return installer.replaceAll("\\D+","");
    }





}
