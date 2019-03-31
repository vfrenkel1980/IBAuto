package Native.linuxtests;

import cloudInfra.CloudServices.AwsService;
import frameworkInfra.testbases.LinuxAWSTestBase;
import frameworkInfra.testbases.LinuxTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class LinuxAWSTests extends LinuxAWSTestBase {


//    static AwsService awsService = new AwsService("0", "0", "2", "1");

    //(String cpu, String memory, String vmCount, String initiator)

//    public static void main(String[] args) {

//        awsService.create();
//        awsService.startVm();
//        awsService.stopVm();
//        awsService.deleteVmAWS();

//        awsService.getIPVfromNamemAWS("5");
//        awsService.startVm();
//    }

    @Test(testName = "Android5", groups = { "4" })
    public void Android5() {
        log.info("starting Android5");

//        int exitCode = linuxService.linuxRunSSHCommand("echo 123", initIPVec.get(4), StaticDataProvider.LinuxAWS.LINUX_KEY_FILE_PATH);

//        int exitCode = linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxAWS.CD_AND_5_DIR_AWS + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
//                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD, "--ib-crash -d1", "Android5", "", "24"), ip);
//        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxAWS.CD_AND_5_DIR_AWS + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ip);
//        Assert.assertTrue((exitCode <= 0), "Sim tensorflow failed with Exit code " + exitCode);
    }

    @Test(testName = "Android6", groups = { "5" })
    public void Android6() {
        log.info("starting Android6");

//        int exitCode = linuxService.linuxRunSSHCommand("echo 123", initIPVec.get(5), StaticDataProvider.LinuxAWS.LINUX_KEY_FILE_PATH);

//        int exitCode = linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxAWS.CD_AND_5_DIR_AWS + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
//                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD, "--ib-crash -d1", "Android5", "", "24"), ip);
//        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxAWS.CD_AND_5_DIR_AWS + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ip);
//        Assert.assertTrue((exitCode <= 0), "Sim tensorflow failed with Exit code " + exitCode);
    }

    @Test(testName = "Android7", groups = { "6" })
    public void Android7() {
        log.info("starting Android7");

//        int exitCode = linuxService.linuxRunSSHCommand("echo 123", initIPVec.get(6), StaticDataProvider.LinuxAWS.LINUX_KEY_FILE_PATH);

//        int exitCode = linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxAWS.CD_AND_5_DIR_AWS + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
//                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD, "--ib-crash -d1", "Android5", "", "24"), ip);
//        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxAWS.CD_AND_5_DIR_AWS + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ip);
//        Assert.assertTrue((exitCode <= 0), "Sim tensorflow failed with Exit code " + exitCode);
    }

    @Test(testName = "Android8", groups = { "7" })
    public void Android8() {
        log.info("starting Android8");

//        int exitCode = linuxService.linuxRunSSHCommand("echo 123", initIPVec.get(7), StaticDataProvider.LinuxAWS.LINUX_KEY_FILE_PATH);


//        int exitCode = linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxAWS.CD_AND_9_DIR_AWS + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
//                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD, "--ib-crash -d1", "Android5", "", "24"), ip);
//        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxAWS.CD_AND_9_DIR_AWS + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ip);
//        Assert.assertTrue((exitCode <= 0), "Sim tensorflow failed with Exit code " + exitCode);
    }

    @Test(testName = "Android9", groups = { "8" })
    public void Android9() {
        log.info("starting Android9");

//        int exitCode = linuxService.linuxRunSSHCommand("echo 123", initIPVec.get(8), StaticDataProvider.LinuxAWS.LINUX_KEY_FILE_PATH);


//        int exitCode = linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxAWS.CD_AND_9_DIR_AWS + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
//                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD, "--ib-crash -d1", "Android5", "", "24"), ip);
//        linuxService.linuxRunSSHCommand(StaticDataProvider.LinuxAWS.CD_AND_9_DIR_AWS + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", ip);
//        Assert.assertTrue((exitCode <= 0), "Sim tensorflow failed with Exit code " + exitCode);
    }
}