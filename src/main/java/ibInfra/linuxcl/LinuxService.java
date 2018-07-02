package ibInfra.linuxcl;

import com.aventstack.extentreports.Status;
import com.jcraft.jsch.*;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import ibInfra.windowscl.WindowsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static frameworkInfra.Listeners.SuiteListener.test;

public class LinuxService extends TestBase implements ILinuxService {

    private WindowsService winService = new WindowsService();

    @Override
    public int linuxRunSSHCommand(String command, String hostIP) {
        JSch jsch = new JSch();
        int exitStatus = 0;
        Session session;
        try {

            session = jsch.getSession("xoreax", hostIP, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            //Set password
            session.setPassword("xoreax");
            session.connect();
            if (test != null) {
                test.log(Status.INFO,"Connected to server " + hostIP);
                test.log(Status.INFO, "Running command " + command);
            }
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
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
                test.log(Status.ERROR, "Failed to run command.\n" +
                        "Command: " + command);
            }
            session.disconnect();
        } catch (JSchException | IOException e) {
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
            //Set password
            session.setPassword("xoreax");
            session.connect();
            if (test != null) {
                test.log(Status.INFO,"Connected to server " + hostIP);
                test.log(Status.INFO, "Running command " + command);
            }
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
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

    public void getFile(String hostname, String copyFrom, String copyTo)
            throws JSchException {
        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession("xoreax", hostname, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword("xoreax");
            session.connect();
            //test.log(Status.INFO, "Connected to server " + hostname);

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            try {
                sftpChannel.get(copyFrom, copyTo, monitor, ChannelSftp.OVERWRITE);
                //test.log(Status.INFO, "Finished getting file from Linux Server...");
            } catch (SftpException e) {
                //test.log(Status.INFO, "file was not found: " + copyFrom);
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

    @Override
    public String runQueryLastBuild(String fieldName, String sqliteTable, String IP) {
        return linuxRunSSHCommandOutputString((String.format(LinuxCommands.RUN_SQLITE_Q, fieldName, sqliteTable)),IP);
    }
}
