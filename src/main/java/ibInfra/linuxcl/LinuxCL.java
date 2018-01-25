package ibInfra.linuxcl;

import com.aventstack.extentreports.Status;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.windowscl.WindowsCLService;
import org.jdom2.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LinuxCL extends TestBase implements ILinuxCL {

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
            test.log(Status.INFO, "Successfully connected to " + hostIP);
            test.log(Status.INFO, "Running command " + command);

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
    public void deleteLogsFolder(List<String> ipList) {
        WindowsCLService runCommand = new WindowsCLService();
        for (Object machine : ipList) {
            runCommand.runCommandWaitForFinish(StaticDataProvider.LinuxCommands.PLINK + machine + " " + StaticDataProvider.LinuxCommands.DELETE_LOGS);
        }
    }

    @Override
    public List<String> breakDownIPList(List ipList) {
        List<String> newIpList = new ArrayList<String>();
        for (Object anIpList : ipList) {
            Element node = (Element) anIpList;
            newIpList.add(node.getContent(0).getValue().trim());
        }
        return newIpList;
    }
}
