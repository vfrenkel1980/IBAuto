package ibInfra.linuxcl;

import com.amazonaws.services.dynamodbv2.xspec.NULL;
import com.aventstack.extentreports.Status;
import com.jcraft.jsch.*;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import ibInfra.windowscl.WindowsService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.collections4.list.SetUniqueList;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

public class LinuxService extends TestBase implements ILinuxService {

    private WindowsService winService = new WindowsService();

    @Override
    public int linuxRunSSHCommand(String command, String hostIP) {

        return linuxRunSSHCommand(command, hostIP, "");
    }

    @Override
    public int linuxRunSSHCommand(String command, String hostIP, String keyFilePath)
    {
        JSch jsch = new JSch();
        int exitStatus = 0;
        Session session;
        boolean sshWithKey = !keyFilePath.isEmpty();
        try {

            if (sshWithKey)
                jsch.addIdentity(keyFilePath);

            session = jsch.getSession("xoreax", hostIP, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("UserKnownHostsFile", "/dev/null");
            //Set password
            session.setPassword("xoreax");
            command = ". ~/.profile; " + command;
            session.connect();
            if (test != null) {
                test.log(Status.INFO,"Connected to server " + hostIP);
                test.log(Status.INFO, "Running command " + command);
                extent.flush();
            }
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setPty(true);
            channelExec.setCommand(command);
            channelExec.connect();

            InputStream in = channelExec.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            exitStatus = channelExec.getExitStatus();
            if (exitStatus > 0) {
                if (test != null)
                    test.log(Status.WARNING, "Failed to run command.\n" +
                            "Command: " + command);
            }
            session.disconnect();
        } catch (JSchException | IOException e) {
            if (test != null)
                test.log(Status.ERROR, "Connection error occurred");
            e.printStackTrace();
        }
        return exitStatus;
    }

    @Override
    public String linuxRunSSHCommandOutputString(String command, String hostIP) {
        JSch jsch = new JSch();
        Session session;
        try {
            session = jsch.getSession("xoreax", hostIP, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("UserKnownHostsFile", "/dev/null");
            //Set password
            session.setPassword("xoreax");
            command = ". ~/.profile; " + command;
            session.connect();
            if (test != null) {
                test.log(Status.INFO,"Connected to server " + hostIP);
                test.log(Status.INFO, "Running command " + command);
                extent.flush();
            }
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setPty(true);
            channelExec.setCommand(command);
            channelExec.connect();

            InputStream in = channelExec.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder commandOutput=new StringBuilder();
            while ((line = reader.readLine()) != null) {
                commandOutput.append(line);
                commandOutput.append('\n');
            }
            channelExec.disconnect();
            return commandOutput.toString();
        } catch (JSchException | IOException e) {
            if (test !=null) {
                test.log(Status.ERROR, "Connection error occurred");
            }
            e.printStackTrace();
            return "Unable to get result output from command " + command;
        }
    }

    @Override
    public List<String> linuxRunSSHCommandAssignToList(String command, String hostIP) {
        JSch jsch = new JSch();
        Session session;
        List<String> output = SetUniqueList.setUniqueList(new ArrayList<String>());
        try {
            session = jsch.getSession("xoreax", hostIP, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("UserKnownHostsFile", "/dev/null");
            //Set password
            session.setPassword("xoreax");
            command = ". ~/.profile; " + command;
            session.connect();

            if (test != null) {
                test.log(Status.INFO,"Connected to server " + hostIP);
                test.log(Status.INFO, "Running command " + command);
                extent.flush();
            }
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setPty(true);
            channelExec.setCommand(command);
            channelExec.connect();

            InputStream in = channelExec.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                output.add(line);
            }
            channelExec.disconnect();
        } catch (JSchException | IOException e) {
            if (test !=null) {
                test.log(Status.ERROR, "Connection error occurred");
            }
            e.printStackTrace();
        }
        return output;
    }

    public void getFile(String hostname, String copyFrom, String copyTo)
            throws JSchException {
        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession("xoreax", hostname, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("UserKnownHostsFile", "/dev/null");
            session.setPassword("xoreax");
            session.connect();
            if (test != null)
                test.log(Status.INFO, "Connected to server " + hostname);
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            try {
                sftpChannel.get(copyFrom, copyTo, monitor, ChannelSftp.OVERWRITE);
                if (test !=null)
                    test.log(Status.INFO, "Finished getting file from Linux Server...");
            } catch (SftpException e) {
                e.getMessage();
                if (test !=null)
                    test.log(Status.INFO, "file was not found: " + copyFrom);
            }
            sftpChannel.exit();
            session.disconnect();
        }catch (JSchException e){
            if (test !=null) {
                test.log(Status.ERROR, "Connection error occurred");
            }
        }
    }

    private final static SftpProgressMonitor monitor = new SftpProgressMonitor() {
        public void init(final int op, final String source, final String target, final long max) {
            log.info("sftp start uploading file from:" + source + " to:" + target);
            test.log(Status.INFO, "sftp start uploading file from:" + source + " to:" + target);
        }

        public boolean count(final long count) {
            log.debug("sftp sending bytes: " + count);
            return true;
        }

        public void end() {
            log.info("sftp uploading is done.");
        }
    };

    @Override
    public void deleteLogsFolder(List<String> ipList) {
        for (Object machine : ipList) {
            log.info("deleting " + machine);
            winService.runCommandWaitForFinish(LinuxCommands.PLINK + machine + " " + LinuxCommands.DELETE_LOGS);
            log.info("deleted " + machine);
        }
    }

    @Override
    public boolean isIBServiceUp(String IP) {
        int res = winService.runCommandWaitForFinish(LinuxCommands.PLINK + IP + " " + LinuxCommands.CHECK_IB_SERVICES);
        return res == 0;
    }

    @Override
    public void killibDbCheck(String IP) {
        linuxRunSSHCommandOutputString(LinuxCommands.KILL_IB_DB_CHECK,IP);
    }

    @Override
    public boolean startIBService(String IP) {
        int res = winService.runCommandWaitForFinish(LinuxCommands.PLINK + IP + " " + LinuxCommands.START_IB_SERVICES);
        return res != 0;
    }

    @Override
    public boolean stopIBService(String IP) {
        int res = winService.runCommandWaitForFinish(LinuxCommands.PLINK + IP + " " + LinuxCommands.STOP_IB_SERVICES);
        return res != 0;
    }

    @Override
    public boolean isLinuxOSUbuntu(String IP) {
        return linuxRunSSHCommandOutputString(LinuxCommands.GET_OS, IP).contains("Ubuntu");
    }

    /**
     * jughggjmkuk y,kuj,jk,j
     * mjh,kjk,jk,j,j
     * @param fieldName
     * @param sqliteTable
     * @param IP
     * @return
     *
     * mjhmjhjm
     */
    @Override
    public String runQueryLastBuild(String fieldName, String sqliteTable, String IP) {
        return linuxRunSSHCommandOutputString((String.format(LinuxCommands.RUN_SQLITE_Q, fieldName, sqliteTable)),IP);
    }

    /**
     *
     * @param destMachine
     * @param version
     * @param grid
     */
    @Override
    public void updateIB(String destMachine, String version, List<String> grid) {
        log.info("starting updateIB");
        String installationFilePath = getInstallerName(LinuxMachines.LINUX_BUILDER, version);
        copyFileFromLinuxToLinux(LinuxMachines.LINUX_BUILDER, destMachine, installationFilePath);
        String installationFileName = installationFilePath.substring(installationFilePath.lastIndexOf("/") + 1);
        //extractUpgradeFile(destMachine, installationFileName);
        linuxRunSSHCommand(String.format(LinuxCommands.IB_APPLY_UPDATE, installationFileName), destMachine);
        SystemActions.sleep(180);
        Assert.assertEquals(version, getIBVersion(destMachine));
        verifyAgentsUpdated(destMachine, version);
        String ibDBCheckPath = getInstallerFolder(LinuxMachines.LINUX_BUILDER, version) + "/ib_db_check.py";
        String  ibTestsPath = getInstallerFolder(LinuxMachines.LINUX_BUILDER, version) + "/ib_tests-1.0.0.tar.bz2";

       for (String machine: grid) {
            if (machine.contains("mb") || machine.contains("mi") || machine.contains("init") )
                copyFileFromLinuxToLinux(LinuxMachines.LINUX_BUILDER, machine, ibDBCheckPath);

            if ( machine.contains("init") ) {
                copyFileFromLinuxToLinux(LinuxMachines.LINUX_BUILDER, machine, ibTestsPath);
                extractFile(machine, LinuxSimulation.IB_TESTS);
            }
        }
    }

    @Override
    public void copyFileFromLinuxToLinux(String srcMachine, String destMachine, String fileName) {
        linuxRunSSHCommand(String.format(LinuxCommands.COPY_FILE_SCP, fileName, destMachine), srcMachine);
    }

    @Override
    public String getInstallerName(String machineName, String version) {
        return LinuxCommands.HOME_DIR + linuxRunSSHCommandOutputString("find . -name \"incredibuild_upgrade_" + version + ".x86_64-i686.tar*\"", machineName).substring(2).replaceAll("\n","") ;
    }

    @Override
    public String getInstallationFileerName(String machineName, String version) {
        return linuxRunSSHCommandOutputString("find /opt/incredibuild/ -name \"incredibuild_" + version + "-x86_64-i686.bin\"", machineName).replaceAll("\n","") ;
    }

    @Override
    public String getInstallerFolder(String machineName, String version) {
        return LinuxCommands.HOME_DIR + linuxRunSSHCommandOutputString("find . -name \"*" + version + "-release*\" -type d", machineName).substring(2).replaceAll("\n","") ;
    }

    @Override
    public void extractUpgradeFile(String machineName, String fileName) {
        linuxRunSSHCommand(String.format(LinuxCommands.EXTRACT_UPGRADE_FILE, fileName), machineName);
    }

    @Override
    public  void extractFile(String machineName, String filePath) {
        linuxRunSSHCommand(String.format(LinuxCommands.EXTRACT_FILE, filePath), machineName);
    }

    @Override
    public String getIBVersion(String machine) {
        String ibVersion = linuxRunSSHCommandOutputString(LinuxCommands.GET_IB_VERSION, machine);
        return ibVersion.substring(ibVersion.indexOf("[") + 1, ibVersion.indexOf("]"));
    }

    @Override
    public void verifyAgentsUpdated(String hostName, String version) {
        LinuxDBService linuxDBService = new LinuxDBService();
        List <String[]> machineList = new ArrayList<>();
        List <String> machineInfo = linuxDBService.selectAllWhere(LinuxDB.DB_COORD_REPORT, LinuxDB.COLUMN_MACHINE + "," + LinuxDB.COLUMN_CONNECTED_SINCE + "," + LinuxDB.COLUMN_VERSION
                , LinuxDB.TABLE_HELPER_MACHINES, LinuxDB.COLUMN_LICENSED_CORES + "> 0", hostName);
        String currentTime = linuxRunSSHCommandOutputString(LinuxCommands.GET_EPOCH_TIME, hostName).replaceAll("\n","");
        for (int i = 0; i < machineInfo.size(); i++)
        {
            machineList.add(i, machineInfo.get(i).split("\\|"));
        }
        long currentTimeLong = Long.parseLong(currentTime);
        for (String[] machine : machineList ) {
            if (currentTimeLong - Long.parseLong(machine[1]) < 10000) {
                if (!version.equals(machine[2]))
                    test.log(Status.WARNING, "Version found on " + machine[0] + " is " + machine[2] + " Should be " + version);
            }
        }
    }

    @Override
    public int installIB(String machineName, String version, String flags, String coord, String binSource,String instFolder, boolean isCoord) {
        String installationFilePath = getInstallationFileerName(binSource, version);
        copyFileFromLinuxToLinux(binSource, machineName, installationFilePath);
        String installationFileName = installationFilePath.substring(installationFilePath.lastIndexOf("/") + 1);
        String installCommand = "sudo ./" + installationFileName + flags;

        if(!isCoord)
            installCommand += coord + " -A " + instFolder;
        installCommand +=  " -A " + instFolder;

        return linuxRunSSHCommand(installCommand, machineName);
    }
}
