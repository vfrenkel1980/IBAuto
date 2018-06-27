package ibInfra.linuxcl;

import com.jcraft.jsch.JSchException;
import frameworkInfra.testbases.LinuxTestBase;
import frameworkInfra.utils.StaticDataProvider.*;

import java.text.SimpleDateFormat;

public class LinuxRunScriptThreads extends LinuxTestBase implements Runnable {

    private LinuxService linuxService = new LinuxService();
    private String firstBuilds;
    private String host;

    public LinuxRunScriptThreads(String firstBuilds, String host){
        this.firstBuilds = firstBuilds;
        this.host = host;
    }

    @Override
    public void run() {
        String suiteLastBuild = linuxService.runQueryLastBuild(LinuxCommands.BUILD_ID, LinuxCommands.BUILD_HISTORY, host).replaceAll("\n","");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        String output = "res" + dateFormat.format(calendar.getTime()) + "_" + host;
        linuxService.linuxRunSSHCommand("./ib_db_check.py -d MI_ib_db_check_data.py -r " + firstBuilds + "," + suiteLastBuild + " --ignore-aborts > " + output, host);
        try {
            linuxService.getFile(host, "/home/xoreax/" + output, Locations.LINUX_SCRIPT_OUTPUT + "MultiInitiator\\" + output);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}
